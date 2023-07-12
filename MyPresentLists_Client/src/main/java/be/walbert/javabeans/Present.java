package be.walbert.javabeans;

import java.io.Serializable;

public class Present implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -7514398724391972517L;
	private int id_present;
	private String name;
	private String description;
	private double average_prince;
	private int priority;
	private String state;
	private String link;
	private String image;
	private Presents_List list;
	
	/*Constructors*/
	public Present() {
		super();
	}

	public Present(int id_present, String name, String description, double average_prince, int priority, String state,
			String link, String image, Presents_List list) {
		this.id_present = id_present;
		this.name = name;
		this.description = description;
		this.average_prince = average_prince;
		this.priority = priority;
		this.state = state;
		this.link = link;
		this.image = image;
		this.list = list;
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

	public double getAverage_prince() {
		return average_prince;
	}

	public void setAverage_prince(double average_prince) {
		this.average_prince = average_prince;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Presents_List getList() {
		return list;
	}

	public void setList(Presents_List list) {
		this.list = list;
	}
	
	
	/*Methods*/
}
