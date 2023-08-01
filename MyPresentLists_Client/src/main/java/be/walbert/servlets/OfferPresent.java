package be.walbert.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.walbert.javabeans.Multiple_Payment;
import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;


public class OfferPresent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OfferPresent() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
			 HttpSession session = request.getSession(false);
	 		 if(session != null) {
	 			 Users currentUser = (Users) session.getAttribute("user"); 
	 			 Present present = Present.find(Integer.parseInt(request.getParameter("id_present")));
	 			 
	 			 if(currentUser != null && present != null && present.getList().isState()) {
	 				 session.setAttribute("present", present);
	 				 getServletContext().getRequestDispatcher("/WEB-INF/OfferPresent.jsp").forward(request, response);
	 			 }
	 			 else {
	 		 		getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
	 			 }
	 		 }
	 	}catch (Exception e) {
	 		getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
	 	}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
	 		 if(session != null) {
	 			 Users currentUser = (Users) session.getAttribute("user"); 
	 			 Present currentPresent = (Present) session.getAttribute("present"); 
	 			 String offerOrPayment = request.getParameter("offerOrPayment"); 
	             Present present = (Present) session.getAttribute("present");
	             
	 	        if (offerOrPayment != null) {
	 	            if (offerOrPayment.equals("offer")) {
	 	            	if(present.getState().equals("ordered")) {
	 	            		if(present.getPayments().size()==0) {
	 	            			present.setState("reserved");
			 	            	if(present.updatePresent()) {
				 			        getServletContext().getRequestDispatcher("/WEB-INF/UserPage.jsp").forward(request, response);
			 	            	}
			 	            	else{
			 		 		        getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
			 	            	}	
	 	            		}
	 	            		else {
	 		            		request.getSession().setAttribute("error_already_multiple_payments", "Sorry, this present is already paid by others users");
	 	 		 		        getServletContext().getRequestDispatcher("/WEB-INF/OfferPresent.jsp").forward(request, response);
	 	 	                }
	 	            	}
	 	            	else {
 		            		request.getSession().setAttribute("error_already_reserved", "Sorry, this present is already reserved");
 	 		 		        getServletContext().getRequestDispatcher("/WEB-INF/OfferPresent.jsp").forward(request, response);
 	 	                }
	 	            } else if (offerOrPayment.equals("payment")) {
	 	            	if(request.getParameter("price_paid")!= null && !request.getParameter("price_paid").isEmpty()) {
	 	            		double price_paid = Double.parseDouble(request.getParameter("price_paid"));
	 	            		if(present.total_price_paid()!= present.getAverage_price()) {
		 	                	if(price_paid>0 && present.getAverage_price()>= price_paid && present.total_price_paid()+price_paid<=present.getAverage_price()) {
		 	                		Multiple_Payment multiple_Payment = new Multiple_Payment(0,price_paid,currentPresent,currentUser);
		 	                		if(multiple_Payment.createMultiplePayment()) {
		 		 	                	present = Present.find(present.getId_present());
		 	                			if(present.getAverage_price()==present.total_price_paid()) {
		 	                				present.setState("fully paid");
		 	                				if(present.updatePresent()) {
		 	                					request.getSession().setAttribute("multiple_paymentAdded", "Great, your multiple_payment has been created for the present !");
				 		 	                	getServletContext().getRequestDispatcher("/WEB-INF/UserPage.jsp").forward(request, response);		 		 	              
		 	                				}
		 	                			} 
		 	                		}
		 	 	                } 
		 	 	                else {
		 		            		request.getSession().setAttribute("error_price_paid", "sorry the price you entered is not correct (less than 0 or too much for the price he reset to pay)");
		 	 		 		        getServletContext().getRequestDispatcher("/WEB-INF/OfferPresent.jsp").forward(request, response);
		 	 	                }	 	                	
		 	                }else {
		 	                	request.getSession().setAttribute("error_present_already_fullpaid", "Sorry, this present is already full paid.");
	 	 		 		        getServletContext().getRequestDispatcher("/WEB-INF/OfferPresent.jsp").forward(request, response);
		 	                }
	 	            	}
	 	            	else {
	 	                	request.getSession().setAttribute("error_price_paid_empty", "Sorry, you must indicate a price paid.");
 	 		 		        getServletContext().getRequestDispatcher("/WEB-INF/OfferPresent.jsp").forward(request, response);
	 	                }
	 	            }
	 	        } else {
	 		        getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
	 	        }
	 		 }
	       
	    } catch (Exception e) {
	        getServletContext().getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
	    }
	}

}
