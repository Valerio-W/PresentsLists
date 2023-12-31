package be.walbert.DAO;

import java.sql.Connection;

import be.walbert.Connection.ProjectConnection;
import be.walbert.Javabeans.Message_API;
import be.walbert.Javabeans.Multiple_Payment_API;
import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;

public class DAOFactory_API extends AbstractDAOFactory_API{

	protected static final Connection conn = ProjectConnection.getInstance();

	@Override
	public DAO<Users_API> getUserDAO_API() {
		
		return new UsersDAO_API(conn);
	}
	
	@Override
	public DAO<Presents_List_API> getPresents_ListDAO_API() {
		
		return new Presents_ListDAO_API(conn);
	}

	@Override
	public DAO<Present_API> getPresentDAO_API() {
		return new PresentDAO_API(conn);
	}
	
	@Override
	public DAO<Message_API> getMessageDAO_API() {
		return new MessageDAO_API(conn);
	}

	@Override
	public DAO<Multiple_Payment_API> getMultiple_PaymentDAO_API() {
		return new Multiple_PaymentDAO_API(conn);
	}
}