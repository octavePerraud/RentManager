package servlet.Reservation;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Reservation;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;
@WebServlet("/rents/create")

public class ReservationCreateServlet extends HttpServlet{
	ReservationService reservationService = ReservationService.getInstance();
	VehicleService vehicleService = VehicleService.getInstance();
	ClientService clientService = ClientService.getInstance();
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/rents/reservationCreate.jsp");
		try {
			request.setAttribute("vehicles", vehicleService.findAll());
			request.setAttribute("clients", clientService.findAll());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage","erreur "+ e.getMessage());
		}
		dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int car = Integer.parseInt(request.getParameter("car"));
		int client =  Integer.parseInt(request.getParameter("client"));
		Date begin = Date.valueOf(request.getParameter("begin"));
		Date end = Date.valueOf(request.getParameter("end")); 

		Reservation newRes = new Reservation();
		newRes.setVehicle_id(car);
		newRes.setClient_id(client);
		newRes.setDebut(begin);
		newRes.setFin(end);
		RequestDispatcher dispatcher;
		
		
				try {
					reservationService.create(newRes);
					//dispatcher =request.getRequestDispatcher("/WEB-INF/views/home.jsp");
					response.sendRedirect(request.getContextPath()+"/rents");
					//response.sendRedirect(request.getContextPath()+"/rents");
				} catch (ServiceException e) {
					try {
						request.setAttribute("vehicles", vehicleService.findAll());
						request.setAttribute("clients", clientService.findAll());
					} catch (ServiceException e1) {
						// TODO Auto-generated catch block
						request.setAttribute("errorMessage","erreur"+ e1.getMessage());
						dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/reservationCreate.jsp");
					}
					request.setAttribute("errorMessage","erreur "+ e.getMessage());
					dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/reservationCreate.jsp");
					dispatcher.forward(request, response);
				}
			
		
		
	}


}


