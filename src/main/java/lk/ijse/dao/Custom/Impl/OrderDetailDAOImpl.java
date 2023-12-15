package lk.ijse.dao.Custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.dao.Custom.OrderDetailDAO;
import lk.ijse.dto.OrderAndOrderDetailsDTO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.TM.OrderDetailTM;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

        if (id != null) {
            String[] strings = id.split("OD-");
            int newID = Integer.parseInt(strings[1]);
            return "OD-"+newID;
        }else {
            return "OD-10000000";
        }
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM order_details");
        nativeQuery.addEntity(OrderDetail.class);
        List<OrderDetail> orderDetailEntities = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return orderDetailEntities;
    }

    @Override
    public List<OrderAndOrderDetailsDTO> getAllItems(String orderID) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery<Object[]> nativeQuery = session.createNativeQuery("SELECT order_details.orderDetailID, order_details.itemID, order_details.itemName, order_details.quantity, order_details.unitPrice, order_details.unitCost, order_details.subTotal, orders.orderID, orders.netTotal, orders.date, orders.time FROM order_details JOIN orders ON order_details.orderID = orders.orderID WHERE orders.orderID = :orderID");
        nativeQuery.setParameter("orderID", orderID);
        List<Object[]> results = nativeQuery.getResultList();
        List<OrderAndOrderDetailsDTO> orderDetailEntities = new ArrayList<>();

        for (Object[] result : results) {
            OrderAndOrderDetailsDTO orderAndOrderDetailsDTO = new OrderAndOrderDetailsDTO();

            orderAndOrderDetailsDTO.setOrderDetailID((String) result[0]);
            orderAndOrderDetailsDTO.setItemID((String) result[1]);
            orderAndOrderDetailsDTO.setItemName((String) result[2]);
            orderAndOrderDetailsDTO.setQuantity((int) result[3]);
            orderAndOrderDetailsDTO.setUnitSellingPrice((double) result[4]);
            orderAndOrderDetailsDTO.setUnitCost((double) result[5]);
            orderAndOrderDetailsDTO.setSubTotal((double) result[6]);
            orderAndOrderDetailsDTO.setOrderID((String) result[7]);
            orderAndOrderDetailsDTO.setNetTotal((double) result[8]);
            orderAndOrderDetailsDTO.setDate((String) result[9]);
            orderAndOrderDetailsDTO.setTime((String) result[10]);

            orderDetailEntities.add(orderAndOrderDetailsDTO);
        }

        transaction.commit();
        session.close();
        return orderDetailEntities;
    }

    public double NetTotalCalculate(String orderID) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT SUM(subTotal) FROM order_details WHERE orderID = :orderID";
        NativeQuery<Double> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("orderID",orderID);
        double netTotal = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        return netTotal;
    }
}