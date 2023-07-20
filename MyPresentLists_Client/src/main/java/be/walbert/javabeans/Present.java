package be.walbert.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.walbert.DAO.DAO;
import be.walbert.DAO.PresentDAO;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_present")
public class Present implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -7514398724391972517L;
	private int id_present;
	private String name;
	private String description;
	private double average_price;
	private int priority;
	private String state;
	private String link;
	private byte[] image;
	private Presents_List list;
	private ArrayList<Multiple_Payment> payments;
	private static final DAO<Present> present_DAO = new PresentDAO();
	
	/*Constructors*/
	public Present() {
		payments= new ArrayList<>();
	}

	public Present(int id_present, String name, String description, double average_price, int priority, String state,
			String link, byte[] image, Presents_List list) {
		this.id_present = id_present;
		this.name = name;
		this.description = description;
		this.average_price = average_price;
		this.priority = priority;
		this.state = state;
		this.link = link;
		this.image = image;
		this.list = list;
		payments = new ArrayList<>();
	}

	public Present(int id_present, String name, String description, double average_price, int priority, String state,
		Presents_List list) {
		this.id_present = id_present;
		this.name = name;
		this.description = description;
		this.average_price = average_price;
		this.priority = priority;
		this.state = state;
		this.list = list;
		payments = new ArrayList<>();
	}
	
	public Present(int id_present, String name, String description, double average_price, int priority, String state,
		 Presents_List list, byte[]  image) {
		this.id_present = id_present;
		this.name = name;
		this.description = description;
		this.average_price = average_price;
		this.priority = priority;
		this.state = state;
		this.image = image;
		this.list = list;
		payments = new ArrayList<>();
	}
	
	public Present(int id_present, String name, String description, double average_price, int priority, String state,
			String link, Presents_List list) {
		this.id_present = id_present;
		this.name = name;
		this.description = description;
		this.average_price = average_price;
		this.priority = priority;
		this.state = state;
		this.link = link;
		this.list = list;
		payments = new ArrayList<>();
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

	public void setImage(byte[]  image) {
		this.image = image;
	}

	public Presents_List getList() {
		return list;
	}

	public void setList(Presents_List list) {
		this.list = list;
	}

	public ArrayList<Multiple_Payment> getPayments() {
		return payments;
	}

	public void setPayments(ArrayList<Multiple_Payment> payments) {
		this.payments = payments;
	}
	
	/*Methods*/
	public void addPayment(Multiple_Payment newpayment) {
		payments.add(newpayment);
	}
	public void removePayment(Multiple_Payment newpayment) {
		payments.remove(newpayment);
	}

	@Override
	public String toString() {
		return "Present [id_present=" + id_present + ", name=" + name + ", description=" + description
				+ ", average_prince=" + average_price + ", priority=" + priority + ", state=" + state + ", link="
				+ link + ", image=" + image + ", list=" + list + ", payments=" + payments + "]";
	}
	public boolean createPresent() {
		return present_DAO.create(this);
	}
	
}
