package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UpdateItemFormController {

    public JFXTextField txtItemName;
    public JFXComboBox comItemID;
    @FXML
    private JFXTextField txtItemQuantity;

    @FXML
    private JFXTextField txtUnitSellingPrice;

    @FXML
    private JFXTextField txtUnitCost;

    @FXML
    private JFXComboBox<?> comItemName;

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
