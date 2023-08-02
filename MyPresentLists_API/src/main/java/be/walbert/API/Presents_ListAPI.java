package be.walbert.API;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;

@Path("/presents_list")
public class Presents_ListAPI {
	
	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create_PresentsList(String json) {
	    if (json == null) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }

	    try {
	    	 JSONObject jsonObject = new JSONObject(json);
	    	 String occasion = jsonObject.getString("occasion");
	    	 boolean state = jsonObject.getBoolean("state");
	    	 JSONObject limitDateObject = jsonObject.getJSONObject("limit_date");
	    	 int year = limitDateObject.getInt("year");
	    	 String monthString = limitDateObject.getString("month");
	    	 int day = limitDateObject.getInt("dayOfMonth");
	    	 Month month = Month.valueOf(monthString.toUpperCase());  
	    	 LocalDate limitDate = LocalDate.of(year, month, day);
	    	 
	    	 JSONObject userObject = jsonObject.getJSONObject("owner");
	    	 Users_API user = new Users_API();
	    	 user.setId(userObject.getInt("id_users"));
	    	 
	    	 Presents_List_API presents_list = new Presents_List_API(0, limitDate, occasion, state, user);
	    	 Present_API first_present = new Present_API();
	    	 JSONArray presentsArray = jsonObject.getJSONArray("presents");
	    	 for (int i = 0; i < presentsArray.length(); i++) {
	    		 JSONObject presentObject = presentsArray.getJSONObject(i);
	    	     String name = presentObject.getString("name");
	    	     String description = presentObject.getString("description");
	    	     double average_price = presentObject.getDouble("average_price");
	    	     int priority = presentObject.getInt("priority");
	    	     String statePresent = presentObject.getString("state");
	    	     String link = presentObject.getString("link"); 
	    	     String imageBase64 = presentObject.getString("image");
	    	     byte[] image = Base64.getDecoder().decode(imageBase64);

		    	 first_present = new Present_API(0, name, description, average_price, priority, statePresent, link, image, presents_list);
	    	 }
	    	 
	    	 presents_list.addPresent(first_present);
	    	 if (!presents_list.create()) {
	    		 return Response.status(Status.SERVICE_UNAVAILABLE).build();
	    	 } else {
	    		 return Response.status(Status.CREATED)
	    				.header("Location", "/MyPresentLists_API/api/presents_list/" + presents_list.getId_list())
	                    .build();
	        }
	    } catch (Exception ex) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response find_Presents_List(@PathParam("id") int id_list) {
		Presents_List_API presents_list = new Presents_List_API();
		try {
			presents_list = Presents_List_API.find(id_list);

			if (presents_list == null) {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}

		} catch (Exception e) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.OK).entity(presents_list).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public Response find_allPresentsLists() {
		ArrayList<Presents_List_API> allLists = new ArrayList<>();
		 
		try {
	        allLists = Presents_List_API.findAll(); 

			if (allLists == null) {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}

		} catch (Exception e) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.OK).entity(allLists).build();
	}
	
	@Path("/update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
 	public Response updatePresentsList(String json) {
	    if (json == null) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }

	    try {
	        JSONObject jsonObject = new JSONObject(json);

	        int idList = jsonObject.getInt("id_list");
	        String occasion = jsonObject.getString("occasion");
	        boolean state = jsonObject.getBoolean("state");
	        JSONObject limitDateObject = jsonObject.getJSONObject("limit_date");
	        int year = limitDateObject.getInt("year");
	        String monthString = limitDateObject.getString("month");
	        int day = limitDateObject.getInt("dayOfMonth");
	        Month month = Month.valueOf(monthString.toUpperCase());  
	        LocalDate limitDate = LocalDate.of(year, month, day);
	        
	        JSONObject userObject = jsonObject.getJSONObject("owner");
	        Users_API user = new Users_API();
	        user.setId(userObject.getInt("id_users"));
	       
	        Presents_List_API presentsList = new Presents_List_API(idList, limitDate, occasion, state, user);

    	    JSONArray guestsArray = jsonObject.getJSONArray("guests");
    	    
    	    for (int i = 0; i < guestsArray.length(); i++) {
    	        JSONObject guestsObject = guestsArray.getJSONObject(i);

    	        int id_guest = guestsObject.getInt("id_users");
    	        String pseudo =guestsObject.getString("pseudo");
    	        String password = guestsObject.getString("password");
    	        String email=guestsObject.getString("email");
    	         
    	        Users_API new_guest = new Users_API(id_guest, pseudo, password, email);
    	        presentsList.addGuest(new_guest);
     	    }
	        
	        if (!presentsList.update()) {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        } else {
	            return Response.status(Status.OK).build();
	        }
	    } catch (Exception ex) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	
}
