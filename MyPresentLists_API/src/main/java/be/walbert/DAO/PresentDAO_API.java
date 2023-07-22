package be.walbert.DAO;

import java.io.ByteArrayInputStream;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Presents_List_API;
import oracle.jdbc.OracleTypes;

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
		try {
			CallableStatement callableStatement = connect.prepareCall("{call Find_Present(?, ?)}");

	        callableStatement.setInt(1, id);
	        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

	        callableStatement.execute();

	        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

	        if (resultSet.next()) {

	            int idPresent = resultSet.getInt("ID_PRESENT");
	            String name = resultSet.getString("NAME");
	            String description = resultSet.getString("DESCRIPTION");
	            double average_price = resultSet.getDouble("AVERAGE_PRICE");
	            int priority = resultSet.getInt("PRIORITY");
	            String statePresent = resultSet.getString("STATE");
	            String link = resultSet.getString("LINK");
	            byte[] image = resultSet.getBytes("IMAGE");
	            int idList = resultSet.getInt("ID_LIST");

	            Presents_List_API presents_list = Presents_List_API.find(idList);
	            Present_API present = new Present_API(idPresent, name, description, average_price, priority, statePresent, link, image, presents_list);
	            return present;
	        }
            return null;

		} catch (Exception e) {
	        e.printStackTrace();
 		}
		return null;
	}
	@Override
	public boolean update(Present_API obj) {
		try {
            CallableStatement cstmt = connect.prepareCall("{call UpdatePresent(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            cstmt.setInt(1, obj.getId_present());
            cstmt.setString(2, obj.getName());
            cstmt.setString(3, obj.getDescription());
            cstmt.setDouble(4, obj.getAverage_price());
            cstmt.setInt(5, obj.getPriority());
            cstmt.setString(6, obj.getState());

            // Handle the case when link is absent
            if (obj.getLink() != null && !obj.getLink().isEmpty()) {
                cstmt.setString(7, obj.getLink());
            } else {
                cstmt.setNull(7, java.sql.Types.VARCHAR);
            }

            // Handle the case when image is absent
            if (obj.getImage() != null) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(obj.getImage());
                cstmt.setBlob(8, inputStream);
            } else {
                cstmt.setNull(8, java.sql.Types.BLOB);
            }

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
