package com.epf.RentManager.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.dao.ReservationDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Reservation;

public class ReservationService {
	private static ReservationService instance = null;
	private ReservationService() {}

	public static ReservationService getInstance() {
		if(instance == null) {
			instance = new ReservationService();
		}
		return instance;
	}
	ReservationDao reservationdao = ReservationDao.getInstance();


	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Reservation> findAll() throws ServiceException {
		try {
			return reservationdao.findALL();
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
	public Optional<Reservation> findById(int id)throws ServiceException{
		Optional<Reservation> optReservation =reservationdao.findById(id);
		if(optReservation.isPresent()) {
			Reservation r= optReservation.get();
			System.out.println(r);
		}else {
			System.out.println("erreur lors du select du client");
		}
		return optReservation;
	}

	/**
	 * 
	 * @param client_id
	 * @return
	 * @throws ServiceException
	 */
	public List<Reservation> findResaByClientId(int client_id) throws ServiceException {
		try {
			return reservationdao.findResaByClientId(client_id);
		}catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	/**
	 * 
	 * @param vehicle_id
	 * @return
	 * @throws ServiceException
	 */
	public List<Reservation> findResaByVehicleId(int vehicle_id) throws ServiceException{
		try {
			return reservationdao.findResaByVehicleId(vehicle_id);
		}catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}



	}

	/**
	 * 
	 * @param int id
	 * @return
	 * @throws ServiceException
	 */
	public long delete(int id) throws ServiceException  {

		try {
			return reservationdao.delete(id);
		}catch(DaoException e) {
			throw new ServiceException(e.getMessage());

		}
	}

	/**
	 * 
	 * @param reservation
	 * @return
	 * @throws ServiceException
	 */
	public long create(Reservation reservation) throws ServiceException  {
		int vehicle_id = reservation.getVehicle_id();
		Date debut = reservation.getDebut();
		Date fin =reservation.getFin();
		List <Reservation> resa;
		try {
			resa = reservationdao.findALL();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
		long diff = fin.getTime() - debut.getTime();
		float duree = (diff / (1000*60*60*24));


		if (0<duree && duree<8) {


			long joursavant= testavantRes (debut, fin, resa, vehicle_id);
			long joursapres= testapresRes (debut, fin, resa, vehicle_id);

			System.out.println("duree");
			System.out.println(joursavant);
			System.out.println(joursapres);
			System.out.println(duree);
			System.out.println("somme");
			System.out.println(joursavant+joursapres+duree);

			if (joursavant+joursapres+duree >30) {
				throw new ServiceException("le vehicule serait trop louer plus de 30 jours de suite");
			}


			try {
				return reservationdao.create(reservation);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				throw new ServiceException(e.getMessage());
			}
		}
		throw new ServiceException("trop long periode");

	}


	/**
	 * 
	 * @param reservation
	 * @return
	 * @throws ServiceException
	 */
	public long edit(Reservation reservation) throws ServiceException  {

		int vehicle_id = reservation.getVehicle_id();
		Date debut = reservation.getDebut();
		Date fin =reservation.getFin();
		List <Reservation> resa;
		try {
			resa = reservationdao.findALL();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
		long diff = fin.getTime() - debut.getTime();
		float duree = (diff / (1000*60*60*24));


		if (0<duree && duree<7) {


			long joursavant= testavantRes (debut, fin, resa, vehicle_id);
			long joursapres= testapresRes (debut, fin, resa, vehicle_id);

			if (joursavant+joursapres+duree >30) {
				throw new ServiceException("le vehicule serait trop louer plus de 30 jours de suite");
			}





			try {
				return reservationdao.edit(reservation);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				throw new ServiceException("probleme lors de la modification de la reservation "+ e.getMessage());
			}
		}
		throw new ServiceException("trop long periode fait plis de 7 jours ou les date ne sont pas bonnes");


	}



	/**
	 * 
	 * @param debut
	 * @param fin
	 * @param listReservation
	 * @param vehicle_id
	 * @return
	 * @throws ServiceException
	 */
	public long testavantRes (Date debut, Date fin, List <Reservation> listReservation, int vehicle_id) throws ServiceException {
		long d=0;
		//for(int j=0; j< listReservation.size(); j++) {
		int j= 0;

		while(j< listReservation.size() && d==0) {
			if (vehicle_id== listReservation.get(j).getVehicle_id()) {
				if(!(debut.after(listReservation.get(j).getFin()) || fin.before(listReservation.get(j).getDebut()))){
					throw new ServiceException("le vehicule est deja reservé a ces dates");
				}
				if((((listReservation.get(j).getFin().getTime() - debut.getTime())/ (1000*60*60*24))<0) &&  (((listReservation.get(j).getFin().getTime() - debut.getTime())/ (1000*60*60*24))>-2)) {

					d+= (((listReservation.get(j).getFin().getTime()) - (listReservation.get(j).getDebut().getTime()))/ (1000*60*60*24))+1;

				}

				j++;
			}
		}

		j--;
		for(int i=0; i< listReservation.size(); i++) {

			if (i!=j && d!=0 &&(((listReservation.get(i).getFin().getTime()- listReservation.get(j).getDebut().getTime())/ (1000*60*60*24))>-2)&&((listReservation.get(i).getFin().getTime()- listReservation.get(j).getDebut().getTime())/ (1000*60*60*24))<0) {

				j=i;
				d += (((listReservation.get(j).getFin().getTime()) - (listReservation.get(j).getDebut().getTime()))/ (1000*60*60*24))+1;
				i=-1;
				if(d>30) {

					throw new ServiceException("le vehicule est deja trop louer sur les 30 derniers jours");
				}
			}

		}

		return d;

	}


	/**
	 * 
	 * @param debut
	 * @param fin
	 * @param listReservation
	 * @param vehicle_id
	 * @return
	 * @throws ServiceException
	 */
	public long testapresRes (Date debut, Date fin, List <Reservation> listReservation, int vehicle_id) throws ServiceException {
		long a=0;
		int j=0;
		while(j< listReservation.size()&& a==0) {
			if (vehicle_id == listReservation.get(j).getVehicle_id()) {

				if(((( fin.getTime() - listReservation.get(j).getDebut().getTime())/ (1000*60*60*24))>-2) &&  (((( fin.getTime() - listReservation.get(j).getDebut().getTime())/ (1000*60*60*24))<0))) {

					a+= (((listReservation.get(j).getFin().getTime()) - (listReservation.get(j).getDebut().getTime()))/ (1000*60*60*24))+1;

				}	
				j++;
			}
		}

		j--;

		for(int i=0; i< listReservation.size(); i++) {

			if (i!=j&& a!=0 &&(((listReservation.get(j).getFin().getTime()- listReservation.get(i).getDebut().getTime())/ (1000*60*60*24))>-2)&& (((listReservation.get(j).getFin().getTime()- listReservation.get(i).getDebut().getTime())/ (1000*60*60*24))<0)) {
				j=i;
				a+=(((listReservation.get(j).getFin().getTime()) - (listReservation.get(j).getDebut().getTime()))/ (1000*60*60*24))+1;
				i=-1;

				if(a>30) {
					throw new ServiceException("le vehicule est deja loué sur une periode de 30 jours consecutifs");
				}
			}
		}


		return a;

	}


}
