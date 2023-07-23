package be.walbert.javabeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.walbert.DAO.DAO;
import be.walbert.DAO.Presents_ListDAO;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_list")
public class Presents_List implements Serializable{

	/*Attributs*/
	private static final long serialVersionUID = 396839806525868199L;
	private static final DAO<Presents_List> presents_listDAO = new Presents_ListDAO();
	private int id_list;
	private LocalDate limit_date;
	private String occasion;
	private boolean state;
	private Users owner;
	private ArrayList<Users> guests;
	 //To avoid a circular reference problem when serializing these object to JSON.
	private ArrayList<Present> presents;
	
	/*Constructors*/
	public Presents_List() {
		guests = new ArrayList<>();
		presents = new ArrayList<>();
	}	
	
	public Presents_List(int id_list, LocalDate limit_date, String occasion, boolean state, Users owner) {
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

	public Users getOwner() {
		return owner;
	}

	public void setOwner(Users owner) {
		this.owner = owner;
	}

	public ArrayList<Users> getGuests() {
		return guests;
	}

	public void setGuests(ArrayList<Users> guests) {
		this.guests = guests;
	}
	
	public ArrayList<Present> getPresents() {
		return presents;
	}

	public void setPresents(ArrayList<Present> presents) {
		this.presents = presents;
	}

	/*Methods*/
	public void addGuest(Users newGuest) {
		guests.add(newGuest);
	}
	public void addPresent(Present newpresent) {
		presents.add(newpresent);
	}
	public void removePresent(Present newpresent) {
		presents.remove(newpresent);
	}
	
	//Sort the present list according to the priority of the Present objects
	public void sortPresents_By_Priority() {
	    int n = presents.size();
	    boolean swapped;
	    
	    do {
	        swapped = false;
	        for (int i = 0; i < n - 1; i++) {
	            Present present1 = presents.get(i);
	            Present present2 = presents.get(i + 1);

	            if (present1.getPriority() > present2.getPriority()) {
	                // Swap the two presents if the priority of present1 is greater than the priority of present2
	                presents.set(i, present2);
	                presents.set(i + 1, present1);
	                swapped = true;
	            }
	        }
	        n--;
	    } while (swapped);
	}

	public boolean createPresents_List() {
		return presents_listDAO.create(this);
	}
	
	public static Presents_List find(int id_list) {
		return presents_listDAO.find(id_list);
	}
	
	public boolean update_PresentsList() {
		this.set_Expired();
		return presents_listDAO.update(this);

	}
	public void set_Expired() {
	    LocalDate today = LocalDate.now();
	    
	    if (limit_date.isBefore(today)) {
	        this.setState(false);
	    }
	    else {
	    	this.setState(true);
	    }
	    
	}

	public static ArrayList<Presents_List> findAllLists() {
	    Presents_ListDAO presentsListDAO = new Presents_ListDAO();
	    return presentsListDAO.findAll();  
	}


}
