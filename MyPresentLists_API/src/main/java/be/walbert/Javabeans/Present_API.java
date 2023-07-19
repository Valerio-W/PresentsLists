package be.walbert.Javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.walbert.DAO.AbstractDAOFactory_API;
import be.walbert.DAO.DAO;

public class Present_API implements Serializable{

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
	private Presents_List_API list;
	private ArrayList<Multiple_Payment_API> payments;
	private static final AbstractDAOFactory_API adf =  AbstractDAOFactory_API.getFactory();
	private static final DAO<Present_API> Present_DAO = adf.getPresentDAO_API();
	
	/*Constructors*/
	public Present_API() {
		payments= new ArrayList<>();
	}

	public Present_API(int id_present, String name, String description, double average_price, int priority, String state,
			String link, byte[] image, Presents_List_API list) {
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

	public Presents_List_API getList() {
		return list;
	}

	public void setList(Presents_List_API list) {
		this.list = list;
	}

	public ArrayList<Multiple_Payment_API> getPayments() {
		return payments;
	}

	public void setPayments(ArrayList<Multiple_Payment_API> payments) {
		this.payments = payments;
	}
	
	/*Methods*/
	public void addPayment(Multiple_Payment_API newpayment) {
		payments.add(newpayment);
	}
	public void removePayment(Multiple_Payment_API newpayment) {
		payments.remove(newpayment);
	}
	
	public boolean create() {
		return Present_DAO.create(this);
	}
}
