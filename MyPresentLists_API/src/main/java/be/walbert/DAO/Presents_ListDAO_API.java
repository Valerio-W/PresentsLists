package be.walbert.DAO;

import java.sql.Connection;
import java.util.ArrayList;

public class Presents_ListDAO_API extends DAO<Presents_ListDAO_API>{

	public Presents_ListDAO_API(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Presents_ListDAO_API obj) {
		return false;
	}

	@Override
	public Presents_ListDAO_API find(int id) {
		return null;
	}

	@Override
	public boolean update(Presents_ListDAO_API obj) {
		return false;
	}

	@Override
	public boolean delete(Presents_ListDAO_API obj) {
		return false;
	}

	@Override
	public ArrayList<Presents_ListDAO_API> findAll() {
		return null;
	}
}
