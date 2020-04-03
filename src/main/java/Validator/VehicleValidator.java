package Validator;

import com.ensta.rentmanager.dao.VehicleDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ValidatorException;
import com.ensta.rentmanager.model.Vehicle;

public class VehicleValidator {

	private static VehicleValidator instance = null;

	private VehicleValidator() {}


	public static VehicleValidator getInstance() {
		if (instance == null) {
			instance = new VehicleValidator();
		}
		return instance;
	}
	
	
	VehicleDao vehicledao = VehicleDao.getInstance();
	
	
	
	public long create(Vehicle vehicle) throws ValidatorException {
		long nb_places = vehicle.getNb_places();
		String constructeur = vehicle.getConstructeur();
		String modele = vehicle.getModele();
		if(nb_places>9 || nb_places<=1) {
			throw new ValidatorException("le vehicule doit avoir entre 2 et 9 places");
		}
		if(constructeur ==null ) {
			throw new ValidatorException("le vehicule doit avoir un constructeur");
		}
		if(modele ==null ) {
			throw new ValidatorException("le vehicule doit avoir un modele");
		}
		try {
			return vehicledao.create(vehicle);
		} catch (DaoException e) {
			throw new ValidatorException(e.getMessage());
		}
	}
	
	
	public long edit(Vehicle vehicle) throws ValidatorException  {
		long nb_places = vehicle.getNb_places();
		String constructeur = vehicle.getConstructeur();
		String modele = vehicle.getModele();

		if(nb_places>9 || nb_places<=1) {
			throw new ValidatorException("le vehicule doit avoir entre 2 et 9 places");
		}
		if(constructeur == null ) {
			
			throw new ValidatorException("le vehicule doit avoir un constructeur");
		}
		if(modele == null ) {
			throw new ValidatorException("le vehicule doit avoir un modele");
		}
		try {
			return vehicledao.edit(vehicle);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ValidatorException("probleme lors de la modification du vehicule "+ e.getMessage());
		}


	}
}
