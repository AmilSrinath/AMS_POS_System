package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class ViewItemFormController{
    @FXML
    private TableColumn<?, ?> colItemID;
    @FXML
    private TableColumn<?, ?> colItemName;
    @FXML
    public TableColumn<?, ?> colItemQuantity;
    @FXML
    public TableColumn<?, ?> colUnitPrice;
    @FXML
    public TableColumn<?, ?> colUnitCost;
}
