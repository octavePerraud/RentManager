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

import com.ensta.rentmanager.persistence.ConnectionManager;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.model.Vehicle;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;

public class ClientDao {

	private static ClientDao instance = null;
	private static ClientDao instanceTest = null;
	private ClientDao() {}
	private ClientDao(boolean test ) {
		this.test=test;
	};


	private boolean test;

	public static ClientDao getInstance(boolean test) {
		if(test) {
			if( instanceTest == null) {
				instanceTest =new ClientDao(true);
			}
		}else {
			if( instanceTest == null) {
				instanceTest =new ClientDao(false);
			}
		}
		return instance;
	}

	public static ClientDao getInstance() {
		if (instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String EDIT_CLIENT_QUERY = "UPDATE client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ? ;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";


	/**
	 * 
	 * @param client
	 * @return
	 * @throws DaoException
	 */
	public long create(Client client) throws DaoException {
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE_CLIENT_QUERY);) {
			statement.setString(1, client.getNom());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getEmail());
			statement.setDate(4, client.getNaissance());

			long result = statement.executeUpdate();
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
	public List<Client> findALL() throws DaoException {
		List<Client> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_CLIENTS_QUERY);) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Client client = new Client(resultSet.getInt(1), 
						resultSet.getString(2), 
						resultSet.getString(3),
						resultSet.getString(4), 
						resultSet.getDate(5));
				resultList.add(client);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}

	}



	public static void main(String... arg) {
		ClientDao dao = ClientDao.getInstance();

		try {
			List<Client> list = dao.findALL();

			for (Client c : list) {
				System.out.println(c);
			}

		} catch (DaoException e) {
			System.out.println("Erreur lors du Select du client");
		}
		/*
		 * Optional<Client> opt = dao.findById(1); if(opt.isEmpty()) {
		 * System.out.println("Erreur lors de la recuperation"); }else { opt.get(); }
		 */
		Optional<Client> optClient;
		optClient = dao.findById(0);
		if (optClient.isPresent()) {
			Client c = optClient.get();
		} else {
			System.out.println("Erreur lors du select du client");
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Client> findById(int id) {
		Client c = new Client();

		Optional<Client> optClient = Optional.of(c);

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement= conn.prepareStatement(FIND_CLIENT_QUERY);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				c = new Client(id,
						resultSet.getString(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getDate(4));
				optClient = Optional.of(c);
			}
			resultSet.close();

		} catch (SQLException e) {

			return Optional.empty();
		}
		return optClient;
	}


	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long delete(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement =conn.prepareStatement(DELETE_CLIENT_QUERY);)
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
	 * @param client
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long edit(Client client, int id) throws DaoException {

		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(EDIT_CLIENT_QUERY);) {

			statement.setString(1, client.getNom());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getEmail()); 
			statement.setDate(4, client.getNaissance());
			statement.setInt(5, id);

			long result = statement.executeUpdate();
			statement.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}
	}

}
