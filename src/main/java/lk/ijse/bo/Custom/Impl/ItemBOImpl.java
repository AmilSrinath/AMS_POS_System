package lk.ijse.bo.Custom.Impl;

import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dao.Custom.ItemDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException, IOException {
        List<ItemDTO> itemDTOList= new ArrayList<>();
        List<Item> itemList = itemDAO.getAll();
        for (Item item : itemList) {
            itemDTOList.add(new ItemDTO(item.getItemID(), item.getItemName(), item.getItemQuantity(), item.getUnitSellingPrice(), item.getUnitCost()));
        }
        return itemDTOList;
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return itemDAO.add(new Item(dto.getItemID(), dto.getItemName(), dto.getItemQuantity(), dto.getUnitSellingPrice(), dto.getUnitCost()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return itemDAO.update(new Item(dto.getItemID(),dto.getItemName(), dto.getItemQuantity(), dto.getUnitSellingPrice(), dto.getUnitCost()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException, IOException {
        return itemDAO.delete(id);
    }

    @Override
    public String generateNewItemID() throws SQLException, ClassNotFoundException, IOException {
        return itemDAO.generateNewID();
    }
}
