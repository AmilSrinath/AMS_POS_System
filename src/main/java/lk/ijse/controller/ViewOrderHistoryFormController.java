package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dao.Custom.Impl.OrderDetailDAOImpl;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.TM.OrderDetailTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ViewOrderHistoryFormController implements Initializable {
    public JFXTextField txtSearch;
    public AnchorPane ViewOrderHistoryForm;
    @FXML
    private TableView<OrderDetailTM> tblItem;

    @FXML
    private TableColumn<?, ?> colorderDetailID;

    @FXML
    private TableColumn<?, ?> colquantity;

    @FXML
    private TableColumn<?, ?> colsubTotal;

    @FXML
    private TableColumn<?, ?> colunitPrice;

    @FXML
    private TableColumn<?, ?> colitemID;

    @FXML
    private TableColumn<?, ?> colorderID;

    ObservableList<OrderDetailTM> observableList;

    private void getAll() throws IOException {
        observableList = FXCollections.observableArrayList();
        List<OrderDetail> allOrderDetails = new OrderDetailDAOImpl().getAllOrderDetails();

        for (OrderDetail orderDetailEntity : allOrderDetails) {
            observableList.add(new OrderDetailTM(
                    orderDetailEntity.getOrderDetailID(),
                    orderDetailEntity.getItem().getItemID(),
                    orderDetailEntity.getOrder().getOrderID(),
                    orderDetailEntity.getQuantity(),
                    orderDetailEntity.getUnitPrice(),
                    orderDetailEntity.getSubTotal()
            ));
        }
        tblItem.setItems(observableList);
    }

    void setCellValueFactory(){
        colitemID.setCellValueFactory(new PropertyValueFactory<>("item"));
        colorderID.setCellValueFactory(new PropertyValueFactory<>("order"));
        colorderDetailID.setCellValueFactory(new PropertyValueFactory<>("orderDetailID"));
        colquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colsubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        colunitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAll();
            setCellValueFactory();
            searchFilter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchFilter(){
        FilteredList<OrderDetailTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearch.setOnKeyReleased(e->{
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super OrderDetailTM>) orderDetailTM->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (orderDetailTM.getOrder().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(orderDetailTM.getItem().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(String.valueOf(orderDetailTM.getSubTotal()).toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(String.valueOf(orderDetailTM.getUnitPrice()).toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(String.valueOf(orderDetailTM.getQuantity()).toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<OrderDetailTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblItem.comparatorProperty());
            tblItem.setItems(buyer);
        });
    }

    public void OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Integer index = tblItem.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String orderID = colorderID.getCellData(index).toString();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderDetailForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        OrderDetailFormController controller = loader.getController();
        controller.setOrderID(orderID);

        stage.initModality(Modality.WINDOW_MODAL); // or Modality.APPLICATION_MODAL
        stage.initOwner(viewItemFormStage); // Set the owner to the HomeForm stage

        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();

        // Disable HomeForm when ViewItemForm is open
        ViewOrderHistoryForm.setDisable(true);

        // Handle close event to enable HomeForm when ViewItemForm is closed
        stage.setOnCloseRequest(windowEvent -> ViewOrderHistoryForm.setDisable(false));

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    Stage viewItemFormStage;
    public void setStage(Stage viewItemFormStage) {
        this.viewItemFormStage = viewItemFormStage;
    }
}
