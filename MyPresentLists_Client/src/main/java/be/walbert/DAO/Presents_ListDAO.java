package be.walbert.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Base64;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class Presents_ListDAO extends DAO <Presents_List>{

	@Override
	public boolean create(Presents_List obj) {
		//Convert Object to json
		ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        // Send POST request with JSON
			String json = objectMapper.writeValueAsString(obj);
	        ClientResponse res = this.ressource
	                .path("presents_list/create")
	                .accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
	                .post(ClientResponse.class, json);

	        if (res.getStatus() == 201) {
	            return true;
	        }
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        return false;
	    }
	    return false;
	}

	@Override
	public boolean delete(Presents_List obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Presents_List obj) {
	    ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        String json = objectMapper.writeValueAsString(obj);
	        ClientResponse res = this.ressource
	                .path("presents_list/update")
	                .accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
	                .put(ClientResponse.class, json);

	        if (res.getStatus() == 200) {
	            return true;
	        }
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        return false;
	    }
	    return false;
	}

	@Override
	public Presents_List find(int id) {
	    try {
	        ClientResponse res = this.ressource
	            .path("presents_list/" + id)
	            .accept(MediaType.APPLICATION_JSON)
	            .get(ClientResponse.class);

	        if (res.getStatus() == 200) {
	        	String response = res.getEntity(String.class); //The response body is extracted as a String using getEntity(String.class)
				JSONObject json = new JSONObject(response);
				JSONObject object_limit_date = json.getJSONObject("limit_date");

			    int year = object_limit_date.getInt("year");
			    String monthString = object_limit_date.getString("month");
			    // Convert month to int
			    int month = Month.valueOf(monthString.toUpperCase()).getValue();
			    int dayOfMonth = object_limit_date.getInt("dayOfMonth");
			    LocalDate limit_date = LocalDate.of(year, month, dayOfMonth);
		        String occasion = json.getString("occasion");
		        boolean state = json.getBoolean("state");
		        
		        JSONObject object_user = json.getJSONObject("owner");
			    int id_users = object_user.getInt("id_users");
			    String pseudo = object_user.getString("pseudo");
			    String password = object_user.getString("password");
			    String email = object_user.getString("email");
			    Users user = new Users(id_users,pseudo,password, email);
			  
	            Presents_List presentsList = new Presents_List(id, limit_date, occasion, state, user);
	            
	            JSONArray presentsArray = json.getJSONArray("presents");

	            for (int i = 0; i < presentsArray.length(); i++) {
	                JSONObject presentObject = presentsArray.getJSONObject(i);

	                int presentId = presentObject.getInt("id_present");
	                String name = presentObject.getString("name");
	                String description = presentObject.getString("description");
	                BigDecimal averagePriceBigDecimal = presentObject.getBigDecimal("average_price");	                int priority = presentObject.getInt("priority");
	                double averagePrice = averagePriceBigDecimal.doubleValue();
	                String statePresent = presentObject.getString("state");
	                String link = null;
	                if (!presentObject.isNull("link")) {
	                    link = presentObject.getString("link");
	                }
	                byte[] image= null;
	                if (!presentObject.isNull("image")) {
	                	image = Base64.getDecoder().decode(presentObject.getString("image"));
	                }

	                Present present = new Present(presentId, name, description, averagePrice, priority, statePresent, link, image,presentsList);
	                presentsList.addPresent(present);
	            }

	            JSONArray guestsArray = json.getJSONArray("guests");

	            for (int i = 0; i < guestsArray.length(); i++) {
	                JSONObject guestObject = guestsArray.getJSONObject(i);

	                int id_guest = guestObject.getInt("id_users");
	                String pseudo_guest = guestObject.getString("pseudo");
	                String password_guest = guestObject.getString("password");
	                String email_guest = guestObject.getString("email");

	                Users new_guest = new Users(id_guest,pseudo_guest,password_guest,email_guest);
	                presentsList.addGuest(new_guest);
	            }

	            
	            return presentsList;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public ArrayList<Presents_List> findAll() {
	    ArrayList<Presents_List> lists = new ArrayList<>();
	    
	    try {
	        ClientResponse res = this.ressource
	            .path("presents_list/all")
	            .accept(MediaType.APPLICATION_JSON)
	            .get(ClientResponse.class);

	        if (res.getStatus() == 200) {
	            String response = res.getEntity(String.class);
	            JSONArray jsonArray = new JSONArray(response);
	            
	            for (int i = 0; i < jsonArray.length(); i++) {
	                JSONObject json = jsonArray.getJSONObject(i);
	                
	                int id_list = json.getInt("id_list");
	                String occasion = json.getString("occasion");
	                boolean state = json.getBoolean("state");

					JSONObject object_limit_date = json.getJSONObject("limit_date");
	                int year = object_limit_date.getInt("year");
				    String monthString = object_limit_date.getString("month");
				    // Convert month to int
				    int month = Month.valueOf(monthString.toUpperCase()).getValue();
				    int dayOfMonth = object_limit_date.getInt("dayOfMonth");
				    LocalDate limit_date = LocalDate.of(year, month, dayOfMonth);

			        JSONObject object_user = json.getJSONObject("owner");
				    int id_users = object_user.getInt("id_users");
				    String pseudo = object_user.getString("pseudo");
				    String password = object_user.getString("password");
				    String email = object_user.getString("email");
				    Users user = new Users(id_users,pseudo,password, email);	            
				    
	                Presents_List presentsList = new Presents_List(id_list, limit_date, occasion, state, user);
	                lists.add(presentsList);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return lists;
	}
}
