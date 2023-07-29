package be.walbert.servlets;

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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

@MultipartConfig
public class CreatePresent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreatePresent() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);  
            
            // Check if id is already in session
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("id") != null) {
                // Remove "id" attribute from session
                session.removeAttribute("id");
            }
            
            // Create new session with id_list
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("id", id);
            
	        Presents_List presents_list = new Presents_List();
	        presents_list.setId_list(Integer.parseInt(request.getParameter("id")));
	        
	        presents_list = Presents_List.find(Integer.parseInt(request.getParameter("id")));

	        if(presents_list != null) {
	        	Users user = (Users) session.getAttribute("user");
	        	if(presents_list.getOwner().getId()== user.getId()) {
	        		RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/CreatePresent.jsp");
	        		dispatcher.forward(request, response);
	        	}
	        	else {
					request.getSession().setAttribute("errorNotOwnerofList", "Sorry, you can modify this list, you are not the owner!");
					getServletContext().getRequestDispatcher("/WEB-INF/UserPage.jsp").forward(request, response);
	        	}
	        
	        } 
	        else { 
	        	RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/Error.jsp");
	        	dispatcher.forward(request, response);
	        }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("id") != null) {
            int id_list = (int) session.getAttribute("id");
            
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
            	Presents_List present_list = new Presents_List();
            	present_list.setId_list(id_list);

            	Present present = new Present();// Create Present objet (without data)
         		present = new Present(0,name, description, average_price, priority, "ordered", link,image, present_list);
        		
         		if(present.createPresent()) {
         			request.getSession().setAttribute("confirm_New_Present", "Great, your new present has just been added");
                    response.sendRedirect(request.getContextPath() + "/Get_Presents_List");
        		}
        		else {
        			getServletContext().getRequestDispatcher("/WEB-INF/Errors.jsp").forward(request, response);
    			    return;
        		}
            }
            else {
            	request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/CreatePresent.jsp").forward(request, response);
            }		
        } else {
            request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
        }
    }

}
