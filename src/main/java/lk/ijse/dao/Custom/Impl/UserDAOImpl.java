package lk.ijse.dao.Custom.Impl;

import lk.ijse.dao.Custom.UserDAO;
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

    @Override
    public List<String> loadUserName() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT username FROM user");
        List<String> usernames = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return usernames;
    }

    public boolean checkPassword(String username, String password) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT password FROM user WHERE username = :username");
        nativeQuery.setParameter("username", username);
        String pass = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (password.equals(pass)){
            return true;
        }else {
            return false;
        }
    }

    public boolean checkUsername(String username) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT username FROM user WHERE username = :username");
        nativeQuery.setParameter("username", username);
        String pass = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (username.equals(pass)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String getDisplayUserName(String username) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT displayUsername FROM user WHERE username = :username");
        nativeQuery.setParameter("username", username);
        String displayUsername = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        return displayUsername;
    }

    public boolean isAdmin(String username) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT id FROM user WHERE displayUsername = :username");
        nativeQuery.setParameter("username", username);
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        System.out.println(id);

        if (id.equals("U-100000")) {
            return true;
        }else {
            return false;
        }
    }

    public String getSettingIDWithDisplayUsername(String displayUsername) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT setting_id FROM user WHERE displayUsername = :displayUsername");
        nativeQuery.setParameter("displayUsername", displayUsername);
        String settingID = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        System.out.println("Setting ID - "+settingID);
        return settingID;
    }

    public String getEmailWithUsername(String username) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT email FROM user WHERE username = :username");
        nativeQuery.setParameter("username", username);
        String email = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        return email;
    }

    public boolean updateUserWithUsername(String username, String password) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("UPDATE user SET password = :password WHERE username = :username");
        nativeQuery.setParameter("password", password);
        nativeQuery.setParameter("username", username);
        nativeQuery.executeUpdate();
        transaction.commit();
        session.close();
        return true;
    }
}
