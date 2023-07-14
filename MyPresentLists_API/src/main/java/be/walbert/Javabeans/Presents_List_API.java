package be.walbert.Javabeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.DAO.AbstractDAOFactory_API;
import be.walbert.DAO.DAO;

public class Presents_List_API implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = 396839806525868199L;
	private int id_list;
	private LocalDate limit_date;
	private String occasion;
	private boolean state;
	private Users_API owner;
	private ArrayList<Users_API> guests;
	private ArrayList<Present_API> presents;
	private static final AbstractDAOFactory_API adf =  AbstractDAOFactory_API.getFactory();
	private static final DAO<Presents_List_API> Presents_List_DAO = adf.getPresents_ListDAO_API();
	
	/*Constructors*/
	public Presents_List_API() {
		guests = new ArrayList<>();
		presents = new ArrayList<>();
	}	
	
	public Presents_List_API(int id_list, LocalDate limit_date, String occasion, boolean state, Users_API owner) {
		this.id_list = id_list;
		this.occasion= occasion;
		this.limit_date = limit_date;
		this.state = state;
		this.owner = owner;
		guests = new ArrayList<>();
		presents = new ArrayList<>();
	}

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

	public Users_API getOwner() {
		return owner;
	}

	public void setOwner(Users_API owner) {
		this.owner = owner;
	}

	public ArrayList<Users_API> getGuests() {
		return guests;
	}

	public void setGuests(ArrayList<Users_API> guests) {
		this.guests = guests;
	}
	
	public ArrayList<Present_API> getPresents() {
		return presents;
	}

	public void setPresents(ArrayList<Present_API> presents) {
		this.presents = presents;
	}

	/*Methods*/
	public void addGuest(Users_API newGuest) {
		guests.add(newGuest);
	}
	public void addPresent(Present_API newpresent) {
		presents.add(newpresent);
	}
	public void removePresent(Present_API newpresent) {
		presents.remove(newpresent);
	}
	
	public boolean create() {
		return Presents_List_DAO.create(this);
	}

}
