package be.walbert.Javabeans;

import java.io.Serializable;
import java.time.LocalDate;

public class List_API implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = 1266205700967767345L;
	private int id_list;
	private LocalDate limit_date;
	private String occasion;
	private boolean state;
	
	/*Constructors*/
	public List_API() {}

	
	/*Getters/Setters*/
	public int getId_list() {
		return id_list;
	}

	public void setId_list(int id_list) {
		this.id_list = id_list;
	}

	public LocalDate getLimit_date() {
		return limit_date;
	}

	public void setLimit_date(LocalDate limit_date) {
		this.limit_date = limit_date;
	}

	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	/*Methods*/
}
