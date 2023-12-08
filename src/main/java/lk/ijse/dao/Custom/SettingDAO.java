package lk.ijse.dao.Custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Setting;

import java.io.IOException;

public interface SettingDAO extends CrudDAO<Setting> {
    boolean displayUsername() throws IOException;
}
