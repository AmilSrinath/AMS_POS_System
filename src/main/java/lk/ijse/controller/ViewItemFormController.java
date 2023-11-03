package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewItemFormController implements Initializable {
    public TableView<Item> tblItem;
    public TableColumn<?, ?> colStatus;
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
    }

    private void getAll() throws SQLException, IOException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<ItemDTO> allitem = itemBO.getAllItems();

        for (ItemDTO itemDTO : allitem){
            observableList.add(new Item(itemDTO.getItemID(), itemDTO.getItemName(), itemDTO.getItemQuantity(), itemDTO.getUnitSellingPrice(), itemDTO.getUnitCost()));
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
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
