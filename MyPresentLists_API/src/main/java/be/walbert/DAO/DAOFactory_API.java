package be.walbert.DAO;

import java.sql.Connection;

import be.walbert.Connection.ProjectConnection;
import be.walbert.Javabeans.Users_API;

public class DAOFactory_API extends AbstractDAOFactory_API{

	protected static final Connection conn = ProjectConnection.getInstance();

	@Override
	public DAO<Users_API> getUserDAO_API() {
		
		return new UsersDAO_API(conn);
	}
}