package servlet.Reservation;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

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
@WebServlet("/rents/edit")

public class ReservationEditServlet extends HttpServlet{
	ReservationService reservationService = ReservationService.getInstance();
	VehicleService vehicleService = VehicleService.getInstance();
	ClientService clientService = ClientService.getInstance();
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/rents/reservationEdit.jsp");
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Optional <Reservation> r = reservationService.findById(id);
			if(r.isPresent()) {
				Reservation reservation= r.get();
				request.setAttribute("reservation", reservation);
			
			}
			}catch(ServiceException e) {
				request.setAttribute("errorMessage","erreur"+ e.getMessage());
			}
		try {
			
			request.setAttribute("vehicles", vehicleService.findAll());
			request.setAttribute("clients", clientService.findAll());
		} catch (ServiceException e) {
			request.setAttribute("errorMessage","erreur"+ e.getMessage());
		}
		dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int id= Integer.parseInt(request.getParameter("id"));
		int car = Integer.parseInt(request.getParameter("car"));
		int client =  Integer.parseInt(request.getParameter("client"));
		Date begin = Date.valueOf(request.getParameter("begin"));
		Date end = Date.valueOf(request.getParameter("end")); 

		Reservation Res = new Reservation();
		Res.setId(id);
		Res.setVehicle_id(car);
		Res.setClient_id(client);
		Res.setDebut(begin);
		Res.setFin(end);
		RequestDispatcher dispatcher;
		
				try {
					reservationService.edit(Res);
					response.sendRedirect(request.getContextPath()+"/rents");
					//dispatcher =request.getRequestDispatcher("/WEB-INF/views/home.jsp");
				} catch (ServiceException e) {
					try {
						request.setAttribute("vehicles", vehicleService.findAll());
						request.setAttribute("clients", clientService.findAll());
					} catch (ServiceException e1) {
						// TODO Auto-generated catch block
						request.setAttribute("errorMessage","erreur"+ e1.getMessage());
					}
					
					request.setAttribute("errorMessage","erreur"+ e.getMessage());
					dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/reservationEdit.jsp");
					dispatcher.forward(request, response);
				}
			
		
		
	}


}


