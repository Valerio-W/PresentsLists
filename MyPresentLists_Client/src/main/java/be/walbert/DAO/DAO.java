package be.walbert.DAO;

import java.net.URI;
import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public abstract class DAO<T> {

	protected Connection connect = null;
	protected Client client;
	protected static final String baseUrl = "http://localhost:8080/MyPresentLists_API/api";
	protected WebResource ressource;
	protected ObjectMapper mapper;

    public DAO() {
		ClientConfig config = new DefaultClientConfig();
		client = Client.create(config);
		ressource = client.resource(getBaseURI());
		mapper = new ObjectMapper();
    }
    
	private static URI getBaseURI() {
		return UriBuilder.fromUri(baseUrl).build();
	}

	protected WebResource getRessource() {
	    return ressource;
	}

    public abstract boolean create(T obj);

    public abstract boolean delete(T obj);

    public abstract boolean update(T obj);

    public abstract T find(int id);

    public abstract ArrayList<T> findAll(Object obj);
}
