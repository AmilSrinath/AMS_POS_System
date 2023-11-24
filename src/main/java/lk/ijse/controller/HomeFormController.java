package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dao.Custom.Impl.HomeDAOImpl;
import lk.ijse.dao.Custom.OrderDAO;
import lk.ijse.dao.DAOFactory;

import javax.management.Notification;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {
    public Label lblTime;
    public AnchorPane HomeForm;
    public Label lblTodayOrderCount;
    public Label lblYesterdayOrderCount;
    public Label lblSellItemTypeCount;
    public Label lblYesterdaySellItemTypeCount;
    public Label lblProfit;
    public Label lblYesterdayProfit;

    Stage homeFormStage; // Separate stage for HomeForm
    Stage placeOrderFormStage = new Stage(); // Separate stage for PlaceOrderForm
    Stage viewItemFormStage = new Stage(); // Separate stage for ViewItemForm

    HomeDAOImpl homeDAO = (HomeDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.HOME);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(String.valueOf(LocalDate.now()));
        TimeNow();
        try {
            setOrderCount();
            setYesterdayOrderCount();
            setSellItemTypeCount();
            setYesterdaySellItemTypeCount();
            setProfit();
            setYesterdayProfit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AnchorPane HomeFom;
    public Label lblDate;

    private void TimeNow(){
        Thread thread = new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!false){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() ->{
                    lblTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    public void AddItemOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddItemForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);

        // Create a new stage for the AddItemForm
        Stage addItemFormStage = new Stage();
        addItemFormStage.initModality(Modality.WINDOW_MODAL); // or Modality.APPLICATION_MODAL
        addItemFormStage.initOwner(homeFormStage); // Set the owner to the HomeForm stage

        addItemFormStage.setScene(scene);
        addItemFormStage.setResizable(false);
        addItemFormStage.centerOnScreen();

        // Disable HomeForm when AddItemForm is open
        HomeForm.setDisable(true);

        // Handle close event to enable HomeForm when AddItemForm is closed
        addItemFormStage.setOnCloseRequest(windowEvent -> HomeForm.setDisable(false));

        addItemFormStage.show();
    }

    public void PlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlaceOrderForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);

        PlaceOrderFormController controller = loader.getController();
        controller.setStage(homeFormStage,profit);

        homeFormStage.setScene(scene);
        homeFormStage.setResizable(false);
        homeFormStage.centerOnScreen();
        homeFormStage.show();
    }

    double profit = 0;

    public void setStage(Stage homeFormStage) {
        this.homeFormStage = homeFormStage;
    }

    public void ViewItemOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewItemForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);

        ViewItemFormController controller = loader.getController();

        // Create a new stage for the ViewItemForm
        Stage viewItemFormStage = new Stage();
        controller.setStage(viewItemFormStage);
        viewItemFormStage.initModality(Modality.WINDOW_MODAL); // or Modality.APPLICATION_MODAL
        viewItemFormStage.initOwner(homeFormStage); // Set the owner to the HomeForm stage

        viewItemFormStage.setScene(scene);
        viewItemFormStage.setResizable(false);
        viewItemFormStage.centerOnScreen();

        // Disable HomeForm when ViewItemForm is open
        HomeForm.setDisable(true);

        // Handle close event to enable HomeForm when ViewItemForm is closed
        viewItemFormStage.setOnCloseRequest(windowEvent -> HomeForm.setDisable(false));

        viewItemFormStage.show();
    }

    public void OrderHistoryOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewOrderHistoryForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);

        // Create a new stage for the ViewItemForm
        Stage viewItemFormStage = new Stage();

        ViewOrderHistoryFormController controller = loader.getController();
        controller.setStage(viewItemFormStage);

        viewItemFormStage.initModality(Modality.WINDOW_MODAL); // or Modality.APPLICATION_MODAL
        viewItemFormStage.initOwner(homeFormStage); // Set the owner to the HomeForm stage

        // Disable HomeForm when ViewItemForm is open
        HomeForm.setDisable(true);

        // Handle close event to enable HomeForm when ViewItemForm is closed
        viewItemFormStage.setOnCloseRequest(windowEvent -> HomeForm.setDisable(false));
        viewItemFormStage.setScene(scene);
        viewItemFormStage.setResizable(false);
        viewItemFormStage.centerOnScreen();

        viewItemFormStage.show();
    }

    public void setOrderCount() throws IOException {
        int orderCount = homeDAO.setOrderCount(lblDate.getText());
        lblTodayOrderCount.setText(String.valueOf(orderCount));
    }

    public void setYesterdayOrderCount() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(lblDate.getText(), formatter);
        LocalDate newDate = date.minusDays(1);
        String result = newDate.format(formatter);

        int yesterdayOrderCount = homeDAO.setYesterdayOrderCount(result);
        lblYesterdayOrderCount.setText(String.valueOf(yesterdayOrderCount));
    }

    public void setSellItemTypeCount() throws IOException {
        int itemTypeCount = homeDAO.setSellItemTypeCount(lblDate.getText());
        lblSellItemTypeCount.setText(String.valueOf(itemTypeCount));
    }

    public void setYesterdaySellItemTypeCount() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(lblDate.getText(), formatter);
        LocalDate newDate = date.minusDays(1);
        String result = newDate.format(formatter);

        int itemTypeCount = homeDAO.setYesterdaySellItemTypeCount(result);
        lblYesterdaySellItemTypeCount.setText(String.valueOf(itemTypeCount));
    }

    public void setProfit() throws IOException {
        try {
            double setProfit = homeDAO.setProfit(lblDate.getText());
            lblProfit.setText(String.valueOf(setProfit));
        }catch (NullPointerException e){
            lblProfit.setText("0");
        }
    }

    public void setYesterdayProfit() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(lblDate.getText(), formatter);
        LocalDate newDate = date.minusDays(1);
        String result = newDate.format(formatter);

        try {
            double setProfit = homeDAO.setYesterdayProfit(result);
            lblYesterdayProfit.setText(String.valueOf(setProfit));
        }catch (NullPointerException e){
            lblYesterdayProfit.setText("0");
        }
    }

    public void btnLogOutOnMouseClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        HomeForm.getScene().getWindow().hide();
    }
}
