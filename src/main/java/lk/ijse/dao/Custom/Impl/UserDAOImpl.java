package lk.ijse.dao.Custom.Impl;

import lk.ijse.dao.Custom.UserDAO;
import lk.ijse.entity.Item;
import lk.ijse.entity.User;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM user");
        nativeQuery.addEntity(User.class);
        List<User> users = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM user WHERE id = :id";
        NativeQuery<User> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("id",id);
        nativeQuery.executeUpdate();
        transaction.commit();
        session.close();
        return true;
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
