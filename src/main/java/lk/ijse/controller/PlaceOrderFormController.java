package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
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
import lk.ijse.dao.Custom.OrderDAO;
import lk.ijse.dao.Custom.OrderDetailDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.TM.OrderTM;
import lk.ijse.util.FactoryConfiguration;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblItemQut;
    public TableColumn<?, ?> colItemID;
    public TableColumn<?, ?> colItemName;
    public TableColumn<?, ?> colQuantity;
    public TableColumn<?, ?> colTotal;
    public TableColumn<OrderTM, Void> colAction;
    public TableView<OrderTM> tblOrder;
    public TableColumn<?,?> colUnitPrice;
    public Label lblNetTotal;
    public Label lblOrderID;
    public Label lblItemUnitPrice;
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    public AnchorPane PlaceOrderForm;
    public JFXTextField txtQuantity;
    public JFXTextField txtItemName;
    Stage MainStage = new Stage();
    private ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadItemName();
            generateNextOrderId();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


        // Configure table columns
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitSellingPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Set the items for the table view
        tblOrder.setItems(orderTMS);

        setupDeleteButtonColumn();

        orderTMS.addListener((ListChangeListener<OrderTM>) change -> {
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

    double profit = 0;

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

    public void setStage(Stage mainStage, double profit) {
        this.MainStage = mainStage;
        this.profit = profit;
    }

    public void txtItemNameOnAction(ActionEvent actionEvent) throws IOException {
        String itemName = txtItemName.getText();

        if (itemName.isEmpty()) {
            return;
        }

        ItemDTO itemDetails = itemDAO.getItemDetailsWithName(itemName);
        lblItemUnitPrice.setText(String.valueOf(itemDetails.getUnitSellingPrice()));

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
                OrderTM existingItem = null;

                for (OrderTM orderTM : orderTMS) {
                    if (orderTM.getItemName().equals(txtItemName.getText())) {
                        existingItem = orderTM;
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

                        OrderTM orderTM = new OrderTM(itemDetails.getItemID(), txtItemName.getText(), unitPrice, itemDetails.getUnitCost(),quantity, total);
                        orderTMS.add(orderTM);
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
        for(OrderTM item: orderTMS){
            netTotal+=item.getTotal();
        }
        System.out.println(netTotal);
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    private double calculateTotalNetAmount() {
        double netTotal = 0.0;
        for (OrderTM orderTM : orderTMS) {
            netTotal += orderTM.getTotal();
        }
        return netTotal;
    }

    private void setupDeleteButtonColumn() {
        colAction.setCellFactory(param -> new TableCell<OrderTM, Void>() {
            private final Button deleteButton = new Button();

            {
                Image image = new Image(getClass().getResourceAsStream("/assest/bin.png"));
                ImageView imageView = new ImageView(image);

                imageView.setFitWidth(16);
                imageView.setFitHeight(16);

                setAlignment(Pos.CENTER);
                deleteButton.setGraphic(imageView);

                deleteButton.setOnAction(event -> {
                    OrderTM orderTM = getTableView().getItems().get(getIndex());
                    orderTMS.remove(orderTM);

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
        for (OrderTM orderTM : orderTMS) {
            if (orderTM.getItemName().equals(itemName)) {
                totalOrderedQuantity += orderTM.getQuantity();
            }
        }
        return totalOrderedQuantity;
    }

    private void generateNextOrderId() throws SQLException, IOException, ClassNotFoundException {
        String nextId = orderDAO.generateNewID();
        lblOrderID.setText(nextId);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        String time = dtf.format(localTime);

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Order order = new Order();
        order.setOrderID(orderDAO.generateNewID());
        order.setDate(String.valueOf(LocalDate.now()));
        order.setTime(time);

        String id = orderDetailDAO.generateNewID();

        for (OrderTM orderTM : orderTMS) {
            String[] strings = id.split("OD-");
            int newID = Integer.parseInt(strings[1]) + 1;
            id = "OD-"+newID;

            OrderDetail orderDetail = new OrderDetail();
            Item item = new Item();

            item.setItemID(orderTM.getItemID());

            orderDetail.setOrderDetailID(id);
            orderDetail.setItem(item);
            orderDetail.setOrder(order);
            orderDetail.setSubTotal(orderTM.getTotal());
            orderDetail.setUnitPrice(orderTM.getUnitSellingPrice());
            orderDetail.setQuantity(orderTM.getQuantity());
            orderDetail.setUnitCost(orderTM.getUnitCost() * orderTM.getQuantity());

            itemDAO.updateQuantityWithItemID(orderTM.getItemID(),orderTM.getQuantity());

            order.addOrderDetail(orderDetail);
            session.detach(item);
            profit = profit + (orderTM.getUnitCost() * orderDetail.getQuantity());
        }

        order.setNetTotal(Double.parseDouble(lblNetTotal.getText()));
        session.save(order);
        transaction.commit();
        session.close();
        generateNextOrderId();
        tblOrder.getItems().clear();
        lblNetTotal.setText("0");
        lblItemQut.setText("0");
    }
}
