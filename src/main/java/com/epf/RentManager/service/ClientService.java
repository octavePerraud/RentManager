package com.epf.RentManager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.dao.ClientDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.exception.ValidatorException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;

import Validator.ClientValidator;

public class ClientService {

	private static ClientService instance = null;
	private ClientService() {}

	public static ClientService getInstance() {
		if(instance == null) {
			instance = new ClientService();
		}
		return instance;
	}
	ClientDao clientdao = ClientDao.getInstance();
	ReservationService reservationservice = ReservationService.getInstance();
	
	ClientValidator clientvalidator = ClientValidator.getInstance();

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Client> findAll() throws ServiceException{
		try {
			return clientdao.findALL();
		}catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param client
	 * @return
	 * @throws ServiceException
	 */
	public long create(Client client) throws ServiceException {

		try {
			return clientvalidator.create(client);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}

	}
	

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Optional<Client> findById(int id)throws ServiceException{
		Optional<Client> optClient =clientdao.findById(id);
		if(optClient.isPresent()) {
			Client c= optClient.get();
			System.out.println(c);
		}else {
			System.out.println("erreur lors du select du client");
		}
		return optClient;
	}

	/*public void checkAge(Client client) throws ServiceException{
		long age = ChronoUnit.YEARS.between(client.getNaissance().toLocalDate(),LocalDate.now());
		if(age<18) {
			throw new ServiceException("le client doit avoir 18 ans il a "+age);
		}
	}*/

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public long delete(int id) throws ServiceException {
		ReservationService reservationservice = ReservationService.getInstance();

		List<Reservation> resultlist = reservationservice.findResaByClientId(id);
		for(Reservation r: resultlist ) {

			reservationservice.delete(r.getId());
		}
		try {
			return clientdao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}


	}

	/**
	 * 
	 * @param client
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public long edit(Client client, int id) throws ServiceException  {
	
		try {
			return clientvalidator.edit(client, id);
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("probleme lors de la modification du client "+ e.getMessage());
		}


	}

}

