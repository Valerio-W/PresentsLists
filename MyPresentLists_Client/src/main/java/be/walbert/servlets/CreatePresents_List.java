package be.walbert.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class CreatePresents_List extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreatePresents_List() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/CreatePresents_List.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String limit_dateStr = request.getParameter("limit_date");
	    LocalDate limit_date = LocalDate.parse(limit_dateStr);
	    String occasion = request.getParameter("occasion");
	     
	    Users u = (Users) request.getSession().getAttribute("user");
	    Presents_List new_list = new Presents_List(0,limit_date,occasion,true,u);
	
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double average_price = Double.parseDouble(request.getParameter("average_price"));
		int priority = Integer.parseInt(request.getParameter("priority"));
		String image = request.getParameter("image");
		String link = request.getParameter("link");

		Present present = new Present();
		if (image != "" && link != "") {
		    present = new Present(0,name, description, average_price, priority, "ordered", link,image, new_list);
		} else if (image != "") {
		    present = new Present(0,name, description, average_price, priority, "ordered", new_list,image);
		} else if (link != "") {
		    present = new Present(0,name, description, average_price, priority, "ordered", image, new_list);
		} else {
		    present = new Present(0,name, description, average_price, priority, "ordered", new_list);
		}
		new_list.addPresent(present);
		if(new_list.createPresents_List()) {
		    response.getWriter().append("liste ajoutée").append(request.getContextPath());   
		}
	    response.getWriter().append("liste non ajoutée").append(request.getContextPath());   


	}
}
