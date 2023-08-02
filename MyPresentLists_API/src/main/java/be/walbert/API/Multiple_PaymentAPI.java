package be.walbert.API;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.walbert.Javabeans.Multiple_Payment_API;
import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Users_API;

@Path("/multiple_payment")
public class Multiple_PaymentAPI {
	
	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create_Meultiple_Payment(String json) {
	    if (json == null) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }
	    
	    try {
	    	 JSONObject jsonObject = new JSONObject(json);
	    	 double price_paid = jsonObject.getDouble("price_paid");	    	 
	    	 JSONObject user_object = jsonObject.getJSONObject("user");
	    	 int id_users = user_object.getInt("id_users");
	    	 
	    	 Users_API users = Users_API.find(id_users);
	    	 
	    	 JSONObject present_object = jsonObject.getJSONObject("present");
	    	 int id_present = present_object.getInt("id_present");
	    	 
	    	 Present_API present = Present_API.find(id_present);	    	 
	    	 Multiple_Payment_API multiple_payment = new Multiple_Payment_API(0,price_paid, present, users);
	    	    
	        if (!multiple_payment.create()) {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        } else {
	            return Response.status(Status.CREATED)
	                    .header("Location", "/MyPresentLists_API/api/multiple_payment/" + multiple_payment.getId_payment())
	                    .build();
	        }
			
		} catch (Exception ex) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }	    
	}
	
	
}
