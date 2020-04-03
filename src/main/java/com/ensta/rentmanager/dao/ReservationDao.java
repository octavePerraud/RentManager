package com.ensta.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.model.Vehicle;
import com.ensta.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String EDIT_RESERVATION_QUERY = "UPDATE reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ? ;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	
	private static final String FIND_RESERVATIONS_QUERY2 ="SELECT r.id, r.client_id, c.nom, c.prenom, r.vehicle_id, r.debut, r.fin FROM Reservation as r, Client as c WHERE r.client_id=c.id;";
	
	
	/**
	 * 
	 * @param reservation
	 * @return
	 * @throws DaoException
	 */
	public long create(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE_RESERVATION_QUERY);) {

			statement.setInt(1, reservation.getClient_id());
			statement.setInt(2, reservation.getVehicle_id());
			statement.setDate(3, reservation.getDebut());
			statement.setDate(4, reservation.getFin());

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
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long delete(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement =conn.prepareStatement(DELETE_RESERVATION_QUERY);)
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
	 */
	public Optional<Reservation> findById(int id) {
		Reservation r = new Reservation();

		Optional<Reservation> optReservation = Optional.of(r);

		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement= conn.prepareStatement(FIND_RESERVATION_QUERY);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				r = new Reservation(id,
						resultSet.getInt(1),
						resultSet.getInt(2),
						resultSet.getDate(3),
						resultSet.getDate(4));
				optReservation = Optional.of(r);
			}
			resultSet.close();

		} catch (SQLException e) {

			return Optional.empty();
		}
		return optReservation;
	}


	/**
	 * 
	 * @param client_id
	 * @return
	 * @throws DaoException
	 */
	public List<Reservation> findResaByClientId(int client_id) throws DaoException {

		List<Reservation> list = new ArrayList<>();

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement= conn.prepareStatement( FIND_RESERVATIONS_BY_CLIENT_QUERY);) {
			statement.setInt(1, client_id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Reservation r = new Reservation(resultSet.getInt(1),
						client_id,
						resultSet.getInt(2),
						resultSet.getDate(3),
						resultSet.getDate(4));
				list.add(r);
			}
			resultSet.close();

			return list;

		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}

	}


	/**
	 * 
	 * @param vehicle_id
	 * @return
	 * @throws DaoException
	 */
	public List<Reservation> findResaByVehicleId(int vehicle_id) throws DaoException {

		List<Reservation> list = new ArrayList<>();

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement= conn.prepareStatement( FIND_RESERVATIONS_BY_VEHICLE_QUERY);) {
			statement.setInt(1, vehicle_id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Reservation r = new Reservation(resultSet.getInt(1),
						resultSet.getInt(2),
						vehicle_id,						
						resultSet.getDate(3),
						resultSet.getDate(4));

				list.add(r);
			}
			resultSet.close();

			return list;

		} catch (SQLException e) {

			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}

	}



	
	/**
	 * 
	 * @return
	 * @throws DaoException
	 */
	public List<Reservation> findALL() throws DaoException {
		List<Reservation> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_QUERY);) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getInt(1),
						resultSet.getInt(2), 
						resultSet.getInt(3), 
						resultSet.getDate(4),
						resultSet.getDate(5));
				resultList.add(reservation);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}

	}
	
	/**
	 * 
	 * @param reservation
	 * @return
	 * @throws DaoException
	 */
	public long edit(Reservation reservation) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(EDIT_RESERVATION_QUERY);) {

			statement.setInt(1, reservation.getClient_id());
			statement.setInt(2, reservation.getVehicle_id());
			statement.setDate(3, reservation.getDebut()); 
			statement.setDate(4, reservation.getFin());
			statement.setInt(5, reservation.getId());

			long result = statement.executeUpdate();
			statement.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}
	}
}
