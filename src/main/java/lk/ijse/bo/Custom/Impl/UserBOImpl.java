package lk.ijse.bo.Custom.Impl;

import lk.ijse.bo.Custom.UserBO;
import lk.ijse.dao.Custom.UserDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.entity.Setting;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public List<User> getAllUser() throws SQLException, ClassNotFoundException, IOException {
        List<User> userList= new ArrayList<>();
        List<User> users = userDAO.getAll();
        for (User user : users) {
            userList.add(new User(user.getId(),user.getName(),user.getUsername(),user.getPassword(),user.getDisplayUsername(),user.getEmail(),user.getContactNumber(),new Setting()));
        }
        return userList;
    }

    @Override
    public boolean addUser(User dto) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean updateUser(User dto) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public String generateNewUserID() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }
}
