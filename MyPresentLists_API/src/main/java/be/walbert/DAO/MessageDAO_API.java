package be.walbert.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.walbert.Javabeans.Message_API;
import be.walbert.Javabeans.Users_API;
import oracle.jdbc.OracleTypes;

public class MessageDAO_API extends DAO<Message_API> {

	public MessageDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Message_API obj) {
	    try {

	        CallableStatement callableStatement = connect.prepareCall("{call Insert_Message(?, ?, ?)}");
	        callableStatement.setString(1, obj.getContent());
	        callableStatement.setInt(2, 1);
	        callableStatement.setInt(3, obj.getUser().getId());
	        callableStatement.execute();
	        callableStatement.close();

	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Message_API find(int id) {
	    try {
	        CallableStatement callableStatement = connect.prepareCall("{call Find_Message(?, ?)}");
	        callableStatement.setInt(1, id);
	        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
	        callableStatement.execute();

	        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

	        if (resultSet.next()) { 
	            int messageId = resultSet.getInt("ID_MESSAGE");
	            String content = resultSet.getString("CONTENT");
	            boolean checked = resultSet.getInt("CHECKED") == 1;
	            int userId = resultSet.getInt("ID_USERS");

	            Users_API user = Users_API.find(userId);
 	            Message_API message = new Message_API(messageId, content, checked, user);

 	           resultSet.close();
	           return message;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public boolean update(Message_API obj) {
	    try {
	        CallableStatement callableStatement = connect.prepareCall("{call Update_Message(?, ?, ?)}");
	        callableStatement.setInt(1, obj.getId_message());  
	        callableStatement.setString(2, obj.getContent());  
	        callableStatement.setInt(3, obj.isChecked() ? 1 : 0); 	        
	        callableStatement.execute();
	        callableStatement.close();
	        
	        return true;  
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;  
	    }
	}

	@Override
	public boolean delete(Message_API obj) {
		return false;
	}

	@Override
	public ArrayList<Message_API> findAll() {
		return null;
	}

}
