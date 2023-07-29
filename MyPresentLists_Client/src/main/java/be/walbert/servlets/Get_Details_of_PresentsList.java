package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class Get_Details_of_PresentsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Get_Details_of_PresentsList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	        
			HttpSession session = request.getSession(false); 
			Users user = (Users) session.getAttribute("user");
	        Presents_List presents_list = new Presents_List();
	        presents_list.setId_list(Integer.parseInt(request.getParameter("id")));
	        
	        presents_list = Presents_List.find(Integer.parseInt(request.getParameter("id")));
	        presents_list.sortPresents_By_Priority();
	        if(presents_list != null) {
	        	 if(presents_list.isOwnerOrGuest(user)) {
	        		 request.setAttribute("presents_list", presents_list);
		             getServletContext().getRequestDispatcher("/WEB-INF/Get_Details_of_PresentsList.jsp").forward(request, response);
	        	 }
	        	 else {
						request.getSession().setAttribute("errorNotAllow", "Sorry, you can access to this list, you are not the owner or a guest!");
						getServletContext().getRequestDispatcher("/WEB-INF/UserPage.jsp").forward(request, response);
		        	}
	        }
		} catch (Exception e) {
			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
