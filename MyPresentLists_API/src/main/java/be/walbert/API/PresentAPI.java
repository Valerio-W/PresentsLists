package be.walbert.API;

import java.time.LocalDate;
import java.time.Month;
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

import org.json.JSONObject;

import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;

@Path("/present")
public class PresentAPI {

	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create_Present(String json) {
	    if (json == null) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }

	    try {
	    	 JSONObject jsonObject = new JSONObject(json);

	    	    String name = jsonObject.getString("name");
	    	    String description = jsonObject.getString("description");
	    	    double average_price = jsonObject.getDouble("average_price");
	    	    int priority = jsonObject.getInt("priority");
	    	    String state = jsonObject.getString("state");
	    	    String link = jsonObject.getString("link");
	    	    String imageBase64 = jsonObject.getString("image");
    	    	byte[] image = Base64.getDecoder().decode(imageBase64);
	    	    
	    	    JSONObject list_object = jsonObject.getJSONObject("list");
	    	    Presents_List_API list = new Presents_List_API();
	    	    list.setId_list(list_object.getInt("id_list"));
	    	    
	    	    Present_API present = new Present_API(0,name,description, average_price,priority , state, link, image, list);
	    	    
	        if (!present.create()) {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        } else {
	            return Response.status(Status.CREATED)
	                    .header("Location", "/MyPresentLists_API/api/present/" + present.getId_present())
	                    .build();
	        }
	    } catch (Exception ex) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }
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

    	    int id_present = jsonObject.getInt("id_present");
	        String name = jsonObject.getString("name");
    	    String description = jsonObject.getString("description");
    	    double average_price = jsonObject.getDouble("average_price");
    	    int priority = jsonObject.getInt("priority");
    	    String state = jsonObject.getString("state");
    	    String link = jsonObject.getString("link");
    	    String imageBase64 = jsonObject.getString("image");
	    	byte[] image = Base64.getDecoder().decode(imageBase64);
    	    
    	    JSONObject list_object = jsonObject.getJSONObject("list");
    	    Presents_List_API list = new Presents_List_API();
    	    list.setId_list(list_object.getInt("id_list"));
    	    
    	    Present_API present = new Present_API(id_present,name,description, average_price,priority , state, link, image, list);

	        if (!present.update()) {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        } else {
	            return Response.status(Status.OK).build();
	        }
	    } catch (Exception ex) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response find_Present(@PathParam("id") int id_present) {
		Present_API present = new Present_API();
		try {
			present = Present_API.find(id_present);

			if (present == null) {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(present).build();
	}
}
