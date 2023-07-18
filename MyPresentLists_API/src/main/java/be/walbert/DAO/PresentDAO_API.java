package be.walbert.DAO;

import java.sql.Connection;
import java.util.ArrayList;

import be.walbert.Javabeans.Present_API;

public class PresentDAO_API extends DAO<Present_API> {

	public PresentDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Present_API obj) {
		// TODO Auto-generated method stub
		return false;
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
