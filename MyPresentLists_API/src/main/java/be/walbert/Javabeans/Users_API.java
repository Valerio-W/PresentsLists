package be.walbert.Javabeans;

import java.io.Serializable;

public class Users_API implements Serializable {

	/*Attributs*/
	private static final long serialVersionUID = 1027273029912291864L;
	private int id;
    private String pseudo;
    private String password;
    private String email;
	
	/*Constructors*/
	public Users_API() {}

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
