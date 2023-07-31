package be.walbert.API;

import java.util.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.walbert.Javabeans.Message_API;
import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;

@Path("/message")
public class MessageAPI {

	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create_Message(String json) {
	    if (json == null) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }
	    
	    try {
	    	 JSONObject jsonObject = new JSONObject(json);
	    	 String content = jsonObject.getString("content");
	    	 boolean checked = jsonObject.getBoolean("checked");	    	 
	    	    
	    	 JSONObject user_object = jsonObject.getJSONObject("user");
	    	 int id_users = user_object.getInt("id_users");
	    	 
	    	 Users_API users = Users_API.find(id_users);
	    	 
	    	 Message_API message = new Message_API(0,content,checked,users);
	    	    
	        if (!message.create()) {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        } else {
	            return Response.status(Status.CREATED)
	                    .header("Location", "/MyPresentLists_API/api/message/" + message.getId_message())
	                    .build();
	        }
			
		} catch (Exception ex) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }	    
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response find_Message(@PathParam("id") int id_message) {
		Message_API message = new Message_API();
		try {
			message = Message_API.find(id_message);

			if (message == null) {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(message).build();
	}
}
