package lk.ijse.dao.Custom.Impl;

import lk.ijse.dao.Custom.OrderDAO;
import lk.ijse.entity.Order;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public boolean add(Order entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT orderID FROM orders ORDER BY CAST(SUBSTRING(orderID, 3) AS SIGNED) DESC LIMIT 1;");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("O-");
            int newID = Integer.parseInt(strings[1]) + 1;
            return "O-"+newID;
        }else {
            return "O-100";
        }
    }
}