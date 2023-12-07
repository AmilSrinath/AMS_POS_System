package lk.ijse.bo.Custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    List<User> getAllUser() throws SQLException, ClassNotFoundException, IOException;
    boolean addUser(User dto) throws SQLException, ClassNotFoundException, IOException;
    boolean updateUser(User dto) throws SQLException, ClassNotFoundException, IOException;
    boolean deleteUser(String id) throws SQLException, ClassNotFoundException, IOException;
    String generateNewUserID() throws SQLException, ClassNotFoundException, IOException;
}
