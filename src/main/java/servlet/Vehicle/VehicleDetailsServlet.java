package servlet.Vehicle;

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
import com.ensta.rentmanager.model.Vehicle;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

@WebServlet("/cars/details")

public class VehicleDetailsServlet extends HttpServlet{
	VehicleService vehicleService = VehicleService.getInstance();
	ReservationService reservationService = ReservationService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/vehicles/vehicleDetails.jsp");
		
		int id = Integer.parseInt(request.getParameter("id"));
	
		try {
		Optional <Vehicle> v = vehicleService.findById(id);
		if(v.isPresent()) {
			Vehicle  vehicle = v.get();
			request.setAttribute("vehicle", vehicle);
			
		}
		}catch (ServiceException e) {
			request.setAttribute("reservations", "une erreur est survenue : " + e.getMessage());
		}
		
		try{
			request.setAttribute("reservations", reservationService.findResaByVehicleId(id));

		}catch(ServiceException e) {
			request.setAttribute("reservations", "une erreur est survenue : " + e.getMessage());
		}
		dispatcher.forward(request, response);
			
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request,response);
	}

}
