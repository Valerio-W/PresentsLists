package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Users;

public class Get_Guest_Lists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Get_Guest_Lists() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session != null) {
				Users currentUser = (Users) session.getAttribute("user"); 
				if(currentUser != null) {
					request.setAttribute("presentsLists", currentUser.getLists());
					session.setAttribute("user", Users.login(currentUser.getPseudo(), currentUser.getPassword()));
					getServletContext().getRequestDispatcher("/WEB-INF/Get_Guest_Lists.jsp").forward(request, response);
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