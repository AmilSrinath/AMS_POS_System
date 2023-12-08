package lk.ijse.dao;

import lk.ijse.dao.Custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        ITEM,ORDER,ORDERDETAIL,HOME,USERADD,USER,SETTING
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case HOME:
                return new HomeDAOImpl();
            case USERADD:
                return new UserAddDAOImpl();
            case USER:
                return new UserDAOImpl();
            case SETTING:
                return new SettingDAOImpl();
            default:
                return null;
        }
    }
}
