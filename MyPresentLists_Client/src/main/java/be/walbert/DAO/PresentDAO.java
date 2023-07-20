package be.walbert.DAO;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import be.walbert.javabeans.Present;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Present obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Present find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Present> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
