package be.walbert.API;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;

@Path("/presents_list")
public class Presents_ListAPI {
	
	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsers(String json) {
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
	    	    
	        if (!presents_list.create()) {
	            return Response.status(Status.SERVICE_UNAVAILABLE).build();
	        } else {
	            return Response.status(Status.CREATED)
	                    .header("Location", "/MyPresentLists_API/api/presents_list/" + presents_list.getId_list())
	                    .build();
	        }
	    } catch (Exception ex) {
	        return Response.status(Status.BAD_REQUEST).build();
	    }
	}
}
