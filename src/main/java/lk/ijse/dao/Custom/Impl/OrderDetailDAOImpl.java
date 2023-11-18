package lk.ijse.dao.Custom.Impl;

import lk.ijse.dao.Custom.OrderDetailDAO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public boolean add(OrderDetail entity) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT orderDetailID FROM order_details ORDER BY CAST(SUBSTRING(orderDetailID, 6) AS SIGNED) DESC LIMIT 1;");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null){
            if (id.equals("999999")){
                id="100000";
            }
        }

        if (id != null) {
            String[] strings = id.split("OD-");
            int newID = Integer.parseInt(strings[1]);
            return "OD-"+newID;
        }else {
            return "OD-100000";
        }
    }
}