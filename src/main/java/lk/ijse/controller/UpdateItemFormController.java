package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.Impl.DataRefreshListener;
import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dao.Custom.ItemDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;

import java.io.IOException;
import java.sql.SQLException;

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
        String itemID = txtItemID.getText();
        String itemName = txtItemName.getText();
        int itemQut = Integer.parseInt(txtItemQuantity.getText());
        double itemUnitSellingPrice = Double.parseDouble(txtUnitSellingPrice.getText());
        double itemUnitCost = Double.parseDouble(txtUnitCost.getText());

        if (!itemBO.updateItem(new ItemDTO(itemID, itemName, itemQut, itemUnitSellingPrice, itemUnitCost))) {
            new Alert(Alert.AlertType.ERROR, "Error!!").show();
        }

        if (dataRefreshListener != null) {
            dataRefreshListener.onRefresh();
        }

        cleanTextField();
        stage.getScene().getWindow().hide();
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
    public void setValues(String id, String itemName, String itemQut, String itemUnitPrice, String itemUnitCost, Stage stage) {
        txtItemID.setText(id);
        txtItemName.setText(itemName);
        txtItemQuantity.setText(String.valueOf(itemQut));
        txtUnitCost.setText(String.valueOf(itemUnitPrice));
        txtUnitSellingPrice.setText(String.valueOf(itemUnitCost));
        this.stage=stage;
    }
}
