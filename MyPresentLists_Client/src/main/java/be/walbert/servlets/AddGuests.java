package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Message;
import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class AddGuests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddGuests() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	      
			HttpSession session = request.getSession(false); 
	        Presents_List presents_list = new Presents_List();
	        presents_list.setId_list(Integer.parseInt(request.getParameter("id")));
	        
	        presents_list = Presents_List.find(Integer.parseInt(request.getParameter("id")));

	        if(presents_list != null) {
	        	Users user = (Users) session.getAttribute("user");
	        	if(presents_list.getOwner().getId()== user.getId()) {
		        	// Create new session with id_list
			        HttpSession newSession = request.getSession(true);
		        	newSession.setAttribute("list", presents_list);
		            getServletContext().getRequestDispatcher("/WEB-INF/AddGuest.jsp").forward(request, response);
		         }
	        	else {
					request.getSession().setAttribute("errorNotOwnerofList", "Sorry, you can modify this list, you are not the owner!");
					getServletContext().getRequestDispatcher("/WEB-INF/UserPage.jsp").forward(request, response);
	        	}
	        }
	        
		} catch (Exception e) {
			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); 
		if (session != null && session.getAttribute("list") != null) {
			Presents_List list = (Presents_List) session.getAttribute("list");
			Users users_existing = new Users();
			users_existing.setPseudo(request.getParameter("pseudo"));
			if(users_existing.findExistingUsers() != null) {
				list.addGuest(users_existing.findExistingUsers());
				Message newMessage = new Message(0, "You have been invited to the list of "+list.getOwner().getPseudo(), true, users_existing.findExistingUsers());
				if(newMessage.create()) {
					if(list.update_PresentsList()) {
						request.getSession().setAttribute("guestAdded", "Great, the user has been invited as guest ! "
								+ "There is the link to access to your presents list : "+ 
								"http://localhost:8080"+ request.getContextPath() + "/Get_Details_of_PresentsList"+"?id="+list.getId_list()
								+"(If the user launches the program on a port other than 8080, he will have to modify the port number of the link).");
						getServletContext().getRequestDispatcher("/WEB-INF/UserPage.jsp").forward(request, response);
					}
					else {
						getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
					}
				}
			}else {
				request.getSession().setAttribute("errorUsersNotFound", "Sorry, no Users found with this pseudo");
	            response.sendRedirect(request.getContextPath() + "/AddGuests"+"?id="+list.getId_list());
			}
		}
		else {
			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
		}
	}
}
