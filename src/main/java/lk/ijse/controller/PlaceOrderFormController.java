package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import jakarta.persistence.criteria.Order;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dao.Custom.ItemDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.OrderItem;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblItemQut;
    public TableColumn<?, ?> colItemID;
    public TableColumn<?, ?> colItemName;
    public TableColumn<?, ?> colQuantity;
    public TableColumn<?, ?> colTotal;
    public TableColumn<OrderItem, Void> colAction;
    public TableView<OrderItem> tblOrder;
    public TableColumn<?,?> colUnitPrice;
    public Label lblNetTotal;
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    public AnchorPane PlaceOrderForm;
    public JFXTextField txtQuantity;
    public JFXTextField txtItemName;
    Stage MainStage = new Stage();
    private ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadItemName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Configure table columns
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitSellingPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Set the items for the table view
        tblOrder.setItems(orderItems);

        setupDeleteButtonColumn();

        orderItems.addListener((ListChangeListener<OrderItem>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    // Handle item removal here
                    try {
                        updateLblItemQut();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void updateLblItemQut() throws IOException {
        String itemName = txtItemName.getText(); // Get the item name from the text field
        int totalOrderedQuantity = calculateTotalOrderedQuantity(itemName); // Pass the item name as a parameter
        ItemDTO itemDetails = itemDAO.getItemDetailsWithName(itemName);

        if (itemDetails != null) {
            int remainingQuantity = itemDetails.getItemQuantity() - totalOrderedQuantity;
            lblItemQut.setText(String.valueOf(remainingQuantity));
        } else {
            lblItemQut.setText("0");
        }
    }

    public void btnBackOnMouceClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        HomeFormController controller = loader.getController();
        controller.setStage(MainStage);
        MainStage.setScene(scene);
        MainStage.setResizable(false);
        MainStage.show();
    }

    public void setStage(Stage mainStage) {
        this.MainStage = mainStage;
    }

    public void txtItemNameOnAction(ActionEvent actionEvent) throws IOException {
        String itemName = txtItemName.getText();

        if (itemName.isEmpty()) {
            return;
        }

        ItemDTO itemDetails = itemDAO.getItemDetailsWithName(itemName);

        if (itemDetails != null) {
            int totalOrderedQuantity = calculateTotalOrderedQuantity(itemName);
            int remainingQuantity = itemDetails.getItemQuantity() - totalOrderedQuantity;
            lblItemQut.setText(String.valueOf(remainingQuantity));
        } else {
            lblItemQut.setText("0");
        }

        txtQuantity.setStyle("-fx-background-color: none;");
        txtQuantity.setText("");
        txtQuantity.requestFocus();
    }

    private void loadItemName() throws IOException {
        List<String> id = itemDAO.loadItemName();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id) {
            obList.add(un);
        }
        TextFields.bindAutoCompletion(txtItemName, obList);
    }

    public void txtQuantityOnKeyReleased(KeyEvent keyEvent) {
        try {
            int qut = Integer.parseInt(txtQuantity.getText());
            int lblQut = Integer.parseInt(lblItemQut.getText());
            if (qut > lblQut | qut <= 0) {
                txtQuantity.setStyle("-fx-background-color: red;");
            } else {
                txtQuantity.setStyle("-fx-background-color: none;");
            }
        } catch (NumberFormatException e) {
        }
    }

    public void addItem() throws IOException {
        ItemDTO itemDetails = itemDAO.getItemDetailsWithName(txtItemName.getText());

        try {
            int quantity = Integer.parseInt(txtQuantity.getText());
            double unitPrice = itemDetails.getUnitSellingPrice();
            double total = quantity * unitPrice;

            if (quantity > 0 && quantity <= itemDetails.getItemQuantity()) {
                OrderItem existingItem = null;

                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getItemName().equals(txtItemName.getText())) {
                        existingItem = orderItem;
                        break;
                    }
                }

                if (existingItem != null) {
                    int totalOrderedQuantity = calculateTotalOrderedQuantity(txtItemName.getText());

                    // Calculate the remaining quantity only for the existing item
                    int remainingQuantity = itemDetails.getItemQuantity() - totalOrderedQuantity + existingItem.getQuantity() - quantity;
                    lblItemQut.setText(String.valueOf(remainingQuantity));

                    int newQuantity = existingItem.getQuantity() + quantity;
                    double newTotal = newQuantity * unitPrice;

                    existingItem.setQuantity(newQuantity);
                    existingItem.setTotal(newTotal);

                    tblOrder.refresh();
                    setNetTotal();
                } else {
                    int totalOrderedQuantity = calculateTotalOrderedQuantity(txtItemName.getText());

                    if (totalOrderedQuantity + quantity <= itemDetails.getItemQuantity()) {
                        // Calculate the remaining quantity only for the new item
                        int remainingQuantity = itemDetails.getItemQuantity() - (totalOrderedQuantity + quantity);
                        lblItemQut.setText(String.valueOf(remainingQuantity));

                        OrderItem orderItem = new OrderItem(itemDetails.getItemID(), txtItemName.getText(), unitPrice, quantity, total);
                        orderItems.add(orderItem);
                        setNetTotal();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Please Check Quantity").show();
                    }
                }
            }
        } catch (NumberFormatException e) {}

        lblItemQut.setText("0");
        txtQuantity.clear();
        txtItemName.clear();
        txtItemName.requestFocus();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        addItem();
    }

    public void txtQuantityOnAction(ActionEvent actionEvent) throws IOException {
        addItem();
    }

    public void setNetTotal(){
        double netTotal = 0;
        for(OrderItem item:orderItems){
            netTotal+=item.getTotal();
        }
        System.out.println(netTotal);
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    private double calculateTotalNetAmount() {
        double netTotal = 0.0;
        for (OrderItem orderItem : orderItems) {
            netTotal += orderItem.getTotal();
        }
        return netTotal;
    }

    private void setupDeleteButtonColumn() {
        colAction.setCellFactory(param -> new TableCell<OrderItem, Void>() {
            private final Button deleteButton = new Button();

            {
                Image image = new Image(getClass().getResourceAsStream("/assest/bin.png"));
                ImageView imageView = new ImageView(image);

                imageView.setFitWidth(16);
                imageView.setFitHeight(16);

                setAlignment(Pos.CENTER);
                deleteButton.setGraphic(imageView);

                deleteButton.setOnAction(event -> {
                    OrderItem orderItem = getTableView().getItems().get(getIndex());
                    orderItems.remove(orderItem);

                    double netTotal = calculateTotalNetAmount();
                    lblNetTotal.setText(String.valueOf(netTotal));
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

    private int calculateTotalOrderedQuantity(String itemName) {
        int totalOrderedQuantity = 0;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItemName().equals(itemName)) {
                totalOrderedQuantity += orderItem.getQuantity();
            }
        }
        return totalOrderedQuantity;
    }
}
