package lk.ijse.dao.Custom.Impl;

import lk.ijse.dao.Custom.UserAddDAO;
import lk.ijse.entity.TM.UserAdd;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserAddDAOImpl implements UserAddDAO {
    public boolean isEmpty() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<Long> nativeQuery = session.createNativeQuery("SELECT COUNT(*) FROM user;");
        Long l = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (l==0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<UserAdd> getAll() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public boolean add(UserAdd entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(UserAdd entity) throws SQLException, ClassNotFoundException, IOException {
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
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT id FROM user ORDER BY CAST(SUBSTRING(id, 6) AS SIGNED) DESC LIMIT 1;");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("U-");
            int newID = Integer.parseInt(strings[1]) + 1;
            return "U-"+newID;
        }else {
            return "U-100000";
        }
    }
}
