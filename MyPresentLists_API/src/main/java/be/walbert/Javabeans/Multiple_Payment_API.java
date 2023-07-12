package be.walbert.Javabeans;

import java.io.Serializable;

public class Multiple_Payment_API implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -5943540420504098361L;
	private int id_payment;
	private double price_paid;
	private Present_API present;
	private Users_API user;
	
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
}
