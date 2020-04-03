package com.epf.RentManager.service;

import org.junit.Test;

import static org.junit.Assert.fail;

import java.sql.Date;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;


public class ClientServiceTest {
	
	ClientService clientService = ClientService.getInstance();
	
	public void testCreate() {
		Client client = new Client();
		client.setNom("nom");
		client.setPrenom("prenom");
		client.setEmail("Email@email");
		client.setNaissance(Date.valueOf("2000-12-12"));

		try {
			clientService.create(client);

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			fail();
		}

	}

	@Test(expected = ServiceException.class)
	public void testCreateFail() throws ServiceException {
		Client client = new Client();
		client.setNom("nom");
		client.setPrenom("Prenom");
		client.setEmail("Email@email");
		client.setNaissance(Date.valueOf("2011-12-12"));
		clientService.create(client);
	}
	
	
//	@Test(expected = ServiceException.class)
//	public void testCreateFailNom() {
//		Client client = new Client();
//		client.setNom("no");
//		client.setPrenom("prenom");
//		client.setEmail("Email@email");
//		client.setNaissance(Date.valueOf("2000-12-12"));
//	}
//	
//	@Test(expected = ServiceException.class)
//	public void testCreateFailPrenom() {
//		Client client = new Client();
//		client.setNom("nom");
//		client.setPrenom("pr");
//		client.setEmail("Email@email");
//		client.setNaissance(Date.valueOf("2000-12-12"));
//	}
//	
//	public void testEdit() {
//		Client client = new Client();
//		client.setNom("nomEtdi");
//		client.setPrenom("prenomEdit");
//		client.setEmail("Email@emailEdit");
//		client.setNaissance(Date.valueOf("2000-12-12"));
//		int id=1;
//
//		try {
//			clientService.edit(client,id);
//
//		} catch (ServiceException e) {
//			System.out.println(e.getMessage());
//			fail();
//		}
//	}
//	
//	@Test(expected = ServiceException.class)
//	public void testEditFail() {
//		Client client = new Client();
//		client.setNom("nomEtdi");
//		client.setPrenom("prenomEdit");
//		client.setEmail("Email@emailEdit");
//		client.setNaissance(Date.valueOf("2011-12-12"));
//		int id=1;
//
//		try {
//			clientService.edit(client,id);
//
//		} catch (ServiceException e) {
//			System.out.println(e.getMessage());
//			fail();
//		}
//	}
}