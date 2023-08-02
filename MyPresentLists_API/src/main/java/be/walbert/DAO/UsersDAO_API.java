package be.walbert.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.Javabeans.Message_API;
import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;
import oracle.jdbc.OracleTypes;


public class UsersDAO_API extends DAO<Users_API>  {

	public UsersDAO_API(Connection conn) {
		super(conn);
	}
	
	/*private methods to call in crud methods*/
	private Users_API getUserDetails(String pseudo, String password) {
	    try (CallableStatement callableStatement_Users = connect.prepareCall("{call Get_User(?, ?, ?)}")) {
	    	callableStatement_Users.setString(1, pseudo);
	    	callableStatement_Users.setString(2, password);
	    	callableStatement_Users.registerOutParameter(3, OracleTypes.CURSOR);
	    	callableStatement_Users.execute();

	        ResultSet rs = (ResultSet) callableStatement_Users.getObject(3);
	        if (rs.next()) {
	            int users_id = rs.getInt("ID_USERS");
	            String email = rs.getString("EMAIL");
	            return new Users_API(users_id, pseudo, password, email);
	        }
	        
	        rs.close();
            callableStatement_Users.close();
            
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	private void getPresentsListByUser(Users_API user) throws SQLException, IOException {
	    try {
	    	CallableStatement callableStatement_PresentsList = connect.prepareCall("{call GetListsByUser(?, ?)}");
		    callableStatement_PresentsList.setInt(1, user.getId());
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
		       
	            CallableStatement callableStatement_Presents = connect.prepareCall("{call GetPresentsByListId(?, ?)}");
	            callableStatement_Presents.setInt(1, id_list);
	            callableStatement_Presents.registerOutParameter(2, OracleTypes.ARRAY, "PRESENTS_TABLE");
	            callableStatement_Presents.execute();
		        
	            Array arrayPresents = callableStatement_Presents.getArray(2);
	            Object[] dataArrayPresents = (Object[]) arrayPresents.getArray();
	            
	            for (Object dataPresents : dataArrayPresents) {
	                Object[] attributesPresents = ((Struct) dataPresents).getAttributes();
	                BigDecimal presentId_decimal = (BigDecimal)attributesPresents[0];
	                int presentId = presentId_decimal.intValue();
	                String name = (String)attributesPresents[1];
	                String description = (String)attributesPresents[2];
	                BigDecimal average_price_decimal = (BigDecimal) attributesPresents[3];
	                double average_price = average_price_decimal.doubleValue();
	                BigDecimal priority_decimal = (BigDecimal)attributesPresents[4];
	                int priority = priority_decimal.intValue();
	                String presentState = (String)attributesPresents[5];
	                String link=null;
	                if((String)attributesPresents[6]!=null) {
	                	 link = (String)attributesPresents[6];
	                }
	                Blob imageBlob = (Blob) attributesPresents[7];
	                byte[] image = null;
	                if(imageBlob != null) {
	                	InputStream inputStream = imageBlob.getBinaryStream();
		                image = inputStream.readAllBytes();
	                }
	                Present_API present = new Present_API(presentId, name, description, average_price, priority, presentState, link, image, presentsList);
	                presentsList.addPresent(present);
	            
	            }
	            user.addList(presentsList);
	        }

		    callableStatement_PresentsList.close();
		} catch (Exception e) {
	        e.printStackTrace();
		}
	}

	private void getGuestLists(Users_API user) throws SQLException {
	    try {
	    	CallableStatement callableStatement_GuestsList = connect.prepareCall("{call GetGuestsListByUser(?, ?)}");
		    callableStatement_GuestsList.setInt(1, user.getId());
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

		    callableStatement_GuestsList.close();
		} catch (Exception e) {
	        e.printStackTrace();
		}
		
	}

	private void getMessagesByUser(Users_API user) throws SQLException {
		try {
			CallableStatement callableStatement_Message = connect.prepareCall("{call GetMessagesByUser(?, ?)}");
	        callableStatement_Message.setInt(1, user.getId());
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
	        
	        callableStatement_Message.close();
		} catch (Exception e) {
	        e.printStackTrace();
 		}
		
	}
	
	/*CRUD methods*/
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
	                getGuestLists(user);
	                
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
	
	/*others methods*/
	public Users_API GetUser(String pseudo, String password) {
	    try {
	        Users_API user = getUserDetails(pseudo, password);
	        if (user != null) {
	            getPresentsListByUser(user);
	            getGuestLists(user);
	            getMessagesByUser(user);
	            return user;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public Users_API isUsersExist(Users_API users_API) {
	    try {
	        CallableStatement callableStatement = connect.prepareCall("{call isUsers_Exist(?,?,?)}");

	        callableStatement.setString(1, users_API.getPseudo());
	        callableStatement.setString(2, users_API.getEmail());
	        callableStatement.registerOutParameter(3, OracleTypes.CURSOR);

	        callableStatement.execute();

	        ResultSet resultSet = (ResultSet) callableStatement.getObject(3);

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
