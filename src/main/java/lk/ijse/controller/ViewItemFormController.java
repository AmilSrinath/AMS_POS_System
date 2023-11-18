package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.Impl.DataRefreshListener;
import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ViewItemFormController implements Initializable, DataRefreshListener {
    public TableView<Item> tblItem;
    public TableColumn<Item, Void> colStatus;
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
    ObservableList<Item> observableList;
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    Stage stage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAll();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        setupDeleteButtonColumn();
    }

    private void getAll() throws SQLException, IOException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<ItemDTO> allitem = itemBO.getAllItems();

        for (ItemDTO itemDTO : allitem){
            observableList.add(new Item(itemDTO.getItemID(), itemDTO.getItemName(), itemDTO.getItemQuantity(), itemDTO.getUnitSellingPrice(), itemDTO.getUnitCost(), new HashSet<OrderDetail>()));
        }
        tblItem.setItems(observableList);
    }

    void setCellValueFactory(){
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitSellingPrice"));
        colUnitCost.setCellValueFactory(new PropertyValueFactory<>("unitCost"));
    }

    public void refreshTable() {
        try {
            getAll();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    String id = "";

    public void rowOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Integer index = tblItem.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        id = colItemID.getCellData(index).toString();
        String itemName = colItemName.getCellData(index).toString();
        String itemQut = colItemQuantity.getCellData(index).toString();
        String itemUnitPrice = colUnitPrice.getCellData(index).toString();
        String itemUnitCost = colUnitCost.getCellData(index).toString();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateItemForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);

        UpdateItemFormController controller = loader.getController();
        controller.setValues(id,itemName,itemQut,itemUnitPrice,itemUnitCost,stage);
        controller.setDataRefreshListener(this);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    private void removeItem(Item item) throws SQLException, IOException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {

            if (itemBO.deleteItem(item.getItemID())) {
                observableList.remove(item);
            }else {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }
    }

    private void setupDeleteButtonColumn() {
        colStatus.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button deleteButton = new Button();

            {
                Image image = new Image(getClass().getResourceAsStream("/assest/bin.png"));
                ImageView imageView = new ImageView(image);

                imageView.setFitWidth(16);
                imageView.setFitHeight(16);

                setAlignment(Pos.CENTER);

                deleteButton.setGraphic(imageView);

                deleteButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    try {
                        removeItem(item);
                    } catch (SQLException | IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        try {
            getAll();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
