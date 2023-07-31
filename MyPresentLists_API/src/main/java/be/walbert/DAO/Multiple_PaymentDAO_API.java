package be.walbert.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;

import be.walbert.Javabeans.Multiple_Payment_API;
import be.walbert.Javabeans.Present_API;

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
		try {
			
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean delete(Multiple_Payment_API obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Multiple_Payment_API find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Multiple_Payment_API> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
