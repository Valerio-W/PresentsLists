package be.walbert.Javabeans;

import java.io.Serializable;

public class Message_API implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -6876975184551742571L;
	private int id_message;
	private String content;
	private boolean checked;
	private Users_API user;
	
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
}
