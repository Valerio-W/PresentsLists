package be.walbert.DAO;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;
import oracle.jdbc.OracleTypes;

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
		    
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Presents_List_API find(int id) {
		try {
			CallableStatement callableStatement = connect.prepareCall("{call Find_List(?, ?)}");
	        callableStatement.setInt(1, id);
	        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
	        callableStatement.execute();

	        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
	        
	        if (resultSet.next()) {
	            int id_list = resultSet.getInt("ID_LIST");
	            LocalDate limit_date = resultSet.getDate("LIMIT_DATE").toLocalDate();
	            String occasion = resultSet.getString("OCCASION");
	            boolean state = resultSet.getInt("STATE") == 1;
	            int id_users = resultSet.getInt("ID_USERS");
	            
	            UsersDAO_API userDAO = new UsersDAO_API(connect);
	            Users_API user = userDAO.find(id_users);

	            Presents_List_API presentsList = new Presents_List_API(id_list, limit_date, occasion, state, user);
	            return presentsList;
	        }
		} catch (Exception e) {
	        e.printStackTrace();
 		}
		return null;
	}

	@Override
	public boolean update(Presents_List_API obj) {
	    try {
	        CallableStatement callableStatement = connect.prepareCall("{call Update_List(?, ?, ?, ?, ?)}");
	        callableStatement.setInt(1, obj.getId_list());
	        callableStatement.setDate(2, Date.valueOf(obj.getLimit_date()));
	        callableStatement.setString(3, obj.getOccasion());
	        callableStatement.setInt(4, obj.isState() ? 1 : 0);
	        callableStatement.setInt(5, obj.getOwner().getId());

	        callableStatement.execute();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean delete(Presents_List_API obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Presents_List_API> findAll() {
	    ArrayList<Presents_List_API> lists = new ArrayList<>();
	    try {
	        CallableStatement callableStatement = connect.prepareCall("{call Get_All_Lists(?)}");
	        callableStatement.registerOutParameter(1, OracleTypes.ARRAY, "LISTTABLE");
	        callableStatement.execute();
	        
	        Array array = callableStatement.getArray(1);
	        Object[] dataArray = (Object[]) array.getArray();
	        
	        for (Object data : dataArray) {
	            Struct struct = (Struct) data;
	            Object[] attributes = struct.getAttributes();
	            
	            int id_list = ((BigDecimal) attributes[0]).intValue();
	            Timestamp timestamp = (Timestamp) attributes[1];
	            LocalDate limit_date = timestamp.toLocalDateTime().toLocalDate();
	            String occasion = (String) attributes[2];
	            boolean state = ((BigDecimal) attributes[3]).intValue() == 1;
	            int id_users = ((BigDecimal) attributes[4]).intValue();

	            UsersDAO_API userDAO = new UsersDAO_API(connect);
	            Users_API user = userDAO.find(id_users);

	            Presents_List_API presentsList = new Presents_List_API(id_list, limit_date, occasion, state, user);
	            lists.add(presentsList);
	        } 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lists;
	}


	        
}
