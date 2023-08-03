package be.walbert.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

@MultipartConfig
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
	
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double average_price = Double.parseDouble(request.getParameter("average_price"));
		int priority = Integer.parseInt(request.getParameter("priority"));
		String link = request.getParameter("link");
		// Get image file
		Part imagePart = request.getPart("image");
		InputStream imageStream = imagePart.getInputStream();
		byte[] image = imageStream.readAllBytes();

		// Close the image stream
		imageStream.close();

		// Add a delay before deleting the file
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Delete temp file
		imagePart.delete();
		
        //Set the list of errors
        ArrayList<String> errors = new ArrayList<String>();
        
        //Check if a field is null or empty
        if (limit_dateStr == null || occasion == null || name == null || description == null ||
        		limit_dateStr.isEmpty() || occasion.isEmpty()
                || name.isEmpty() || description.isEmpty()) {
            errors.add("A field is null or empty.");
        } else {
        	if(limit_date.isBefore(LocalDate.now().plusWeeks(1))) {
                errors.add("The limit_date must be in at least 1 week ");
        	}
            if (average_price<=0) {//Check the average_price
                errors.add("The average price must be greater than 0 ");
            }
            if (priority<=0) {//Check priority
                errors.add("Priority must be greater than 0");
            }
            if(description.length()>100) {
                errors.add("Description must not exceed 100 characters");
            }
        }

        if (errors.isEmpty()) {//If errors is empty (no errors)
        	Users u = (Users) request.getSession().getAttribute("user");	//Get the current User
    		Presents_List new_list = new Presents_List(0,limit_date,occasion,true,u); //Create Presents_List with data of form
        	Present present = new Present();// Create Present objet (without data)
     		present = new Present(0,name, description, average_price, priority, "ordered", link,image, new_list);
    		 
    		//Add present to the Presents_List object
    		new_list.getPresents().add(present);
    		if(new_list.createPresents_List()) {
                request.getSession().setAttribute("confirm_New_Presents_List", "Great, your new list has just been created");
                response.sendRedirect(request.getContextPath() + "/UserPage");
    		}
    		else {
    			getServletContext().getRequestDispatcher("/WEB-INF/Errors.jsp").forward(request, response);
			    return;
    		}
    	    response.getWriter().append("liste non ajoutée").append(request.getContextPath());   
        }
        else {
        	request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/CreatePresents_List.jsp").forward(request, response);
        }		
	}
}
