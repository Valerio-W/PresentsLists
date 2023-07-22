package be.walbert.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;

@MultipartConfig
public class UpdatePresent extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdatePresent() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ServletContext context = getServletContext();
	     String id_present_Param = request.getParameter("id_present");
	     if (id_present_Param != null && !id_present_Param.isEmpty()) {
	        int id_present = Integer.parseInt(id_present_Param);  
	            
	        // Check if id is already in session
	        HttpSession session = request.getSession(false);
	        if (session != null && session.getAttribute("id_present") != null) {
	        // Remove "id" attribute from session
	            session.removeAttribute("id_present");
	        }
	            
	        // Create new session with id_list
	        HttpSession newSession = request.getSession(true);
	        Present present = new Present();
	        present.setId_present(id_present);
	        present = Present.find(present.getId_present());
	        newSession.setAttribute("present", present);
	            
	        RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/UpdatePresent.jsp");
	        dispatcher.forward(request, response);
	      } else { 
	        RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/Error.jsp");
	        dispatcher.forward(request, response);
	        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); 
		if (session != null && session.getAttribute("present") != null) {
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
	            if ( name == null || description == null || name.isEmpty() || description.isEmpty()) {
	                errors.add("A field is null or empty.");
	            } else {
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
	            	Present present = (Present) request.getSession().getAttribute("present");

	            	Present present_updated = new Present(present.getId_present(),name, description, average_price, priority, present.getState(), link,image, present.getList());
	            	
	            	if(present_updated.updatePresent()) {
	            		request.getSession().setAttribute("confirm_Present_Updated", "Great, your present has just been updated");
	                    response.sendRedirect(request.getContextPath() + "/Get_Presents_List");
	        		}
	            	else {
	        			getServletContext().getRequestDispatcher("/WEB-INF/Errors.jsp").forward(request, response);
	    			    return;
	        		}
	            }
	            else {
	            	request.setAttribute("errors", errors);
	                request.getRequestDispatcher("/WEB-INF/UpdatePresent.jsp").forward(request, response);
	            }	
		}
		else {
			request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
		}
	}
}
