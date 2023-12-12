package lk.ijse.dao.Custom.Impl;

import jakarta.persistence.NoResultException;
import lk.ijse.dao.Custom.SettingDAO;
import lk.ijse.entity.Setting;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SettingDAOImpl implements SettingDAO {
    @Override
    public List<Setting> getAll() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public boolean add(Setting entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Setting entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Setting SET showUsername = :showUsername, showDate = :showDate, showTime = :showTime, showNotification = :showNotification WHERE settingID = :settingID";

        Query query = session.createQuery(hql);
        query.setParameter("showUsername", entity.getShowUsername());
        query.setParameter("showDate", entity.getShowDate());
        query.setParameter("showTime", entity.getShowTime());
        query.setParameter("showNotification", entity.getShowNotification());
        query.setParameter("settingID", entity.getSettingID());

        int result = query.executeUpdate();

        transaction.commit();
        session.close();

        return result > 0;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT settingID FROM setting ORDER BY CAST(SUBSTRING(settingID, 6) AS SIGNED) DESC LIMIT 1;");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("S-");
            int newID = Integer.parseInt(strings[1]) + 1;
            return "S-"+newID;
        }else {
            return "S-100000";
        }
    }

    @Override
    public boolean displayUsername(String settingID) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT showUsername FROM setting WHERE settingID = :settingID");
        nativeQuery.setParameter("settingID", settingID);
        String isTrue = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (isTrue.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean displayDate(String settingID) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT showDate FROM setting WHERE settingID = :settingID");
        nativeQuery.setParameter("settingID", settingID);
        String isTrue = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (isTrue.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean displayTime(String settingID) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT showTime FROM setting WHERE settingID = :settingID");
        nativeQuery.setParameter("settingID", settingID);
        String isTrue = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (isTrue.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String getNotificationSide(String settingID) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT showNotification FROM setting WHERE settingID = :settingID");
        nativeQuery.setParameter("settingID", settingID);
        String notificationSide = nativeQuery.uniqueResult();

        transaction.commit();
        session.close();

        return notificationSide;
    }
}
