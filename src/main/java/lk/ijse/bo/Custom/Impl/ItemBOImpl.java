package lk.ijse.bo.Custom.Impl;

import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dto.ItemDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public String generateNewItemID() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }
}
