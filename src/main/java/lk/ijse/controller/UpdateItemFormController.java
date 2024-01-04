package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.Impl.DataRefreshListener;
import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dao.Custom.ItemDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFilds;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

public class UpdateItemFormController {
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @FXML
    public JFXTextField txtItemID;
    @FXML
    public JFXTextField txtItemName;
    @FXML
    private JFXTextField txtItemQuantity;
    @FXML
    private JFXTextField txtUnitSellingPrice;
    @FXML
    private JFXTextField txtUnitCost;

    public void cleanTextField(){
        txtItemName.clear();
        txtItemQuantity.clear();
        txtUnitSellingPrice.clear();
        txtUnitCost.clear();
    }

    private DataRefreshListener dataRefreshListener;

    public void setDataRefreshListener(DataRefreshListener dataRefreshListener) {
        this.dataRefreshListener = dataRefreshListener;
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (!isValidated()){
            Notifications.create()
                    .title("Not Successfully...!")
                    .text("Please fill the TextFiled...!")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.valueOf(HomeFormController.notify))
                    .showError();

            return;
        }

        String itemID = txtItemID.getText();
        String itemName = txtItemName.getText();
        int itemQut = Integer.parseInt(txtItemQuantity.getText());
        double itemUnitSellingPrice = Double.parseDouble(txtUnitSellingPrice.getText());
        double itemUnitCost = Double.parseDouble(txtUnitCost.getText());

        if (!itemBO.updateItem(new ItemDTO(itemID, itemName, itemQut, itemUnitSellingPrice, itemUnitCost, new HashSet<OrderDetail>()))) {
            new Alert(Alert.AlertType.ERROR, "Error!!").show();
        }

        if (dataRefreshListener != null) {
            dataRefreshListener.onRefresh();
        }

        cleanTextField();
        stage.getScene().getWindow().hide();
        homeForm.setDisable(false);

        // Handle close event to enable HomeForm when ViewItemForm is closed
        stage.setOnCloseRequest(windowEvent -> homeForm.setDisable(true));
    }

    public void txtItemIDOnAction(ActionEvent actionEvent) throws IOException {
        String itemID = txtItemID.getText();
        ItemDTO itemDTO = itemDAO.getItemDetails(itemID);
        try{
            txtItemName.setText(itemDTO.getItemName());
            txtItemQuantity.setText(String.valueOf(itemDTO.getItemQuantity()));
            txtUnitCost.setText(String.valueOf(itemDTO.getUnitCost()));
            txtUnitSellingPrice.setText(String.valueOf(itemDTO.getUnitSellingPrice()));
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR,"Invalid Item ID!").show();
            cleanTextField();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        cleanTextField();
    }
    Stage stage = new Stage();
    AnchorPane homeForm;

    public void setValues(String id, String itemName, String itemQut, String itemUnitPrice, String itemUnitCost, Stage stage, AnchorPane homeForm) {
        txtItemID.setText(id);
        txtItemName.setText(itemName);
        txtItemQuantity.setText(String.valueOf(itemQut));
        txtUnitSellingPrice.setText(String.valueOf(itemUnitPrice));
        txtUnitCost.setText(String.valueOf(itemUnitCost));
        this.stage = stage;
        this.homeForm = homeForm;
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtItemName))return false;
        if (!Regex.setTextColor(TextFilds.INT,txtItemQuantity))return false;
        if (!Regex.setTextColor(TextFilds.DOUBLE,txtUnitCost))return false;
        if (!Regex.setTextColor(TextFilds.DOUBLE,txtUnitSellingPrice))return false;
        return true;
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME,txtItemName);
    }

    public void txtQuantityOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INT,txtItemQuantity);
    }

    public void txtUnitSellingPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.DOUBLE,txtUnitSellingPrice);
    }

    public void txtUnitCostOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.DOUBLE,txtUnitCost);
    }
}
