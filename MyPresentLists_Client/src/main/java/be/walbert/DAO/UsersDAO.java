package be.walbert.DAO;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Users obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Users find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Users> findAll(Object obj) {
		// TODO Auto-generated method stub
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
				int id_users = json.getInt("id");
				String email = json.getString("email");
				user = new Users(id_users, pseudo, password, email);
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
	
	public boolean checkAccount(Users u) {
	    MultivaluedMap<String, String> paramsPost = new MultivaluedMapImpl();
	    paramsPost.add("pseudo", u.getPseudo());
	    paramsPost.add("email", u.getEmail());

	    try {
	        ClientResponse res = this.ressource
	            .path("users/checkEmail")
	            .accept(MediaType.APPLICATION_JSON)
	            .post(ClientResponse.class, paramsPost);

	        if (res.getStatus() == 200) {
	            return true; // Pseudo or email already taken
	        } else {
	            return false; // Pseudo or email available
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

}
