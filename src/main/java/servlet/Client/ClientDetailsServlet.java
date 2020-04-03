package servlet.Client;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
@WebServlet("/users/details")

public class ClientDetailsServlet extends HttpServlet{
	ClientService clientService = ClientService.getInstance();
	ReservationService reservationService = ReservationService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/users/clientDetails.jsp");
		
		int id = Integer.parseInt(request.getParameter("id"));
	
		try {
		Optional <Client> c = clientService.findById(id);
		if(c.isPresent()) {
			Client  client = c.get();
			request.setAttribute("client", client);
			
		}
		}catch(ServiceException e) {
			request.setAttribute("client", "une erreur est survenue : " + e.getMessage());
		}
		
		try{
			request.setAttribute("reservations", reservationService.findResaByClientId(id));

		}catch(ServiceException e) {
			request.setAttribute("reservations", "une erreur est survenue : " + e.getMessage());
		}
		dispatcher.forward(request, response);
		
		
		
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request,response);
	}

}
