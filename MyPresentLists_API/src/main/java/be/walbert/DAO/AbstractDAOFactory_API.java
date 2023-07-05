package be.walbert.DAO;

import be.walbert.Javabeans.Users_API;

public abstract class AbstractDAOFactory_API {

	public abstract DAO<Users_API> getUserDAO_API();

	public static AbstractDAOFactory_API getFactory() {
		return new DAOFactory_API();

	}
}