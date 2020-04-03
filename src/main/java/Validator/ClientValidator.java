package Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.ensta.rentmanager.dao.ClientDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ValidatorException;
import com.ensta.rentmanager.model.Client;

public class ClientValidator {

	private static ClientValidator instance = null;
	private ClientValidator() {}



	public static ClientValidator getInstance() {
		if (instance == null) {
			instance = new ClientValidator();
		}
		return instance;
	}


	ClientDao clientdao = ClientDao.getInstance();



	/**
	 * 
	 * @param client
	 * @return
	 * @throws ValidatorException
	 */
	public long create(Client client) throws ValidatorException {
		long age = ChronoUnit.YEARS.between(client.getNaissance().toLocalDate(),LocalDate.now());
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String email = client.getEmail();
		List <Client> cli;

		try {
			cli= clientdao.findALL();
			for(Client c : cli) {
				if (email.compareTo(c.getEmail())==0) {
					throw new ValidatorException(": cette email est deja utilisé");
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ValidatorException(e.getMessage());
		}

		if(age<18) {
			throw new ValidatorException(": le client doit avoir 18 ans");
		}
		if(nom.length()<3) {
			throw new ValidatorException(": le nom du client doit avoir 3 caracteres au minimum");
		}
		if(prenom.length()<3) {
			throw new ValidatorException(": le prenom du client doit avoir 3 caracteres au minimum");
		}


		try {
			return clientdao.create(client);
		} catch (DaoException e) {
			throw new ValidatorException(e.getMessage());
		}
	}


	/**
	 * 
	 * @param client
	 * @param id
	 * @return
	 * @throws ValidatorException
	 */
	public long edit(Client client, int id) throws ValidatorException  {
		long age = ChronoUnit.YEARS.between(client.getNaissance().toLocalDate(),LocalDate.now());
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String email = client.getEmail();

		try {
			List <Client> cli = clientdao.findALL();
			for(Client c : cli) {
				if (email.compareTo(c.getEmail())==0 && c.getId() != id) {
					throw new ValidatorException(": cette email est deja utilisé");
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ValidatorException(e.getMessage());
		}

		if(age<18) {
			throw new ValidatorException("le client doit avoir 18 ans");
		}else if(nom.length()<3) {
			throw new ValidatorException("le nom du client doit avoir 3 caracteres au minimum");
		}else if(prenom.length()<3) {
			throw new ValidatorException("le prenom du client doit avoir 3 caracteres au minimum");
		}

		try {
			return clientdao.edit(client, id);
		} catch (DaoException e) {
			throw new ValidatorException(e.getMessage());
		}

	}




}
