package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Users;

public class Get_Presents_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Get_Presents_List() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session != null) {
				Users currentUser = (Users) session.getAttribute("user");
				if(currentUser != null) {
					session.setAttribute("user", Users.find(currentUser.getId()));
					getServletContext().getRequestDispatcher("/WEB-INF/Get_Presents_List.jsp").forward(request, response);
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
