package lk.ijse.dao.Custom.Impl;

import lk.ijse.dao.Custom.ItemDAO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public List<Item> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM item");
        nativeQuery.addEntity(Item.class);
        List<Item> itemList = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return itemList;
    }

    @Override
    public boolean add(Item entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM item WHERE itemid = :itemid";
        NativeQuery<Item> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("itemid",id);
        nativeQuery.executeUpdate();
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public List<String> loadItemID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT itemID FROM item");
        List<String> studentIds = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return studentIds;
    }

    @Override
    public List<String> loadItemName() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT itemName FROM item");
        List<String> studentIds = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return studentIds;
    }

    @Override
    public ItemDTO getItemDetails(String itemID) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM item WHERE itemID = :itemID");
        nativeQuery.setParameter("itemID", itemID);
        Object[] result = (Object[]) nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (result != null && result.length > 0) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemName((String) result[1]);
            itemDTO.setItemQuantity((int) result[2]);
            itemDTO.setUnitCost((double) result[3]);
            itemDTO.setUnitSellingPrice((double) result[4]);

            return itemDTO;
        } else {
            return null;
        }
    }
}
