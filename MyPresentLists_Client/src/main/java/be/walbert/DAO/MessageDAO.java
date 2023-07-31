package be.walbert.DAO;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import be.walbert.javabeans.Message;
import be.walbert.javabeans.Users;

public class MessageDAO extends DAO<Message>{

	@Override
	public boolean create(Message obj) {
		ObjectMapper objectMapper = new ObjectMapper();

	    try {
			String json = objectMapper.writeValueAsString(obj);
	        ClientResponse res = this.ressource
	                .path("message/create")
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
	public boolean delete(Message obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Message obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Message find(int id) {
		try {
			ClientResponse res = this.ressource
			   .path("message/" + id)
			   .accept(MediaType.APPLICATION_JSON)
			   .get(ClientResponse.class);
			
			  if (res.getStatus() == 200) {
		        	String response = res.getEntity(String.class); //The response body is extracted as a String using getEntity(String.class)
					JSONObject json = new JSONObject(response);
					
					int id_message = json.getInt("id_message");
					String content= json.getString("content");
					boolean checked = json.getBoolean("checked");
					
			        JSONObject object_user = json.getJSONObject("user");
			        int id_users = object_user.getInt("id_users");
				    String pseudo = object_user.getString("pseudo");
				    String password = object_user.getString("password");
				    String email = object_user.getString("email");
				    Users user = new Users(id_users,pseudo,password, email);
				  
				    Message message = new Message(id_message, content, checked, user);
				    
				    return message;
			  }
		} catch (Exception e) {
	        e.printStackTrace();
 		}
		return null;
	}

	@Override
	public ArrayList<Message> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
