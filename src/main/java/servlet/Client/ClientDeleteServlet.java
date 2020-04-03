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

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet{
	ClientService clientService = ClientService.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		System.out.println (request.getParameter("id"));
		int id = Integer.parseInt(request.getParameter("id"));
		
		
		try {
			request.setAttribute("client",clientService.delete(id));
			response.sendRedirect(request.getContextPath()+"/users");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage","erreur"+ e.getMessage());
		}
				
		
		//dispatcher.forward(request, response);
	}
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
}

}