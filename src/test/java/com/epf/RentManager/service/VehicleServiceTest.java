package com.epf.RentManager.service;


import static org.junit.Assert.fail;

import org.junit.Test;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Vehicle;


public class VehicleServiceTest {
	
	VehicleService vehicleService = VehicleService.getInstance();

	public void testCreate() {
		Vehicle vehicle = new Vehicle();
		vehicle.setModele("modele");
		vehicle.setConstructeur("constructeur");
		vehicle.setNb_places(6);
	
		try {
			vehicleService.create(vehicle);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
//	
//	@Test(expected = ServiceException.class)
//	public void testCreateFail() throws ServiceException {
//		Vehicle vehicle = new Vehicle();
//		vehicle.setModele("");
//		vehicle.setConstructeur("constructeur");
//		vehicle.setNb_places(6);
//		
//		vehicleService.create(vehicle);
//	}
//	
//	
//	@Test(expected = ServiceException.class)
//	public void testCreateFailConstru() throws ServiceException {
//		Vehicle vehicle = new Vehicle();
//		vehicle.setModele("modele");
//		vehicle.setConstructeur("");
//		vehicle.setNb_places(6);
//		
//		vehicleService.create(vehicle);
//	}
//	
	@Test(expected = ServiceException.class)
	public void testCreateFailNbPlaces20() throws ServiceException {
		Vehicle vehicle = new Vehicle();
		vehicle.setModele("modele");
		vehicle.setConstructeur("");
		vehicle.setNb_places(20);
		
		vehicleService.create(vehicle);
	}
	@Test(expected = ServiceException.class)
	public void testCreateFailNbPlaces_5() throws ServiceException {
		Vehicle vehicle = new Vehicle();
		vehicle.setModele("modele");
		vehicle.setConstructeur("");
		vehicle.setNb_places(-5);
		
		vehicleService.create(vehicle);
	}

}
