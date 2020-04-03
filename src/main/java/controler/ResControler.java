package controler;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Reservation;
import com.epf.RentManager.service.ReservationService;


public class ResControler {
	ReservationService reservationService = ReservationService.getInstance();
	public static void main(String[] args) {

		ResControler res = new ResControler();
		Scanner sc =new Scanner(System.in);
		boolean done =false;
		while(!done) {

			System.out.println("liste des operations");
			System.out.println("1 - Afficher la liste des Reservation");
			System.out.println("2 - Creer une reservation");
			System.out.println("3 - Lister toutes les Réservations associées à un Client donné");
			System.out.println("4 - Lister toutes les Réservations associées à un Vehicle donné");
			System.out.println("5 - Supprimer une reservation");
			System.out.println("6 - Modifer une reservation");
			System.out.println("7 - Afficher une reservation choisie ");


			int choix = sc.nextInt();
			sc.nextLine();
			Reservation reservation = new Reservation();
			switch(choix) {
			case 0:
				done =true;
				break;
			case 1:
				try{
					List<Reservation> list = res.reservationService.findAll();

					for(Reservation Reservation : list) {
						System.out.println(Reservation);

					}
				}catch(ServiceException e) {
					System.out.println("une erreue est survenue :"+ e.getMessage());
				}
				printAllReservation(res);
				break;
			case 2:
				System.out.println("Entrez l'id du client");
				reservation.setClient_id(Integer.parseInt(sc.nextLine()));
				System.out.println("Entrez l'id du vehicle");
				reservation.setVehicle_id(Integer.parseInt(sc.nextLine()));
				//sc.nextLine();
				System.out.println("Entrez la date de debut (yyyy-mm-jj)");
				reservation.setDebut(Date.valueOf(sc.nextLine()));
				System.out.println("Entrez la date de fin (yyyy-mm-jj)");
				reservation.setFin(Date.valueOf(sc.nextLine()));
				try {
					res.reservationService.create(reservation);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					System.out.println("une erreur est survenue lors de la creation de la reservation :"+e.getMessage());
				}
				break;
			case 3:

				try {
					System.out.println("Entrez l'id du client");
					int id =sc.nextInt();

					List<Reservation> list = res.reservationService.findResaByClientId(id);

					for(Reservation Reservation : list) {
						System.out.println(Reservation);

					}

					System.out.println(res);
				} catch (ServiceException e) {
					System.out.println("une erreur est survenue:"+e.getMessage());
				}
				printAllReservation(res);
				break;
			case 4:

				try {
					System.out.println("Entrez l'id du vehicle");
					int id =sc.nextInt();

					List<Reservation> list = res.reservationService.findResaByVehicleId(id);

					for(Reservation Reservation : list) {
						System.out.println(Reservation);
					}

				} catch (ServiceException e) {
					System.out.println("une erreur est survenue:"+e.getMessage());
				}
				break;


			case 5:		
				System.out.println("Entrez l'id");
				int id =sc.nextInt();
				try {
					res.reservationService.delete(id);
				} catch (ServiceException e) {
					System.out.println("Unne erreur est survenue:"+e.getMessage());
					//e.printStackTrace();
				}
				break;
			case 6:

				System.out.println("Entrez l'id de la reservation");
				reservation.setId(Integer.parseInt(sc.nextLine()));
				System.out.println("Modifiez la reservation");
				System.out.println("Entrez l'id du client");
				reservation.setClient_id(Integer.parseInt(sc.nextLine()));
				System.out.println("Entrez l'id du vehicule");
				reservation.setVehicle_id(Integer.parseInt(sc.nextLine()));
				System.out.println("Entrez la date de debut (yyyy-mm-jj)");
				reservation.setDebut(Date.valueOf(sc.nextLine()));
				System.out.println("Entrez la date de fin (yyyy-mm-jj)");
				reservation.setFin(Date.valueOf(sc.nextLine()));

				try {

					res.reservationService.edit(reservation);
				} catch (ServiceException e) {
					System.out.println("Unne erreur est survenue:"+e.getMessage());
					//e.printStackTrace();
				}
				break;
			case 7:		
				
				System.out.println("Entrez l'id");
				id =sc.nextInt();
				try {
					res.reservationService.findById(id);
				} catch (ServiceException e) {
					System.out.println("Unne erreur est survenue:"+e.getMessage());
					//e.printStackTrace();
				}
				break;
			default:
				System.out.println("PAS LE BON CHOIX");
				break;
			}
		}
		sc.close();	
	}
	private static void printAllReservation(ResControler res) {
		try {
			List<Reservation>list =res.reservationService.findAll();

			//for(Reservation reservation : list) {
			//.out.println(reservation);
			//}

			//list.stream().forEach(System.out::println); //equivalent au for

		}catch(ServiceException e) {
			System.out.println("Unne erreur est survenue:"+e.getMessage());
		}
	}
}


