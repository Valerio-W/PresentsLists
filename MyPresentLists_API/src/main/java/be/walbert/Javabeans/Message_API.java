package be.walbert.Javabeans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.walbert.DAO.AbstractDAOFactory_API;
import be.walbert.DAO.DAO;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_message")// Annotation indicating the use of an identifier generator to handle circular references during JSON serialization.
public class Message_API implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -6876975184551742571L;
	private int id_message;
	private String content;
	private boolean checked;
	private Users_API user;
	private static final AbstractDAOFactory_API adf =  AbstractDAOFactory_API.getFactory();
	private static final DAO<Message_API> Message_DAO = adf.getMessageDAO_API();
	
	/*Constructors*/
	public Message_API() {
	}
	
	public Message_API(int id_message, String content, boolean checked, Users_API user) {
		this.id_message = id_message;
		this.content = content;
		this.checked= checked;
		this.user = user;
	}

	/*Getters/Setters*/
	public int getId_message() {
		return id_message;
	}

	public void setId_message(int id_message) {
		this.id_message = id_message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Users_API getUser() {
		return user;
	}

	public void setUser(Users_API user) {
		this.user = user;
	}
	
	/*Methods*/
	public boolean create() {
		return Message_DAO.create(this);
	}
	
	public boolean update() {
		return Message_DAO.update(this);
	}

	public static Message_API find(int id_message) {
		return Message_DAO.find(id_message);
	}
}
