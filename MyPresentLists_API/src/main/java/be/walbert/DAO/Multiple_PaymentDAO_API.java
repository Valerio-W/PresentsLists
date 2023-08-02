package be.walbert.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.walbert.Javabeans.Multiple_Payment_API;
import oracle.jdbc.OracleTypes;

public class Multiple_PaymentDAO_API extends DAO<Multiple_Payment_API> {

	public Multiple_PaymentDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Multiple_Payment_API obj) {
		try {
	        CallableStatement callableStatement = connect.prepareCall("{call Insert_Multiple_Payment(?, ?, ?)}");

	        callableStatement.setDouble(1, obj.getPrice_paid());
	        callableStatement.setInt(2, obj.getPresent().getId_present());
	        callableStatement.setInt(3, obj.getUser().getId());
	        callableStatement.execute();
	        callableStatement.close();

	        return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean update(Multiple_Payment_API obj) {
		return false;
	}

	@Override
	public boolean delete(Multiple_Payment_API obj) {
		return false;
	}

	@Override
	public Multiple_Payment_API find(int id) {
		try {
			CallableStatement callableStatement = connect.prepareCall("{call Find_Multiple_Payment(?, ?)}");

	        callableStatement.setInt(1, id);
	        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

	        callableStatement.execute();

	        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

	        if (resultSet.next()) {

	            int id_payment = resultSet.getInt("ID_PAYMENT");
	            double price_paid = resultSet.getDouble("PRICE_PAID");
	            int id_present = resultSet.getInt("ID_PRESENT");
	            int id_users = resultSet.getInt("ID_USERS");
	            PresentDAO_API presentdao = new PresentDAO_API(connect);
	            UsersDAO_API usersdao = new UsersDAO_API(connect);
	            Multiple_Payment_API multiple_payment = new Multiple_Payment_API(id_payment, price_paid,presentdao.find(id_present),usersdao.find(id_users));
 	            
	            resultSet.close();
	            return multiple_payment;
	        }
            return null;

		} catch (Exception e) {
	        e.printStackTrace();
 		}
		return null;
	}

	@Override
	public ArrayList<Multiple_Payment_API> findAll() {
		return null;
	}
}
