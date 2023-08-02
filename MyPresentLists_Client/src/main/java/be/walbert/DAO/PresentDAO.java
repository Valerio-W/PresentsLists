package be.walbert.DAO;

import java.util.ArrayList;
import java.util.Base64;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import be.walbert.javabeans.Multiple_Payment;
import be.walbert.javabeans.Present;
import be.walbert.javabeans.Presents_List;
import be.walbert.javabeans.Users;

public class PresentDAO extends DAO<Present>{

	@Override
	public boolean create(Present obj) {
		//Convert Object to json
		ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        // Send POST request with JSON
			String json = objectMapper.writeValueAsString(obj);
	        ClientResponse res = this.ressource
	                .path("present/create")
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
	public boolean delete(Present obj) {
		return false;
	}

	@Override
	public boolean update(Present obj) {
		ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        String json = objectMapper.writeValueAsString(obj);
	        ClientResponse res = this.ressource
	                .path("present/update")
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
	public Present find(int id) {
		 try {
			 ClientResponse res = this.ressource
		     .path("present/" + id)
		     .accept(MediaType.APPLICATION_JSON)
		     .get(ClientResponse.class);

		     if (res.getStatus() == 200) {
		    	String response = res.getEntity(String.class); //The response body is extracted as a String using getEntity(String.class)
				JSONObject json = new JSONObject(response);
					
		        int presentId = json.getInt("id_present");
		        String name = json.getString("name");
		        String description = json.getString("description");
		        double averagePrice = json.getDouble("average_price");
		        int priority = json.getInt("priority");
		        String statePresent = json.getString("state");
		        String link = null;
		        if (!json.isNull("link")) {
		        	link = json.getString("link");
		        }
		        byte[] image= null;
		        if (!json.isNull("image")) {
		        	image = Base64.getDecoder().decode(json.getString("image"));
		        }
		        JSONObject list_object = json.getJSONObject("list");
		        int id_list = list_object.getInt("id_list");
		      
		        Presents_List list = Presents_List.find(id_list);
		        Present present = new Present(presentId, name, description, averagePrice, priority, statePresent, link, image, list);

		        JSONArray multiple_paymentsArray = json.getJSONArray("payments");

	            for (int i = 0; i < multiple_paymentsArray.length(); i++) {
	                JSONObject multiple_paymentObject = multiple_paymentsArray.getJSONObject(i);

	                int id_multiple_payment = multiple_paymentObject.getInt("id_payment");
	                double price_paid = multiple_paymentObject.getDouble("price_paid");
	                
	                JSONObject usersObject = multiple_paymentObject.getJSONObject("user");
	                Users users = Users.find(usersObject.getInt("id_users"));
	                Multiple_Payment new_mutliple_payment = new Multiple_Payment(id_multiple_payment,price_paid,present, users);
	                present.addPayment(new_mutliple_payment);
	            }
		        return present;
		     }
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return null;
	}

	@Override
	public ArrayList<Present> findAll() {
		return null;
	}

}
