package be.walbert.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class UpdatePresentsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdatePresentsList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {   
	    	HttpSession session = request.getSession(false);
	    	Presents_List presents_list = new Presents_List();
	        presents_list.setId_list(Integer.parseInt(request.getParameter("id")));
	        
	        presents_list = Presents_List.find(Integer.parseInt(request.getParameter("id")));
	        presents_list.sortPresents_By_Priority();
	        
	        if(presents_list != null) {
	        	Users user = (Users) session.getAttribute("user");
	        	if(presents_list.getOwner().getId()== user.getId()) {
	        		session.setAttribute("presents_list", presents_list);
		             getServletContext().getRequestDispatcher("/WEB-INF/UpdatePresentsList.jsp").forward(request, response);
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
		try {
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute("presents_list") != null) {
	        	Presents_List presents_list = (Presents_List) session.getAttribute("presents_list");

				String limit_dateStr = request.getParameter("limit_date");
			    LocalDate limit_date = LocalDate.parse(limit_dateStr);
		        if (!limit_date.isBefore(LocalDate.now().plusWeeks(1))) {
				    presents_list.setLimit_date(limit_date);
				    if(presents_list.update_PresentsList()) {
				    	request.getSession().setAttribute("confirmUpdatedList", "Great, your list has just been updated");
		                response.sendRedirect(request.getContextPath() + "/UserPage");
				    }
				    else {
						getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
				    }
			    }
			    else {
			    	request.getSession().setAttribute("errorLimitDate", "Sorry the limit_date must be in at least 1 week");
	                response.sendRedirect(request.getContextPath() + "/UpdatePresentsList"+"?id="+presents_list.getId_list());
			    } 
			}
			else {
				getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
		}
	}

}
