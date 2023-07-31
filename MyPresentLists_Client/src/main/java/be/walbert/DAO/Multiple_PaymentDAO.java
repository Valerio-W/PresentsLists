package be.walbert.DAO;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import be.walbert.javabeans.Multiple_Payment;
import be.walbert.javabeans.Present;

public class Multiple_PaymentDAO extends DAO<Multiple_Payment>{

	@Override
	public boolean create(Multiple_Payment obj) {
		//Convert Object to json
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Send POST request with JSON
			String json = objectMapper.writeValueAsString(obj);
			ClientResponse res = this.ressource
					.path("multiple_payment/create")
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
	public boolean delete(Multiple_Payment obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Multiple_Payment obj) {
		ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        String json = objectMapper.writeValueAsString(obj);
	        ClientResponse res = this.ressource
	                .path("multiple_payment/update")
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
	public Multiple_Payment find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Multiple_Payment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
