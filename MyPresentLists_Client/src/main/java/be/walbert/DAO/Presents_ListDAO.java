package be.walbert.DAO;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.walbert.javabeans.Presents_List;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Presents_List find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Presents_List> findAll(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
