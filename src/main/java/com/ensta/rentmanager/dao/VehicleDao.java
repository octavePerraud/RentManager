package com.ensta.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.model.Vehicle;
import com.ensta.rentmanager.persistence.ConnectionManager;


public class VehicleDao {

	private static VehicleDao instance = null;
	private static VehicleDao instanceTest = null;
	private VehicleDao() {}
	
	private VehicleDao(boolean test ) {
		this.test=test;
	};


	private boolean test;

	public static VehicleDao getInstance(boolean test) {
		if(test) {
			if( instanceTest == null) {
				instanceTest =new VehicleDao(true);
			}
		}else {
			if( instanceTest == null) {
				instanceTest =new VehicleDao(false);
			}
		}
		return instance;
	}

	
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?,?, ?);";
	private static final String EDIT_VEHICLE_QUERY = "UPDATE vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ? ;";

	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";

	/**
	 * 
	 * @param vehicle
	 * @return
	 * @throws DaoException
	 */
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE_VEHICLE_QUERY);) {
			statement.setString(1, vehicle.getConstructeur());
			statement.setString(2, vehicle.getModele());
			statement.setInt(3, vehicle.getNb_places());

			long result = statement.executeLargeUpdate();
			statement.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @throws DaoException
	 */
	public List<Vehicle> findALL() throws DaoException {
		List<Vehicle> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_VEHICLES_QUERY);) 
		{
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Vehicle vehicle = new Vehicle(resultSet.getInt(1), 
						resultSet.getString(2), 
						resultSet.getString(3),
						resultSet.getInt(4));
				resultList.add(vehicle);
			}
			return resultList;
		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}

	}


	public static void main(String... arg) {
		VehicleDao dao = VehicleDao.getInstance();
		try {
			List<Vehicle> list = dao.findALL();

			for (Vehicle v : list) {
				System.out.println(v);
			}

		} catch (DaoException e) {
			System.out.println("Erreur lors du Select du vehicle");
		}
		Optional<Vehicle> optVehicle;
		try {
			optVehicle = dao.findById(0);
			if (optVehicle.isPresent()) {
				Vehicle v = optVehicle.get();
			} else {
				System.out.println("Erreur lors du select du vehicle");
			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long delete(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement =conn.prepareStatement(DELETE_VEHICLE_QUERY);)
		{
			statement.setInt(1,  id);

			long result =statement.executeUpdate();
			statement.close();
			conn.close();

			return result;

		}catch(SQLException e) {
			throw new DaoException("Erreur lors de la suppression :"+e.getMessage());
		}

	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public Optional<Vehicle> findById(int id) throws DaoException {
		Vehicle v = new Vehicle();

		Optional<Vehicle> optVehicle = Optional.of(v);

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement= conn.prepareStatement(FIND_VEHICLE_QUERY);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				v = new Vehicle(id,
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getInt(4));

				optVehicle = Optional.of(v);
			}
			resultSet.close();

		} catch (SQLException e) {

			return Optional.empty();
		}
		return optVehicle;
	}


	/**
	 * 
	 * @param vehicle
	 * @return
	 * @throws DaoException
	 */
	public long edit(Vehicle vehicle) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(EDIT_VEHICLE_QUERY);) {

			statement.setString(1, vehicle.getConstructeur());
			statement.setString(2, vehicle.getModele());
			statement.setInt(3, vehicle.getNb_places()); 
			statement.setInt(4, vehicle.getId());

			long result = statement.executeUpdate();
			statement.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}
	}

}
