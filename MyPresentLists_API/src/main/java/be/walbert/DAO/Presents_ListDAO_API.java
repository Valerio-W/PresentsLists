package be.walbert.DAO;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
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
import be.walbert.Javabeans.Multiple_Payment_API;
import be.walbert.Javabeans.Present_API;
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
		    Present_API first_present = obj.getPresents().get(0);
		    first_present.getList().setId_list(callableStatement.getInt(5));
		    PresentDAO_API presentDAO= new PresentDAO_API(connect);
		    
		    //When a new presents_list is created a first present is added too
		    if( presentDAO.create(obj.getPresents().get(0))) {
		        return true;
		    }
		    return false;
		    
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Presents_List_API find(int id) {
		try {
			CallableStatement callableStatement = connect.prepareCall("{call Find_List(?, ?)}");
			CallableStatement presents_callableStatement = connect.prepareCall("{call GetPresentsByListId(?, ?)}");
			presents_callableStatement.setInt(1, id);
			presents_callableStatement.registerOutParameter(2, Types.ARRAY, "PRESENTS_TABLE");

	        callableStatement.setInt(1, id);
	        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
	        callableStatement.execute();
			presents_callableStatement.execute();

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
	            
	            Array array = presents_callableStatement.getArray(2);
		        Object[] dataArray = (Object[]) array.getArray();
		        
		        for (Object dataPresents : dataArray) {
		            Struct structPresents = (Struct) dataPresents;
		            Object[] attributesPresents = structPresents.getAttributes();
		            
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
	                CallableStatement callableStatementMultiplePayment = connect.prepareCall("{call GetMultiplePaymentByPresent(?, ?)}");
		            callableStatementMultiplePayment.setInt(1, presentId);
		            callableStatementMultiplePayment.registerOutParameter(2, OracleTypes.ARRAY, "MULTIPLE_PAYMENTTABLE");
		            callableStatementMultiplePayment.execute();

		            Array arrayMultiplePayment = callableStatementMultiplePayment.getArray(2);
			        Object[] dataArrayMultiplePayment = (Object[]) arrayMultiplePayment.getArray();
			        if (arrayMultiplePayment != null) {
		                for (Object dataMultiplePayment : dataArrayMultiplePayment) {
		                	Struct structPresentsMultiplePayment = (Struct) dataMultiplePayment;
				            Object[] attributesMultiplePayment = structPresentsMultiplePayment.getAttributes();
				            
			                BigDecimal multiple_payment_id_decimal = (BigDecimal)attributesMultiplePayment[0];
			                int multiple_payment_id = multiple_payment_id_decimal.intValue(); 
			                BigDecimal price_paid_decimal = (BigDecimal)attributesMultiplePayment[1];
			                double price_paid = price_paid_decimal.doubleValue(); 
			                BigDecimal id_users_decimal = (BigDecimal)attributesMultiplePayment[3];
			                int id_users_guest = id_users_decimal.intValue(); 
			                Users_API users_guest = Users_API.find(id_users_guest);
				            Multiple_Payment_API multiple_payment = new Multiple_Payment_API(multiple_payment_id, price_paid, present, users_guest);
				            present.getPayments().add(multiple_payment);
		                }
		            }
	                presentsList.getPresents().add(present);
	            }
		        
		        CallableStatement getGuestsStatement = connect.prepareCall("{call GetGuestsByListId(?, ?)}");
	            getGuestsStatement.setInt(1, id);
	            getGuestsStatement.registerOutParameter(2, OracleTypes.ARRAY, "GUESTS_TABLE");
	            getGuestsStatement.execute();

	            Array guestsArray = getGuestsStatement.getArray(2);
	            Object[] guestsDataArray = (Object[]) guestsArray.getArray();

	            for (Object dataGuests : guestsDataArray) {
	                Struct structGuest = (Struct) dataGuests;
	                Object[] attributesGuest = structGuest.getAttributes();

	                int guestUserId = ((BigDecimal) attributesGuest[2]).intValue();
	                Users_API newGuest = Users_API.find(guestUserId);
	                presentsList.getGuests().add(newGuest);
	            }
	            resultSet.close();
	            callableStatement.close();
	            presents_callableStatement.close();
	            getGuestsStatement.close();
	            
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
	        
	        for (Users_API guest : obj.getGuests()) {
	            // Check if guest is already in database
	            CallableStatement checkGuestStatement = connect.prepareCall("{call guest_AlreadyExist(?, ?, ? )}");
	            checkGuestStatement.setInt(1, obj.getId_list());
	            checkGuestStatement.setInt(2, guest.getId());
	            checkGuestStatement.registerOutParameter(3, Types.NUMERIC);

	            checkGuestStatement.execute();

	            int guestExists = checkGuestStatement.getInt(3);

	            if (guestExists == 0) {
	                CallableStatement insertGuestStatement = connect.prepareCall("{call Insert_Guest(?, ?)}");
	                insertGuestStatement.setInt(1, obj.getId_list());
	                insertGuestStatement.setInt(2, guest.getId());
	                insertGuestStatement.execute();
	            }
	            
	            callableStatement.close();
	        }
	        
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