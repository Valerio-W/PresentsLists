package be.walbert.javabeans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.walbert.DAO.DAO;
import be.walbert.DAO.Multiple_PaymentDAO;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_payment")
public class Multiple_Payment implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = -5943540420504098361L;
	private int id_payment;
	private double price_paid;
	private Present present;
	private Users user;
	private static final DAO<Multiple_Payment> multiple_payment_DAO = new Multiple_PaymentDAO();
	
	/*Constructors*/
	public Multiple_Payment() {}

	public Multiple_Payment(int id_payment, double price_paid, Present present, Users user) {
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

	public Present getPresent() {
		return present;
	}

	public void setPresent(Present present) {
		this.present = present;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	/*Methods*/
	public boolean createMultiplePayment() {
		return multiple_payment_DAO.create(this);
	}
	public boolean updateMultiplePayment() {
		return multiple_payment_DAO.update(this);
	}

	public static Multiple_Payment find(int id_multiple_payment) {
		return multiple_payment_DAO.find(id_multiple_payment);
	}
}
