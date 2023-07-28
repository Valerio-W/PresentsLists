package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	        Presents_List presents_list = new Presents_List();
	        presents_list.setId_list(Integer.parseInt(request.getParameter("id")));
	        
	        presents_list = Presents_List.find(Integer.parseInt(request.getParameter("id")));

	        if(presents_list != null) {
	        	 // Create new session with id_list
		        HttpSession newSession = request.getSession(true);
	        	newSession.setAttribute("list", presents_list);
	            getServletContext().getRequestDispatcher("/WEB-INF/AddGuest.jsp").forward(request, response);
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
			if(users_existing.isUsersExist() != null) {
				list.addGuest(users_existing.isUsersExist());
				if(list.update_PresentsList()) {
					response.getWriter().append("Guest added !");
				}
				else {
					getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
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
