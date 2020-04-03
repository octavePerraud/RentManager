package controler;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Vehicle;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.VehicleService;

public class VehControler {
	VehicleService vehicleService =VehicleService.getInstance();
	public static void main(String[] args) {

		VehControler veh = new VehControler();
		Scanner sc =new Scanner(System.in);
		boolean done =false;
		while(!done) {

			System.out.println("liste des operations");
			System.out.println("1 - Afficher la liste des vehicle ");
			System.out.println("2 - Ajouter un vehicule ");
			System.out.println("3 - Chercher un vehicule ");
			System.out.println("4 - Supprimer un vehicule ");
			System.out.println("5 - Modifier un vehicule ");



			int choix = sc.nextInt();
			sc.nextLine();
			Vehicle vehicle = new Vehicle();
			switch(choix) {

			case 0:
				done =true;
				break;
			case 1:
				try{
					List<Vehicle> list = veh.vehicleService.findAll();

					for(Vehicle vehicle1 : list) {
						System.out.println(vehicle1);

					}
				}catch(ServiceException e) {
					System.out.println("une erreue est survenue :"+ e.getMessage());
				}
				printAllVehicle(veh);
				break;
				
			
			case 2:

				System.out.println("Entrez le Constructeur");
				vehicle.setConstructeur(sc.nextLine());
				System.out.println("Entrez le Modele");
				vehicle.setModele(sc.nextLine());
				System.out.println("Entrz le nombre de places");
				vehicle.setNb_places(sc.nextInt());
				try {
					veh.vehicleService.create(vehicle);
				} catch (ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				break;
			case 3:

				try {
					System.out.println("Entrez l'id");
					int id =sc.nextInt();
					veh.vehicleService.findById(id);
				} catch (ServiceException e) {
					System.out.println("une erreur est survenue:"+e.getMessage());
				}

				break;
				
			case 4:
				
				System.out.println("Entrez l'id");
				int id =sc.nextInt();
				try {
					veh.vehicleService.delete(id);
				} catch (ServiceException e) {
					System.out.println("Unne erreur est survenue:"+e.getMessage());
					//e.printStackTrace();
				}
				break;
			case 5:
				
				System.out.println("Entrez l'id");
				vehicle.setId(Integer.parseInt(sc.nextLine()));
				System.out.println("Entrez le constructeur");
				vehicle.setConstructeur(sc.nextLine());
				System.out.println("Entrez le modele");
				vehicle.setModele(sc.nextLine());
				System.out.println("Entrz le nombre de place");
				vehicle.setNb_places(sc.nextInt());
				
				try {
				
					veh.vehicleService.edit(vehicle);
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
	
	
	private static void printAllVehicle(VehControler veh) {
		try {
			List<Vehicle>list =veh.vehicleService.findAll();

			/*for(Vehicle vehicle : list) {
				System.out.println(vehicle);
			}
			*/
			//list.stream().forEach(System.out::println); //equivalent au for
			
		}catch(ServiceException e) {
			System.out.println("Unne erreur est survenue:"+e.getMessage());
		}
	}
}
