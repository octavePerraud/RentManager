package servlet.Vehicle;

import java.io.IOException;
import java.sql.Date;
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
import com.epf.RentManager.service.VehicleService;



@WebServlet("/cars/edit")

public class VehicleEditServlet extends HttpServlet{
	VehicleService vehicleService = VehicleService.getInstance();
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/vehicles/vehicleEdit.jsp");
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Optional<Vehicle> v;
		try {
			v = vehicleService.findById(id);
			if(v.isPresent()) {
				Vehicle  vehicle= v.get();
				request.setAttribute("vehicle", vehicle);
			
			}
		} catch (ServiceException e) {
			request.setAttribute("reservations", "une erreur est survenue : " + e.getMessage());
		}
		
		
		dispatcher.forward(request, response);
	}
protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	int id = Integer.parseInt(request.getParameter("id"));
	String constructeur = request.getParameter("constructeur");
	String modele = request.getParameter("modele");
	int seats = Integer.parseInt(request.getParameter("seats"));
	
	
	Vehicle vehicle = new Vehicle();
	
	
	vehicle.setId(id);
	vehicle.setConstructeur(constructeur);
	vehicle.setModele(modele);
	vehicle.setNb_places(seats);
	RequestDispatcher dispatcher;
	
	try {
		vehicleService.edit(vehicle);
		response.sendRedirect(request.getContextPath()+"/cars");
		//dispatcher =request.getRequestDispatcher("/WEB-INF/views/home.jsp");
	} catch (ServiceException e) {
		request.setAttribute("vehicle", vehicle);
		request.setAttribute("errorMessage","erreur"+ e.getMessage());
		dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/vehicleEdit.jsp");
		dispatcher.forward(request, response);
	}
	
	
	}

}
