package be.walbert.Javabeans;

import java.io.Serializable;

public class Present_API implements Serializable {

	/*Attributs*/
	private static final long serialVersionUID = 6177375188816076959L;
	private int id_present;
	private String name;
	private String description;
	private double average_price;
	private int priority;
	private String state;
	private String link;
    private byte[] image; 
	
	/*Constructors*/
	public Present_API() {
	}

	/*Getters/Setters*/

	public int getId_present() {
		return id_present;
	}

	public void setId_present(int id_present) {
		this.id_present = id_present;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAverage_price() {
		return average_price;
	}

	public void setAverage_price(double average_price) {
		this.average_price = average_price;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
		
	
	/*Methods*/
}
