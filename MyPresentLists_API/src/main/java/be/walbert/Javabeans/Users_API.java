package be.walbert.Javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.walbert.DAO.AbstractDAOFactory_API;
import be.walbert.DAO.DAO;
import be.walbert.DAO.UsersDAO_API;

public class Users_API implements Serializable {

	/*Attributs*/
	private static final long serialVersionUID = 1027273029912291864L;
	private int id;
    private String pseudo;
    private String password;
    private String email;
	
	private static final AbstractDAOFactory_API adf =  AbstractDAOFactory_API.getFactory();
	private static final DAO<Users_API> UsersDAO = adf.getUserDAO_API();
	
	
	/*Constructors*/
	public Users_API() {}

	public Users_API(int id, String pseudo, String password, String email) {
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
	public ArrayList<Users_API> printAll() {
	    ArrayList<Users_API> users = UsersDAO.findAll();
   
	    return users;
	}

	public static Users_API login (String pseudo, String password) {
		UsersDAO_API userdao = (UsersDAO_API) adf.getUserDAO_API();
		return userdao.GetUser(pseudo, password);
	}
	

}
