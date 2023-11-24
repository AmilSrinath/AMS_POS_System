package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {

    public JFXComboBox<String> comSelectRiceType;
    public AnchorPane selectWeight;
    @FXML
    private JFXTextField txtItemID;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemQuantity;

    @FXML
    private JFXTextField txtUnitSellingPrice;

    @FXML
    private JFXTextField txtUnitCost;
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            generateNextItemId();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanTextField(){
        txtItemID.clear();
        txtItemName.clear();
        txtItemQuantity.clear();
        txtUnitSellingPrice.clear();
        txtUnitCost.clear();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        String itemID = txtItemID.getText();
        String itemName = txtItemName.getText();
        int itemQut = Integer.parseInt(txtItemQuantity.getText());
        double itemUnitSellingPrice = Double.parseDouble(txtUnitSellingPrice.getText());
        double itemUnitCost = Double.parseDouble(txtUnitCost.getText());

        if (itemBO.addItem(new ItemDTO(itemID, itemName, itemQut, itemUnitSellingPrice, itemUnitCost, new HashSet<OrderDetail>()))) {
            Notifications.create()
                    .title("සාර්ථකයි...!")
                    .text("අයිතමය සාර්ථකව එකතුකර ගෙන ඇත.")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .showConfirm();

        } else {
            Notifications.create()
                    .title("අසාර්ථකයි...!")
                    .text("අයිතමය සාර්ථකව එකතුකර ගෙන නැත. නැවත උත්සහා කරන්න..!")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .showConfirm();
        }
        cleanTextField();
        generateNextItemId();
    }
    private void generateNextItemId() throws SQLException, IOException, ClassNotFoundException {
        String nextId = itemBO.generateNewItemID();
        txtItemID.setText(nextId);
    }

}
