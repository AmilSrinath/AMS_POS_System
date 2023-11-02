package lk.ijse.dao.Custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;

import java.io.IOException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    List<String> loadItemID() throws IOException;

    List<String> loadItemName() throws IOException;

    ItemDTO getItemDetails(String itemID) throws IOException;
}
