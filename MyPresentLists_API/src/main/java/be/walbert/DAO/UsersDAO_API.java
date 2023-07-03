package be.walbert.DAO;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.ArrayList;

import be.walbert.Javabeans.Users_API;
import oracle.jdbc.OracleTypes;


public class UsersDAO_API extends DAO<Users_API>  {

	public UsersDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Users_API obj) {
		return false;
	}

	@Override
	public Users_API find(int id) {
		return null;
	}

	@Override
	public boolean update(Users_API obj) {
		return false;
	}

	@Override
	public boolean delete(Users_API obj) {
		return false;
	}

	public ArrayList<Users_API> findAll() {
	    ArrayList<Users_API> users = new ArrayList<>();
	    
	    try {
	        CallableStatement callableStatement = connect.prepareCall("{call Get_All_Users(?)}");
	        
	        callableStatement.registerOutParameter(1, Types.ARRAY, "USERS_TABLE");
	        
	        callableStatement.execute();
	        
	        Array array = callableStatement.getArray(1);
	        Object[] dataArray = (Object[]) array.getArray();
	        
	        for (Object data : dataArray) {
	            Struct struct = (Struct) data;
	            Object[] attributes = struct.getAttributes();
	            
	            int id_users = ((BigDecimal) attributes[0]).intValue();
	            String pseudo = (String) attributes[1];
	            String password = (String) attributes[2];
	            String email = (String) attributes[3];
	            
	            Users_API user = new Users_API(id_users, pseudo, password, email);
	            users.add(user);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return users;
	}
}
