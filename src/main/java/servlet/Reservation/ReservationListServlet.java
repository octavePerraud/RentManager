package servlet.Reservation;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
@WebServlet("/rents")

public class ReservationListServlet extends HttpServlet{
	ReservationService reservationService = ReservationService.getInstance();
	ClientService clientService = ClientService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/rents/reservationList.jsp");

		
		
		
		try{
			request.setAttribute("reservations", reservationService.findAll());
			
			List<Reservation> res = reservationService.findAll();
			List<Client> c = clientService.findAll();
			
			//if( == res.get(2))
			System.out.println(res);
			System.out.println(c);

		}catch(ServiceException e) {
			request.setAttribute("reservations", "une erreur est survenue : " + e.getMessage());
		}
		
		/*try {
			List<Reservation> res = reservationService.findAll();
			int i = res.client_id;
			Optional <Client> c = clientService.findById(i);
			if(c.isPresent()) {
				Client  client = c.get();
				request.setAttribute("client", client);
				
			}
			}catch(ServiceException e) {
				e.printStackTrace();
			}*/
			
		dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
