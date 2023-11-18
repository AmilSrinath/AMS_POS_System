package lk.ijse.dao.Custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetail;

import java.io.IOException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    public String generateNewID() throws IOException;
    List<OrderDetail> getAllOrderDetails() throws IOException;
}
