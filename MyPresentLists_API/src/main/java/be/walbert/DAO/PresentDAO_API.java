package be.walbert.DAO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import be.walbert.Javabeans.Present_API;

public class PresentDAO_API extends DAO<Present_API> {

	public PresentDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Present_API obj) {
	    try { 

	        CallableStatement cstmt = connect.prepareCall("{ call Insert_Present(?, ?, ?, ?, ?, ?, ?, ?, ?) }");

	        cstmt.setInt(1, obj.getId_present());
	        cstmt.setString(2, obj.getName());
	        cstmt.setString(3, obj.getDescription());
	        cstmt.setDouble(4, obj.getAverage_price());
	        cstmt.setInt(5, obj.getPriority());
	        cstmt.setString(6, obj.getState());
	        cstmt.setString(7, ""); // can be null
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(obj.getImage());
	        cstmt.setBlob(8, inputStream);
	        cstmt.setInt(9, obj.getList().getId_list());

	        cstmt.execute();
	        cstmt.close();

	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Present_API find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Present_API obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Present_API obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Present_API> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
