package be.walbert.Javabeans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.walbert.DAO.AbstractDAOFactory_API;
import be.walbert.DAO.DAO;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_payment")// Annotation indicating the use of an identifier generator to handle circular references during JSON serialization
public class Multiple_Payment_API implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -5943540420504098361L;
	private int id_payment;
	private double price_paid;
	private Present_API present;
	private Users_API user;
	private static final AbstractDAOFactory_API adf =  AbstractDAOFactory_API.getFactory();
	private static final DAO<Multiple_Payment_API> Multiple_Payment_DAO = adf.getMultiple_PaymentDAO_API();
	
	/*Constructors*/
	public Multiple_Payment_API() {}

	public Multiple_Payment_API(int id_payment, double price_paid, Present_API present, Users_API user) {
		this.id_payment = id_payment;
		this.price_paid = price_paid;
		this.present = present;
		this.user = user;
	}

	/*Getters/Setters*/
	public int getId_payment() {
		return id_payment;
	}

	public void setId_payment(int id_payment) {
		this.id_payment = id_payment;
	}

	public double getPrice_paid() {
		return price_paid;
	}

	public void setPrice_paid(double price_paid) {
		this.price_paid = price_paid;
	}

	public Present_API getPresent() {
		return present;
	}

	public void setPresent(Present_API present) {
		this.present = present;
	}

	public Users_API getUser() {
		return user;
	}

	public void setUser(Users_API user) {
		this.user = user;
	}
	
	/*Methods*/
	public boolean create() {
		return Multiple_Payment_DAO.create(this);
	}
	
	public boolean update() {
		return Multiple_Payment_DAO.update(this);
	}

	public static Multiple_Payment_API find(int id_message) {
		return Multiple_Payment_DAO.find(id_message);
	}
		
}
