package be.walbert.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import be.walbert.Javabeans.Presents_List_API;

public class Presents_ListDAO_API extends DAO<Presents_List_API>{

	public Presents_ListDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Presents_List_API obj) {
		try {
			CallableStatement callableStatement = connect.prepareCall("{call Insert_List(?,?,?,?,?)}");
		        		        
		    callableStatement.setDate(1, Date.valueOf(obj.getLimit_date()));
		    callableStatement.setString(2, obj.getOccasion());
		    callableStatement.setBoolean(3, obj.isState());
		    callableStatement.setInt(4, obj.getOwner().getId());
		    callableStatement.registerOutParameter(5, Types.INTEGER); 
		    
		    callableStatement.execute();
		    
		    //int id_list = callableStatement.getInt(5); 

	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Presents_List_API find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Presents_List_API obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Presents_List_API obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Presents_List_API> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

 	 
}
