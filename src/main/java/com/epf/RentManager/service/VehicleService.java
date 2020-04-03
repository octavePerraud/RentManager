package com.epf.RentManager.service;

import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.dao.VehicleDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.exception.ValidatorException;
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.model.Vehicle;

import Validator.ClientValidator;
import Validator.VehicleValidator;

public class VehicleService {
	private static VehicleService instance = null;
	private VehicleService() {}

	public static VehicleService getInstance() {
		if(instance == null) {
			instance = new VehicleService();
		}
		return instance;
	}
	VehicleDao vehicledao = VehicleDao.getInstance();
	VehicleValidator vehiclevalidator = VehicleValidator.getInstance();

	/**
	 *  create a new Vehicle
	 * @param vehicle
	 * @return
	 * @throws ServiceException
	 */
	public long create(Vehicle vehicle) throws ServiceException {

		try {
			return vehiclevalidator.create(vehicle);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicledao.findALL();
		}catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Optional<Vehicle> findById(int id) throws ServiceException  {
		Optional<Vehicle> optVehicle;
		try {
			optVehicle = vehicledao.findById(id);
			if(optVehicle.isPresent()) {
				Vehicle v= optVehicle.get();
				System.out.println(v);

			}else {
				System.out.println("erreur lors du select du vehicle");
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
		return optVehicle;

	}


	/**
	 * delete the vehicle
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public long delete(int id)  throws ServiceException {
		ReservationService reservationservice = ReservationService.getInstance();

		List<Reservation> resultlist = reservationservice.findResaByVehicleId(id);
		for(Reservation r: resultlist ) {

			reservationservice.delete(r.getId());
		}
		try {
			return vehicledao.delete(id);
		}catch(DaoException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	/**
	 * edit the vehicle
	 * @param vehicle
	 * @return
	 * @throws ServiceException
	 */
	public long edit(Vehicle vehicle) throws ServiceException  {
		
		try {
			return vehiclevalidator.edit(vehicle);
		} catch (ValidatorException e) {
			throw new ServiceException("probleme lors de la modification du vehicule "+ e.getMessage());
		}
	}
}
