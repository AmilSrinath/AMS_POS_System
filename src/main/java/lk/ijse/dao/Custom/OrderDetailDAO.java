package lk.ijse.dao.Custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetail;

import java.io.IOException;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    public String generateNewID() throws IOException;
}
