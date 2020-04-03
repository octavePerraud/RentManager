package servlet.Client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.epf.RentManager.service.ClientService;
@WebServlet("/users")

public class ClientListServlet extends HttpServlet{
	ClientService clientService = ClientService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/users/clientList.jsp");

		try{
			request.setAttribute("clients", clientService.findAll());

		}catch(ServiceException e) {
			request.setAttribute("clients", "une erreur est survenue : " + e.getMessage());
		}
		dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
