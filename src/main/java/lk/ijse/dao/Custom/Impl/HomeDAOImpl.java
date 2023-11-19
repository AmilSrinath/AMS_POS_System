package lk.ijse.dao.Custom.Impl;

import lk.ijse.dao.SuperDAO;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;

public class HomeDAOImpl implements SuperDAO {
    public int setOrderCount(String date) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT COUNT(orderID) FROM orders WHERE date = :date";
        NativeQuery<Long> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("date",date);
        int count = Math.toIntExact(nativeQuery.uniqueResult());
        transaction.commit();
        session.close();
        return count;
    }

    public int setYesterdayOrderCount(String date) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT COUNT(orderID) FROM orders WHERE date = :date";
        NativeQuery<Long> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("date",date);
        int count = Math.toIntExact(nativeQuery.uniqueResult());
        transaction.commit();
        session.close();
        return count;
    }
}
