package servlet.Client;

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
import com.ensta.rentmanager.model.Client;
import com.epf.RentManager.service.ClientService;



@WebServlet("/users/edit")

public class ClientEditServlet extends HttpServlet{
	ClientService clientService = ClientService.getInstance();
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher =request.getRequestDispatcher("/WEB-INF/views/users/clientEdit.jsp");
		
		int id = Integer.parseInt(request.getParameter("id"));
		try {
		Optional <Client> c = clientService.findById(id);
		if(c.isPresent()) {
			Client  client= c.get();
			request.setAttribute("client", client);
		
		}
		}catch(ServiceException e) {
			e.printStackTrace();
		}
		//int id1 = Integer.parseInt(request.getParameter("id"));
		
		
		
		
		
		dispatcher.forward(request, response);
	}
protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	int id = Integer.parseInt(request.getParameter("id"));
	String last_name = request.getParameter("last_name");
	String first_name = request.getParameter("first_name");
	String email = request.getParameter("email");
	Date birthdate = Date.valueOf(request.getParameter("birthdate")); 
	
	
	Client client = new Client();
	client.setId(id);
	
	client.setEmail(email);
	client.setNom(last_name);
	client.setPrenom(first_name);
	client.setNaissance(birthdate);
	RequestDispatcher dispatcher;
	
	try {
		clientService.edit(client, id);
		response.sendRedirect(request.getContextPath()+"/users");
		//dispatcher =request.getRequestDispatcher("/WEB-INF/views/home.jsp");
	} catch (ServiceException e) {
		request.setAttribute("client", client);
		request.setAttribute("errorMessage","erreur"+ e.getMessage());
		//response.sendRedirect(request.getContextPath()+"/users/edit");
		dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/clientEdit.jsp");
		dispatcher.forward(request, response);
	}
	
	
	}

}
