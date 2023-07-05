package be.walbert.javabeans;

import java.io.Serializable;

public class Users implements Serializable{
	
	/*Attributs*/
	private static final long serialVersionUID = 8882105652315396517L;

	private int id;
	private String pseudo;
	private String password;
	private String email;
	
	/*Constructors*/
	public Users() {}
	
	
	/*Getters/Setters*/
	public Users(int id, String pseudo, String password, String email) {
		this.id = id;
		this.pseudo = pseudo;
		this.password = password;
		this.email = email;
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
	
}
