package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.walbert.javabeans.Presents_List;

public class Get_Details_of_PresentsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Get_Details_of_PresentsList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	        
	        Presents_List presents_list = new Presents_List();
	        presents_list.setId_list(Integer.parseInt(request.getParameter("id")));
	        
	        presents_list = Presents_List.find(Integer.parseInt(request.getParameter("id")));
	        presents_list.sortPresents_By_Priority();
	        if(presents_list != null) {
	        	 request.setAttribute("presents_list", presents_list);
	             getServletContext().getRequestDispatcher("/WEB-INF/Get_Details_of_PresentsList.jsp").forward(request, response);
	         }
		} catch (Exception e) {
			getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
