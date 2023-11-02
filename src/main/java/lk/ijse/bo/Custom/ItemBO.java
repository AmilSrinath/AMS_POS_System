package lk.ijse.bo.Custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException, IOException;
    boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException, IOException;
    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException, IOException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException, IOException;
    String generateNewItemID() throws SQLException, ClassNotFoundException, IOException;
}
