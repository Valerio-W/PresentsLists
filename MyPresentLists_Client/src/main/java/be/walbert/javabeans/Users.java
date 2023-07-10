package be.walbert.javabeans;

import java.io.Serializable;

import be.walbert.DAO.DAO;
import be.walbert.DAO.UsersDAO;

public class Users implements Serializable{
	
	/*Attributs*/
	private static final long serialVersionUID = 8882105652315396517L;
	private static final DAO<Users> userDAO = new UsersDAO();

	private int id;
	private String pseudo;
	private String password;
	private String email;
	
	/*Constructors*/
	public Users() {}
	
	public Users(int id, String pseudo, String password, String email) {
		this.id = id;
		this.pseudo = pseudo;
		this.password = password;
		this.email = email;
	}
	public Users(String pseudo, String password) {
		this.pseudo = pseudo;
		this.password = password;
	}
	
	/*Getters/Setters*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	/*Methods*/
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
