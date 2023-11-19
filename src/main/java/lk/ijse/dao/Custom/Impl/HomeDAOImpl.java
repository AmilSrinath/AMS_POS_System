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

    public int setSellItemTypeCount(String date) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT COUNT(DISTINCT itemID) AS itemCount FROM order_details JOIN orders ON order_details.orderID = orders.orderID Where orders.date = :date";
        NativeQuery<Long> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("date",date);
        int count = Math.toIntExact(nativeQuery.uniqueResult());
        transaction.commit();
        session.close();
        return count;
    }

    public int setYesterdaySellItemTypeCount(String date) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT COUNT(DISTINCT itemID) AS itemCount FROM order_details JOIN orders ON order_details.orderID = orders.orderID Where orders.date = :date";
        NativeQuery<Long> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("date",date);
        int count = Math.toIntExact(nativeQuery.uniqueResult());
        transaction.commit();
        session.close();
        return count;
    }

    public double setProfit(String date) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT SUM(subTotal)-SUM(unitCost) FROM order_details JOIN orders ON order_details.orderID = orders.orderID WHERE orders.date = :date";
        NativeQuery<Double> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("date",date);
        double profit = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        return profit;
    }

    public double setYesterdayProfit(String date) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT SUM(subTotal)-SUM(unitCost) FROM order_details JOIN orders ON order_details.orderID = orders.orderID WHERE orders.date = :date";
        NativeQuery<Double> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("date",date);
        double profit = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        return profit;
    }
}
