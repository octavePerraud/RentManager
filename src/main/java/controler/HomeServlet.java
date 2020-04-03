package controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;


@WebServlet("/home")
public class HomeServlet extends HttpServlet{
	ClientService clientService = ClientService.getInstance();
	VehicleService vehicleService = VehicleService.getInstance();
	ReservationService reservationService = ReservationService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/home.jsp");

		try{
			request.setAttribute("nbUtilisateurs", clientService.findAll().size());
			request.setAttribute("nbVehicles", vehicleService.findAll().size());
			request.setAttribute("nbReservations", reservationService.findAll().size());

		}catch(ServiceException e) {
			request.setAttribute("nbUtilisateurs", "une erreur est survenue : " + e.getMessage());
			
		}
		dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}