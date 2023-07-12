package be.walbert.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.walbert.DAO.DAO;
import be.walbert.DAO.UsersDAO;

public class Users implements Serializable{
	
	/*Attributs*/
	private static final long serialVersionUID = 8882105652315396517L;
	private static final DAO<Users> userDAO = new UsersDAO();

	private int id_users;
	private String pseudo;
	private String password;
	private String email;
	private ArrayList<Presents_List> lists;
	private ArrayList<Message> messages;
	
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
	}
	
	public Users(String pseudo, String password) {
		this.pseudo = pseudo;
		this.password = password;
		lists = new ArrayList<>();
		messages = new ArrayList<>();
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

	/*Methods*/
	public void addList(Presents_List newList) {
		lists.add(newList);
	}
	public void addMessage(Message newMessage) {
		messages.add(newMessage);
	}
	public void removeMessage(Message newMessage) {
		messages.add(newMessage);
	}
	
	public static Users login (String pseudo, String password) {
		UsersDAO userDAO = new UsersDAO();
		return userDAO.GetUser(pseudo, password);
	}

	public boolean Signin() {
		return userDAO.create(this);
		
	}

	public boolean checkAccount() {
		return ((UsersDAO) userDAO).checkAccount(this);
	}
}
