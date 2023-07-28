package be.walbert.Javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.walbert.DAO.AbstractDAOFactory_API;
import be.walbert.DAO.DAO;
import be.walbert.DAO.UsersDAO_API;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_users")
public class Users_API implements Serializable {

	/*Attributs*/
	private static final long serialVersionUID = 1027273029912291864L;
	@JsonProperty("id_users") //specify the name of a property during JSON serialization/deserialization
	private int id_users;
	private String pseudo;
	private String password;
	private String email;
	private ArrayList<Presents_List_API> lists;
	private ArrayList<Message_API> messages;
	private ArrayList<Multiple_Payment_API> payments;
	
	private static final AbstractDAOFactory_API adf =  AbstractDAOFactory_API.getFactory();
	private static final DAO<Users_API> UsersDAO = adf.getUserDAO_API();
	
	
	/*Constructors*/
	public Users_API() {
		lists = new ArrayList<>();
	}
	
	public Users_API(int id_users, String pseudo, String password, String email) {
		this.id_users = id_users;
		this.pseudo = pseudo;
		this.password = password;
		this.email = email;
		lists = new ArrayList<>();
		messages = new ArrayList<>();
		payments = new ArrayList<>();
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
	
	public ArrayList<Presents_List_API> getLists() {
		return lists;
	}

	public void setLists(ArrayList<Presents_List_API> lists) {
		this.lists = lists;
	}

	public ArrayList<Message_API> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message_API> messages) {
		this.messages = messages;
	}

	public ArrayList<Multiple_Payment_API> getPayments() {
		return payments;
	}

	public void setPayments(ArrayList<Multiple_Payment_API> payments) {
		this.payments = payments;
	}
	/*Methods*/
	public void addList(Presents_List_API newList) {
		lists.add(newList);
	}
	public void addMessage(Message_API newMessage) {
		messages.add(newMessage);
	}
	public void removeMessage(Message_API newMessage) {
		messages.remove(newMessage);
	}
	public void addPayment(Multiple_Payment_API newPayment) {
		payments.add(newPayment);
	}
	public void removePayment(Multiple_Payment_API newPayment) {
		payments.remove(newPayment);
	}
	public static Users_API login (String pseudo, String password) {
		UsersDAO_API userdao = (UsersDAO_API) adf.getUserDAO_API();
		return userdao.GetUser(pseudo, password);
	}

	public boolean create() {
		return UsersDAO.create(this);
	}

	public boolean checkAccount() {
		UsersDAO_API userdao = (UsersDAO_API) adf.getUserDAO_API();
		return userdao.checkAccount(this);
	}

	public Users_API isUsersExist() {
		UsersDAO_API userdao = (UsersDAO_API) adf.getUserDAO_API();
		return userdao.isUsersExist(this);
	}
	

}
