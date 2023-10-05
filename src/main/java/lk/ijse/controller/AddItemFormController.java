package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnAddOnAction(ActionEvent actionEvent) {

    }
}
