package controler;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.epf.RentManager.service.ClientService;

public class CliControler {
	ClientService clientService =ClientService.getInstance();
	public static void main(String[] args) {

		CliControler cli = new CliControler();
		Scanner sc =new Scanner(System.in);
		boolean done =false;
		while(!done) {

			System.out.println("liste des operations");
			System.out.println("1 - Afficher la liste des clients");
			System.out.println("2 - Ajouter un client");
			System.out.println("3 - Chercher un client");
			System.out.println("4 - Supprimer un client");
			System.out.println("5 - Modifier un client");


			int choix = sc.nextInt();
			sc.nextLine();
			Client client = new Client();
			switch(choix) {

			case 0:
				done =true;
				break;
			case 1:
				try{
					List<Client> list = cli.clientService.findAll();

					/*for(Client client1 : list) {
						System.out.println(client1);

					}*/
				}catch(ServiceException e) {
					System.out.println("une erreue est survenue :"+ e.getMessage());
				}
				printAllClient(cli);
				break;
			case 2:
				System.out.println("Entrez le nom");
				client.setNom(sc.nextLine());
				System.out.println("Entrez le prenom");
				client.setPrenom(sc.nextLine());
				System.out.println("Entrz l'email");
				client.setEmail(sc.nextLine());
				System.out.println("Entrez la date au format yyyy-mm-jj");
				client.setNaissance(Date.valueOf(sc.nextLine()));
				try {
					cli.clientService.create(client);
				} catch (ServiceException e) {
					
					System.out.println("une erreur est survenue:"+e.getMessage());
				}
				break;
			case 3:

				try {
					System.out.println("Entrez l'id");
					int id =sc.nextInt();
					cli.clientService.findById(id);
				} catch (ServiceException e) {
					System.out.println("une erreur est survenue:"+e.getMessage());
				}

				break;
			case 4:

				System.out.println("Entrez l'id");
				int id =sc.nextInt();
				try {
					cli.clientService.delete(id);
				} catch (ServiceException e) {
					System.out.println("Unne erreur est survenue:"+e.getMessage());
					//e.printStackTrace();
				}
				break;
				
			case 5:
				
				System.out.println("Entrez l'id");
				id =sc.nextInt();
				System.out.println("Entrez le nom");
				client.setNom(sc.nextLine());
				System.out.println("Entrez le prenom");
				client.setPrenom(sc.nextLine());
				System.out.println("Entrz l'email");
				client.setEmail(sc.nextLine());
				System.out.println("Entrez la date au format yyyy-mm-jj");
				client.setNaissance(Date.valueOf(sc.nextLine()));
				
				try {
				
					cli.clientService.edit(client, id);
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

	private static void printAllClient(CliControler cli) {
		try {
			List<Client>list =cli.clientService.findAll();

			for(Client client : list) {
				System.out.println(client);
			}

			//list.stream().forEach(System.out::println); //equivalent au for

		}catch(ServiceException e) {
			System.out.println("Unne erreur est survenue:"+e.getMessage());
		}
	}

}