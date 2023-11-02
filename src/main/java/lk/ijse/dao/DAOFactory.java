package lk.ijse.dao;

import lk.ijse.dao.Custom.Impl.ItemDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        ITEM
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case ITEM:
                return new ItemDAOImpl();
            default:
                return null;
        }
    }
}
