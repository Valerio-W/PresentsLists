package be.walbert.javabeans;

import java.io.Serializable;

import be.walbert.DAO.DAO;
import be.walbert.DAO.MessageDAO;
import be.walbert.DAO.PresentDAO;

public class Message implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -6876975184551742571L;
	private int id_message;
	private String content;
	private boolean checked;
	private Users user;
	private static final DAO<Message> message_DAO = new MessageDAO();

	/*Constructors*/
	public Message() {
	}
	
	public Message(int id_message, String content, boolean checked, Users user) {
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	/*Methods*/
	public boolean create() {
		return message_DAO.create(this);
	}
	
	public boolean update() {
		return message_DAO.update(this);
	}

	public static Message find(int id_message) {
		return message_DAO.find(id_message);
	}
}
