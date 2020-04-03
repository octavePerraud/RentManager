
package servlet.Vehicle;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Vehicle;
import com.epf.RentManager.service.VehicleService;
@WebServlet("/cars/create")

public class VehicleCreateServlet extends HttpServlet{
	VehicleService vehicleService = VehicleService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/vehicles/vehicleCreate.jsp");

		dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String modele = request.getParameter("modele");
		int seats = Integer.parseInt(request.getParameter("seats"));
		String marque = request.getParameter("manufacturer"); 

		Vehicle newVehicle = new Vehicle();
		newVehicle.setConstructeur(marque);
		newVehicle.setModele(modele);
		newVehicle.setNb_places(seats);

		RequestDispatcher dispatcher;
		try {
			vehicleService.create(newVehicle);
			response.sendRedirect(request.getContextPath()+"/cars");
			//dispatcher =request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		}catch(ServiceException e){
			request.setAttribute("errorMessage","erreur"+ e.getMessage());
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/vehicleCreate.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
