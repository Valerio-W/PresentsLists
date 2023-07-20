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
	        CallableStatement cstmt = connect.prepareCall("{call InsertPresent(?, ?, ?, ?, ?, ?, ?, ?)}");

	        cstmt.setString(1, obj.getName());
	        cstmt.setString(2, obj.getDescription());
	        cstmt.setDouble(3, obj.getAverage_price());
	        cstmt.setInt(4, obj.getPriority());
	        cstmt.setString(5, obj.getState());

	        // Handle the case when link is absent
	        if (obj.getLink() != null && !obj.getLink().isEmpty()) {
	            cstmt.setString(6, obj.getLink());
	        } else {
	            cstmt.setNull(6, java.sql.Types.VARCHAR);
	        }

	        // Handle the case when image is absent
	        if (obj.getImage() != null) {
	            ByteArrayInputStream inputStream = new ByteArrayInputStream(obj.getImage());
	            cstmt.setBlob(7, inputStream);
	        } else {
	            cstmt.setNull(7, java.sql.Types.BLOB);
	        }

	        cstmt.setInt(8, obj.getList().getId_list());

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
