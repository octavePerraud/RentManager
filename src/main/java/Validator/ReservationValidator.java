package Validator;

import com.ensta.rentmanager.dao.ReservationDao;

public class ReservationValidator {
	
	private static ReservationValidator instance = null;
	private ReservationValidator() {}
	




	public static ReservationValidator getInstance() {
		if (instance == null) {
			instance = new ReservationValidator();
		}
		return instance;
	}
	
	
	ReservationDao reservationdao = ReservationDao.getInstance();

}
