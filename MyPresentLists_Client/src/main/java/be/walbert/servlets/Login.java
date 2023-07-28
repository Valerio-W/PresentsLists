package be.walbert.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Login.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String pseudo = request.getParameter("pseudo");
	     String password = request.getParameter("password");
	      
	    Users user = Users.login(pseudo, password);
	    
	    if(user==null) {
	        request.getSession().setAttribute("userNotFound", "Sorry, no account found.");
	        response.sendRedirect(request.getContextPath() + "/Login");
	    }
	    else {
	    	request.getSession().setAttribute("user", user);
	    	ArrayList<Presents_List> all_presentsList = Presents_List.findAllLists();
	    	for (Presents_List presents_List : all_presentsList) {
				presents_List.update_PresentsList();
			}
	        response.sendRedirect(request.getContextPath() + "/UserPage");
	    }
	}

}
