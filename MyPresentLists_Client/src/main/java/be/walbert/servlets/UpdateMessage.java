package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Message;
import be.walbert.javabeans.Users;

public class UpdateMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateMessage() {
        super();
     }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        String idMessageParam = request.getParameter("id");
            HttpSession session = request.getSession(false);
            
            if (session != null && idMessageParam != null && !idMessageParam.isEmpty()) {
                int id_message = Integer.parseInt(request.getParameter("id"));
                Users currentUser = (Users) session.getAttribute("user");
                if (currentUser != null) {
                    Message message_Updated = Message.find(id_message);
                    if(message_Updated.getUser().getId()== currentUser.getId()) {
                    	message_Updated.setChecked(false);
                        if(message_Updated.update()) {
                 			getServletContext().getRequestDispatcher("/WEB-INF/UserPage.jsp").forward(request, response);
                        }
                    }
                    else {
             			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
                    }
                }
            }
            else {
     			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
            }

         } catch (Exception e) {
 			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
