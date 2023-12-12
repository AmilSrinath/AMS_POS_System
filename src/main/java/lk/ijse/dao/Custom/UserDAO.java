package lk.ijse.dao.Custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    List<String> loadUserName() throws IOException;
    String getDisplayUserName(String username) throws IOException;
}
