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

import be.walbert.Javabeans.Message_API;
import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;
import oracle.jdbc.OracleTypes;


public class UsersDAO_API extends DAO<Users_API>  {

	public UsersDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Users_API obj) {
		try {
			CallableStatement callableStatement = connect.prepareCall("{call Insert_User(?,?,?)}");
		        		        
		    callableStatement.setString(1, obj.getPseudo());
		    callableStatement.setString(2, obj.getPassword());
		    callableStatement.setString(3, obj.getEmail());
	        
		    callableStatement.execute();
		    
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Users_API find(int id) {
		 try {
			 	CallableStatement callableStatement = connect.prepareCall("{call Find_User(?, ?)}");
	            callableStatement.setInt(1, id);
	            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
	            callableStatement.execute();

	            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

	            if (resultSet.next()) {
	                int id_users = resultSet.getInt("ID_USERS");
	                String pseudo = resultSet.getString("PSEUDO");
	                String password = resultSet.getString("PASSWORD");
	                String email = resultSet.getString("EMAIL");

	                Users_API user = new Users_API(id_users, pseudo, password, email);
	                return user;
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
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
	
	public Users_API GetUser(String pseudo, String password) {
		try {

			CallableStatement callableStatement_Users = connect.prepareCall("{call Get_User(?, ?, ?)}");
	        CallableStatement callableStatement_PresentsList = connect.prepareCall("{call GetListsByUser(?, ?)}");

	        callableStatement_Users.setString(1, pseudo);
	        callableStatement_Users.setString(2, password);
	        callableStatement_Users.registerOutParameter(3, OracleTypes.CURSOR);
	        callableStatement_Users.execute();

	        ResultSet rs = (ResultSet) callableStatement_Users.getObject(3);

	        if (rs.next()) {
	        	int users_id = rs.getInt("ID_USERS");
	            String email = rs.getString("EMAIL");
	            Users_API user = new Users_API(users_id, pseudo, password, email);
	            
	            callableStatement_PresentsList.setInt(1, users_id);
	            callableStatement_PresentsList.registerOutParameter(2, OracleTypes.ARRAY, "LISTTABLE");
	            callableStatement_PresentsList.execute();

	            Array array = callableStatement_PresentsList.getArray(2);
	            Object[] dataArray = (Object[]) array.getArray();

	            for (Object data : dataArray) {
	                Object[] attributes = ((Struct) data).getAttributes();
	                
	                int id_list = ((BigDecimal) attributes[0]).intValue();
	                LocalDate limit_date = ((Timestamp) attributes[1]).toLocalDateTime().toLocalDate();
	                String occasion = (String) attributes[2];
	                int state = ((BigDecimal) attributes[3]).intValue();
	                
	                boolean stateValue = (state == 1);
	                Presents_List_API presentsList = new Presents_List_API(id_list, limit_date, occasion, stateValue, user);
	                user.addList(presentsList);
	            }

		        CallableStatement callableStatement_GuestsList = connect.prepareCall("{call GetGuestsListByUser(?, ?)}");

		        callableStatement_GuestsList.setInt(1, users_id);
		        callableStatement_GuestsList.registerOutParameter(2, OracleTypes.ARRAY, "GUESTS_TABLE");
		        callableStatement_GuestsList.execute();
		        
		        Array arrayGuestList = callableStatement_GuestsList.getArray(2);
	            Object[] dataArrayGuestList = (Object[]) arrayGuestList.getArray();

	            for (Object dataGuestList : dataArrayGuestList) {
	                Object[] attributesGuestList = ((Struct) dataGuestList).getAttributes();
	                
	                int id_list = ((BigDecimal) attributesGuestList[1]).intValue();
	                
	                Presents_List_API presentsList = Presents_List_API.find(id_list);
	                user.addGuestList(presentsList);
	            }
	            
		        CallableStatement callableStatement_Message = connect.prepareCall("{call GetMessagesByUser(?, ?)}");
		        callableStatement_Message.setInt(1, users_id);
		        callableStatement_Message.registerOutParameter(2, OracleTypes.ARRAY, "MESSAGE_TABLE");
		        callableStatement_Message.execute();
		        
		        
		        Array arrayMessages = callableStatement_Message.getArray(2);
	            Object[] dataArrayMessages = (Object[]) arrayMessages.getArray();

	            for (Object dataMessage : dataArrayMessages) {
	                Object[] attributesMessages = ((Struct) dataMessage).getAttributes();
	                
	                int id_message = ((BigDecimal) attributesMessages[0]).intValue();
	                
	                Message_API message = Message_API.find(id_message);
	                user.addMessage(message);
	            }
		        
		        
		        
	            rs.close();
	            callableStatement_Users.close();
	            callableStatement_PresentsList.close();
	            callableStatement_GuestsList.close();
	            callableStatement_Message.close();
	            
	            return user;
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}

	public boolean checkAccount(Users_API users_API) {
		try {

			CallableStatement callableStatement = connect.prepareCall("{call Check_Email(?,?,?)}");
		       			
		    callableStatement.setString(1, users_API.getPseudo());
		    callableStatement.setString(2, users_API.getEmail());
		    callableStatement.registerOutParameter(3, Types.NUMERIC);

		    callableStatement.execute();

		    int result = callableStatement.getInt(3);

		    if (result == 1) {//If pseudo or email already taken
		    	return true;
		      } else {
		    	return false;
		      }
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
		return false;
	}

	public Users_API isUsersExist(Users_API users_API) {
	    try {
	        CallableStatement callableStatement = connect.prepareCall("{call isUsers_Exist(?, ?)}");

	        callableStatement.setString(1, users_API.getPseudo());
	        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

	        callableStatement.execute();

	        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

	        if (resultSet.next()) {
 	            int id_users = resultSet.getInt("ID_USERS");
	            String pseudo = resultSet.getString("PSEUDO");
	            String password = resultSet.getString("PASSWORD");
	            String email = resultSet.getString("EMAIL");

	            Users_API existingUser = new Users_API(id_users, pseudo, password, email);
	            return existingUser;
	        } else {
	            return null;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
