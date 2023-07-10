package be.walbert.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.walbert.javabeans.Users;

public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Signin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Signin.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");
	     
	  //Will store the list of errors.
	  ArrayList<String> errors = new ArrayList<String>();
	  		
	  if(pseudo==null || password == null || email == null) {	  
		errors.add("A field is null.");
	  }
	  if (pseudo.equals("") || password.equals("") || email.equals("")) {
	    errors.add("A field is empty.");
	  }
	  if(!pseudo.matches("^[0-9a-zA-Z]{5,}$")) {
		    errors.add("Pseudo must contain at least 5 characters.");
	  }
	  if(!password.matches("^[0-9a-zA-Z]{4,}$")) {
		    errors.add("Password must contain at least 4 characters.");
	  }
	  
	  Users u = new Users(0, pseudo, password, email);

	  if (request.getParameter("submit") == null) {
		  if (errors.size() < 0) {
			  if(u.Signin()) {
		    	request.getSession().setAttribute("confirmAccount", "Great, your account has just been created");
		    	response.sendRedirect(request.getContextPath() + "/HomePage");
			  }
			  else {
				getServletContext().getRequestDispatcher("/WEB-INF/Errors.jsp").forward(request, response);
			    return;
			  }
		  } else {
			  request.setAttribute("errors", errors);
			  getServletContext().getRequestDispatcher("/WEB-INF/Signin.jsp").forward(request, response);
		  }
	  }
	}
}
