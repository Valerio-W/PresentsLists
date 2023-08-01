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

public class Get_Details_of_Present extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Get_Details_of_Present() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	        
			HttpSession session = request.getSession(false); 
			Users user = (Users) session.getAttribute("user");
	        Present present = new Present();
	        
	        present = Present.find(Integer.parseInt(request.getParameter("id_present")));
	        if(present!= null) {
	        	 if(present.getList().isOwnerOrGuest(user)) {
	        		 request.setAttribute("present", present);
		             getServletContext().getRequestDispatcher("/WEB-INF/Get_Details_of_Present.jsp").forward(request, response);
	        	 }
	        	 else {
						request.getSession().setAttribute("errorNotAllow", "Sorry, you can access to this present, you are not the owner or a guest!");
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
