package servlet.Reservation;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.epf.RentManager.service.ReservationService;

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet{
	ReservationService reservationService = ReservationService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/rents/reservationCreate.jsp");

		int id = Integer.parseInt(request.getParameter("id"));
		
		try {
			request.setAttribute("client", reservationService.delete(id));
			response.sendRedirect(request.getContextPath()+"/rents");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage","erreur"+ e.getMessage());
			//dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/reservationList.jsp");
			response.sendRedirect(request.getContextPath()+"/rents");
		}
				
		//c = clientService.findById(id);
		
		//dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
}

}