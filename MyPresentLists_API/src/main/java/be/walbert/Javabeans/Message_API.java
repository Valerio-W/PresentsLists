package be.walbert.Javabeans;

import java.io.Serializable;

public class Message_API implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = 3493079009878220285L;

	
	/*Constructors*/
	public Message_API() {}
	private int id_message;
	private String content;
	
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
	
	/*Methods*/
}
