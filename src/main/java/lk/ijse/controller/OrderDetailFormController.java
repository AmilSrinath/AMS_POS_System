package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dao.Custom.OrderDetailDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.OrderAndOrderDetailsDTO;
import lk.ijse.entity.TM.OrderDetailsTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailFormController implements Initializable {
    public Label lblOrderID;
    public Label lblNetTotal;
    public Label lblDate;
    public Label lblTime;
    @FXML
    private TableView<OrderDetailsTM> tblOrderDetail;

    @FXML
    private TableColumn<OrderDetailsTM, ?> colorderDetailID;

    @FXML
    private TableColumn<OrderDetailsTM, ?> colitemID;

    @FXML
    private TableColumn<OrderDetailsTM, ?> colitemName;

    @FXML
    private TableColumn<OrderDetailsTM, ?> colquantity;

    @FXML
    private TableColumn<OrderDetailsTM, ?> colunitPrice;

    @FXML
    private TableColumn<OrderDetailsTM, ?> colunitCost;

    @FXML
    private TableColumn<OrderDetailsTM, ?> colsubTotal;
    private List<OrderAndOrderDetailsDTO> orderAndOrderDetailsDTOS = new ArrayList<>();
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    ObservableList<OrderDetailsTM> observableList = FXCollections.observableArrayList(); // Initialize here

    public void setOrderID(String orderID) throws IOException {
        orderAndOrderDetailsDTOS = orderDetailDAO.getAllItems(orderID);

        for (OrderAndOrderDetailsDTO orderAndOrderDetailsDTO : orderAndOrderDetailsDTOS){
            lblOrderID.setText(orderAndOrderDetailsDTO.getOrderID());
            lblDate.setText(orderAndOrderDetailsDTO.getDate());
            lblTime.setText(orderAndOrderDetailsDTO.getTime());
            lblNetTotal.setText(String.valueOf(orderAndOrderDetailsDTO.getNetTotal()));

            OrderDetailsTM orderDetailsTM = new OrderDetailsTM();
            orderDetailsTM.setOrderDetailID(orderAndOrderDetailsDTO.getOrderDetailID());
            orderDetailsTM.setItemID(orderAndOrderDetailsDTO.getItemID());
            orderDetailsTM.setItemName(orderAndOrderDetailsDTO.getItemName());
            orderDetailsTM.setQuantity(orderAndOrderDetailsDTO.getQuantity());
            orderDetailsTM.setUnitSellingPrice(orderAndOrderDetailsDTO.getUnitSellingPrice());
            orderDetailsTM.setSubTotal(orderAndOrderDetailsDTO.getSubTotal());
            orderDetailsTM.setUnitCost(orderAndOrderDetailsDTO.getUnitCost());

            observableList.add(orderDetailsTM);
        }

        tblOrderDetail.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colorderDetailID.setCellValueFactory(new PropertyValueFactory<>("orderDetailID"));
        colitemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colitemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colunitPrice.setCellValueFactory(new PropertyValueFactory<>("unitSellingPrice"));
        colunitCost.setCellValueFactory(new PropertyValueFactory<>("unitCost"));
        colsubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
    }
}
