package be.walbert.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.walbert.DAO.DAO;
import be.walbert.DAO.UsersDAO;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_users")
public class Users implements Serializable{
	
	/*Attributs*/
	private static final long serialVersionUID = 8882105652315396517L;
	private static final DAO<Users> userDAO = new UsersDAO();
	@JsonProperty("id_users") //specify the name of a property during JSON serialization/deserialization
	private int id_users;
	private String pseudo;
	private String password;
	private String email;
	private ArrayList<Presents_List> lists;
	private ArrayList<Message> messages;
	private ArrayList<Multiple_Payment> payments;
	private ArrayList<Presents_List> guests_lists;

	/*Constructors*/
	public Users() {
		lists = new ArrayList<>();
	}
	
	public Users(int id_users, String pseudo, String password, String email) {
		this.id_users = id_users;
		this.pseudo = pseudo;
		this.password = password;
		this.email = email;
		lists = new ArrayList<>();
		messages = new ArrayList<>();
		payments = new ArrayList<>();
		guests_lists = new ArrayList<>();
	}
	
	public Users(String pseudo, String password) {
		this.pseudo = pseudo;
		this.password = password;
		lists = new ArrayList<>();
		messages = new ArrayList<>();
		payments = new ArrayList<>();
		guests_lists = new ArrayList<>();
	}
	
	/*Getters/Setters*/
	public int getId() {
		return id_users;
	}

	public void setId(int id_users) {
		this.id_users = id_users;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public ArrayList<Presents_List> getLists() {
		return lists;
	}

	public void setLists(ArrayList<Presents_List> lists) {
		this.lists = lists;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public ArrayList<Multiple_Payment> getPayments() {
		return payments;
	}

	public void setPayments(ArrayList<Multiple_Payment> payments) {
		this.payments = payments;
	}
	
	public ArrayList<Presents_List> getGuests_lists() {
		return guests_lists;
	}

	public void setGuests_lists(ArrayList<Presents_List> guests_lists) {
		this.guests_lists = guests_lists;
	}
	
	/*Methods*/
	
	public static Users login (String pseudo, String password) {
		UsersDAO userDAO = new UsersDAO();
		return userDAO.GetUser(pseudo, password);
	}

	public boolean Signin() {
		return userDAO.create(this);
		
	}

	public Users findExistingUsers() {
		UsersDAO userDAO = new UsersDAO();
		return userDAO.find(this);
	}
	
	public static Users find(int id_users) {
		return userDAO.find(id_users);
	}
	

}
