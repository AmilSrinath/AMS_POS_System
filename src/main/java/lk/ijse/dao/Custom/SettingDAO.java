package lk.ijse.dao.Custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Setting;

import java.io.IOException;

public interface SettingDAO extends CrudDAO<Setting> {
    boolean displayUsername(String settingID) throws IOException;
    boolean displayDate(String settingID) throws IOException;

    boolean displayTime(String settingID) throws IOException;
    String getNotificationSide(String settingID) throws IOException;
}
