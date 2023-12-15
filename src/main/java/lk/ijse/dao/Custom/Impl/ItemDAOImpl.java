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
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            String sql = "DELETE FROM item WHERE itemid = :itemid";
            NativeQuery<Item> nativeQuery = session.createNativeQuery(sql);
            nativeQuery.setParameter("itemid", id);
            nativeQuery.executeUpdate();
            transaction.commit();
            session.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT itemid FROM item ORDER BY CAST(SUBSTRING(itemid, 6) AS SIGNED) DESC LIMIT 1;");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("I-");
            int newID = Integer.parseInt(strings[1]) + 1;
            return "I-"+newID;
        }else {
            return "I-100000";
        }
    }

    @Override
    public List<String> loadItemID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT itemID FROM item");
        List<String> itemList = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return itemList;
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

    @Override
    public ItemDTO getItemDetailsWithName(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM item WHERE itemName = :itemName");
        nativeQuery.setParameter("itemName", id);
        Object[] result = (Object[]) nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (result != null && result.length > 0) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemID((String) result[0]);
            itemDTO.setItemName((String) result[1]);
            itemDTO.setItemQuantity((int) result[2]);
            itemDTO.setUnitCost((double) result[3]);
            itemDTO.setUnitSellingPrice((double) result[4]);

            return itemDTO;
        } else {
            return null;
        }
    }

    @Override
    public void updateQuantityWithItemID(String itemID, int quantity) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("UPDATE item SET  itemQuantity =  (itemQuantity- :quantity) WHERE itemID = :itemID");
        nativeQuery.setParameter("quantity", quantity);
        nativeQuery.setParameter("itemID", itemID);
        nativeQuery.executeUpdate();
        transaction.commit();
        session.close();
    }

    public int setHighestItemCount() throws IOException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            String sql = "select itemQuantity from Item ORDER BY itemQuantity DESC LIMIT 1";
            NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql); // Change the type to Integer
            int count = nativeQuery.uniqueResult(); // No need to cast, it's already an int
            transaction.commit();
            session.close();
            return count;
        }catch (Exception e){
            return 0;
        }
    }

    public int setLowestItemCount() throws IOException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            String sql = "select itemQuantity from Item ORDER BY itemQuantity LIMIT 1";
            NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql);
            int count = nativeQuery.uniqueResult();
            transaction.commit();
            session.close();
            return count;
        }catch (Exception e){
            return 0;
        }
    }

    public String setHighestItemName() throws IOException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery<String> nativeQuery = session.createNativeQuery("select itemName from Item ORDER BY itemQuantity DESC LIMIT 1;");
            String HighestItemName = nativeQuery.uniqueResult();
            transaction.commit();
            session.close();
            return HighestItemName;
        }catch (Exception e){
            return "Insert Item";
        }
    }

    public String setLowestItemName() throws IOException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery<String> nativeQuery = session.createNativeQuery("select itemName from Item ORDER BY itemQuantity LIMIT 1;");
            String LowestItemName = nativeQuery.uniqueResult();
            transaction.commit();
            session.close();
            return LowestItemName;
        }catch (Exception e){
            return "Insert Item";
        }
    }
}
