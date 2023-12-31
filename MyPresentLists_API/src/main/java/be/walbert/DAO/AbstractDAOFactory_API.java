package be.walbert.DAO;

import be.walbert.Javabeans.Message_API;
import be.walbert.Javabeans.Multiple_Payment_API;
import be.walbert.Javabeans.Present_API;
import be.walbert.Javabeans.Presents_List_API;
import be.walbert.Javabeans.Users_API;

public abstract class AbstractDAOFactory_API {

	public abstract DAO<Users_API> getUserDAO_API();

	public abstract DAO<Presents_List_API> getPresents_ListDAO_API();
	
	public abstract DAO<Present_API> getPresentDAO_API();
	
	public abstract DAO<Message_API> getMessageDAO_API();

	public abstract DAO<Multiple_Payment_API> getMultiple_PaymentDAO_API();

	public static AbstractDAOFactory_API getFactory() {
		return new DAOFactory_API();

	}
}
