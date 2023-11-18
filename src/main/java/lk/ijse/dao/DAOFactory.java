package lk.ijse.dao;

import lk.ijse.dao.Custom.Impl.ItemDAOImpl;
import lk.ijse.dao.Custom.Impl.OrderDAOImpl;
import lk.ijse.dao.Custom.Impl.OrderDetailDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        ITEM,ORDER,ORDERDETAIL
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
