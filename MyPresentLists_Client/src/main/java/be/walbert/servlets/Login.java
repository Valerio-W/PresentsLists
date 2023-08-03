package be.walbert.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Message;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
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
	    	HttpSession session = request.getSession(false);
	    	Integer id_guest_list = (Integer) session.getAttribute("id_guest_list");

	        if (id_guest_list != null) {
	        	request.getSession().setAttribute("user", user);
				Presents_List list = Presents_List.find(id_guest_list);
				if(list != null) {
					if(!list.isOwnerOrGuest(user)) {
						list.getGuests().add(user);
						Message newMessage = new Message(0, "You have been invited to the list of "+list.getOwner().getPseudo(), true, user);
						if(newMessage.create()) {
							if(list.update_PresentsList()) {
								request.getSession().setAttribute("user", user);
						    	ArrayList<Presents_List> all_presentsList = Presents_List.findAllLists();
						    	for (Presents_List presents_List : all_presentsList) {
									presents_List.update_PresentsList();
								}
								getServletContext().getRequestDispatcher("/WEB-INF/Get_Messages.jsp").forward(request, response);
							}
							else {
								getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
							}
						}
					}else {
						request.getSession().setAttribute("user", user);
				    	ArrayList<Presents_List> all_presentsList = Presents_List.findAllLists();
				    	for (Presents_List presents_List : all_presentsList) {
							presents_List.update_PresentsList();
						}
				        response.sendRedirect(request.getContextPath() + "/UserPage");
			        }    
				}else {
					request.getSession().setAttribute("user", user);
			    	ArrayList<Presents_List> all_presentsList = Presents_List.findAllLists();
			    	for (Presents_List presents_List : all_presentsList) {
						presents_List.update_PresentsList();
					}
			        response.sendRedirect(request.getContextPath() + "/UserPage");
				}
					
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

}
