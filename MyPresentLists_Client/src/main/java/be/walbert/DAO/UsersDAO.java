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

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.walbert.javabeans.Message;
import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class UsersDAO extends DAO<Users>{

	public UsersDAO() {}
	
	@Override
	public boolean create(Users obj) {
		MultivaluedMap<String, String> paramsPost = new MultivaluedMapImpl();
		paramsPost.add("pseudo", obj.getPseudo());
		paramsPost.add("password", obj.getPassword());
		paramsPost.add("email", obj.getEmail());

		try {
			 ClientResponse res = this.ressource
	 	                .path("users/create")
	 	                .accept(MediaType.APPLICATION_JSON)
	 	                .post(ClientResponse.class, paramsPost);
			
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
	public boolean delete(Users obj) {
		return false;
	}

	@Override
	public boolean update(Users obj) {
		return false;
	}

	@Override
	public Users find(int id) {
		try {
			 ClientResponse res = this.ressource
		     .path("users/" + id)
		     .accept(MediaType.APPLICATION_JSON)
		     .get(ClientResponse.class);
			 if (res.getStatus() == 200) {
		    	String response = res.getEntity(String.class); //The response body is extracted as a String using getEntity(String.class)
				JSONObject json = new JSONObject(response);
					
		        int id_users = json.getInt("id_users");
		        String pseudo = json.getString("pseudo");
		        String password = json.getString("password");
		        String email = json.getString("email");
		         
		        Users users = new Users(id_users, pseudo, password, email);
		        
		        JSONArray listsArray = json.getJSONArray("lists");
				if(listsArray.length()>0) {
					ArrayList<Presents_List> lists = new ArrayList<>();
					
					for (int i = 0; i < listsArray.length(); i++) {
					    JSONObject listObj = listsArray.getJSONObject(i);
					    //Extracting attribute values ​​from the "list" and creating a Presents_List object
					    int id_list = listObj.getInt("id_list");
					    JSONObject object_limit_date = listObj.getJSONObject("limit_date");

					    int year = object_limit_date.getInt("year");
					    String monthString = object_limit_date.getString("month");

					    // Convert month to int
					    int month = Month.valueOf(monthString.toUpperCase()).getValue();
					    int dayOfMonth = object_limit_date.getInt("dayOfMonth");

					    LocalDate limit_date = LocalDate.of(year, month, dayOfMonth);
				        String occasion = listObj.getString("occasion");
					    boolean state = listObj.getBoolean("state");
					    Presents_List presentsList = new Presents_List(id_list, limit_date, occasion, state, users);
					    lists.add(presentsList);
					}
					users.setLists(lists);
				}
		        return users;
			 }
		} catch (Exception e) {
		 e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Users> findAll() {
		return null;
	}

	public Users GetUser(String pseudo, String password) {
		MultivaluedMap<String, String> paramsPost = new MultivaluedMapImpl();
		paramsPost.add("pseudo", pseudo);
		paramsPost.add("password", password);
 
 		try { 
 			Users user = new Users();
 			//Calls the API using ClientResponse to send a POST request 
 			//to the "/users/login" URL and passes the paramsPost parameters.
 			 ClientResponse res = this.ressource
 	                .path("users/login")
 	                .accept(MediaType.APPLICATION_JSON)
 	                .post(ClientResponse.class, paramsPost);
			
 			if (res.getStatus() == 200) {	//Get HTTP response code
 				String response = res.getEntity(String.class); //The response body is extracted as a String using getEntity(String.class)
				JSONObject json = new JSONObject(response);
				int id_users = json.getInt("id_users");
				String email = json.getString("email");
				user = new Users(id_users, pseudo, password, email);

				JSONArray listsArray = json.getJSONArray("lists");
				if(listsArray.length()>0) {
					ArrayList<Presents_List> lists = new ArrayList<>();
					
					for (int i = 0; i < listsArray.length(); i++) {
					    JSONObject listObj = listsArray.getJSONObject(i);
					    //Extracting attribute values ​​from the "list" and creating a Presents_List object
					    int id_list = listObj.getInt("id_list");
					    JSONObject object_limit_date = listObj.getJSONObject("limit_date");

					    int year = object_limit_date.getInt("year");
					    String monthString = object_limit_date.getString("month");

					    // Convert month to int
					    int month = Month.valueOf(monthString.toUpperCase()).getValue();
					    int dayOfMonth = object_limit_date.getInt("dayOfMonth");

					    LocalDate limit_date = LocalDate.of(year, month, dayOfMonth);
				        String occasion = listObj.getString("occasion");
					    boolean state = listObj.getBoolean("state");
					    
					    //Creating the Presents_List object and adding it to "lists"
					    Presents_List presentsList = new Presents_List(id_list, limit_date, occasion, state, user);
					    
					    JSONArray presentsArray = listObj.getJSONArray("presents");

			            for (int j = 0; j < presentsArray.length(); j++) {
			                JSONObject presentObject = presentsArray.getJSONObject(j);

			                int presentId = presentObject.getInt("id_present");
			                String name = presentObject.getString("name");
			                String description = presentObject.getString("description");
			                BigDecimal averagePriceBigDecimal = presentObject.getBigDecimal("average_price");	    
			                int priority = presentObject.getInt("priority");
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
					    lists.add(presentsList);
					}
					user.setLists(lists);
				}
				
				
				JSONArray listsGuestArray = json.getJSONArray("guests_lists");
				if(listsGuestArray.length()>0) {
					ArrayList<Presents_List> guests = new ArrayList<>();
					
					for (int i = 0; i < listsGuestArray.length(); i++) {
					    JSONObject listGuestObj = listsGuestArray.getJSONObject(i);
					     
					    Presents_List presentsList = Presents_List.find(listGuestObj.getInt("id_list"));
					    guests.add(presentsList);
					}
					user.setGuests_lists(guests);
				}
				
				
				JSONArray messagesArray = json.getJSONArray("messages");
				if(messagesArray.length()>0) {
					ArrayList<Message> messages = new ArrayList<>();
					
					for (int i = 0; i < messagesArray.length(); i++) {
					    JSONObject listMessagesObj = messagesArray.getJSONObject(i);
					     
					    Message message = Message.find(listMessagesObj.getInt("id_message"));
					    messages.add(message);
					}
					user.setMessages(messages);
				}
				
 				return user;
			}
 			else {
 				return null; //If HTTP response code is not 200 (so 500,401...) return null object
 			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Users find(Users u) {
		MultivaluedMap<String, String> paramsPost = new MultivaluedMapImpl();
		paramsPost.add("pseudo", u.getPseudo());
		paramsPost.add("email", u.getEmail());
		
		try {
	        ClientResponse res = this.ressource
	            .path("users/isUsersExist")
	            .accept(MediaType.APPLICATION_JSON)
	            .post(ClientResponse.class, paramsPost);

	        if (res.getStatus() == 200) {	// User with pseudo found
	        	String response = res.getEntity(String.class); 
				JSONObject json = new JSONObject(response);
				int id_users = json.getInt("id_users");
				String password = json.getString("password");
				String email = json.getString("email");
				Users user_existing = new Users(id_users, u.getPseudo(), password, email); 
				
				return user_existing;
	        } else {
	        	return null;// User with pseudo not found
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return null;
	    }
	}

}
