package servlet.Reservation;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.model.Vehicle;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;



@WebServlet("/rents/details")

public class ReservationDetailsServlet extends HttpServlet{
	ReservationService reservationService = ReservationService.getInstance();
	ClientService clientService = ClientService.getInstance();
	VehicleService vehicleService = VehicleService.getInstance();
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/rents/reservationDetails.jsp");


		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Optional<Reservation> r = reservationService.findById(id);
			if(r.isPresent()) {
				Reservation  resa = r.get();
				request.setAttribute("reservation", resa);
				
				Optional <Client> c = clientService.findById(resa.getClient_id());
				if(c.isPresent()) {
					Client  client = c.get();
				request.setAttribute("client",  client );
				}
				
				Optional <Vehicle> v = vehicleService.findById(resa.getVehicle_id());
				if(v.isPresent()) {
					Vehicle  vehicle = v.get();
					request.setAttribute("vehicle", vehicle);
					}
				
				//request.setAttribute("client",clientService.findById(resa.getClient_id()));
				//request.setAttribute("vehicle", vehicleService.findById(resa.getVehicle_id()));
				

			}
		} catch (ServiceException e) {
			request.setAttribute("reservation", "une erreur est survenue : " + e.getMessage());
		}
		
		dispatcher.forward(request, response);
		

	}

	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		doGet(request,response);
	}

}




