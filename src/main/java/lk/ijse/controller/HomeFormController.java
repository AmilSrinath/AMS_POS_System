package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable{
    public Label lblTime;
    public AnchorPane HomeForm;
    public AnchorPane ControllArea;
    public Button btnDashbord;
    public Button btnPlaceOrder;
    public Button btnOrderHistory;
    public Button btnViewItem;
    public Button btnUsers;
    public Button btnAddItem;
    public Label lblUsername;
    public Button btnLogOut;

    Stage homeFormStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(String.valueOf(LocalDate.now()));
        TimeNow();
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
        navigation("/view/AddItemForm.fxml");
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
        navigation("/view/ViewItemForm.fxml");
    }

    public void OrderHistoryOnAction(ActionEvent actionEvent) throws IOException {
        navigation("/view/ViewOrderHistoryForm.fxml");
    }

    public void navigation(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        AnchorPane anchorPane = loader.load();
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        HomeForm.getScene().getWindow().hide();
    }

    public void DashbordOnAction(ActionEvent actionEvent) throws IOException {
        navigation("/view/DashbordForm.fxml");
    }

    int value = -1;

    public void UserOnAction(ActionEvent actionEvent) {

    }

    public void btnPlaceOrderOnMouseEntered(MouseEvent mouseEvent) {
        btnPlaceOrder.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnPlaceOrderOnMouseExited(MouseEvent mouseEvent) {
        if (value!=4) {
            btnPlaceOrder.setStyle("-fx-background-color: none;");
        }
    }

    public void btnDashbordOnMouseEntered(MouseEvent mouseEvent) {
        btnDashbord.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnDashbordOnMouseExited(MouseEvent mouseEvent) {
        if (value!=0) {
            btnDashbord.setStyle("-fx-background-color: none;");
        }
    }

    public void btnUserOnMouseEntered(MouseEvent mouseEvent) {
        btnUsers.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnUserOnMouseExited(MouseEvent mouseEvent) {
        if (value!=5) {
            btnUsers.setStyle("-fx-background-color: none;");
        }
    }

    public void btnAddItemOnMouseEntered(MouseEvent mouseEvent) {
        btnAddItem.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnAddItemOnMouseExited(MouseEvent mouseEvent) {
        if (value!=1) {
            btnAddItem.setStyle("-fx-background-color: none;");
        }
    }

    public void btnViewItemOnMouseEntered(MouseEvent mouseEvent) {
        btnViewItem.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnViewItemOnMouseExited(MouseEvent mouseEvent) {
        if (value!=2) {
            btnViewItem.setStyle("-fx-background-color: none;");
        }
    }

    public void btnOrderHistoryOnMouseEntered(MouseEvent mouseEvent) {
        btnOrderHistory.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnOrderHistoryOnMouseExited(MouseEvent mouseEvent) {
        if (value!=3) {
            btnOrderHistory.setStyle("-fx-background-color: none;");
        }
    }

    public void btnLogOutOnMouseEntered(MouseEvent mouseEvent) {
        btnLogOut.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnLogOutOnMouseExited(MouseEvent mouseEvent) {
        btnLogOut.setStyle("-fx-background-color: none;");
    }

    public void btnAddItemOnMouseClicked(MouseEvent mouseEvent) {
        btnAddItem.setStyle("-fx-background-color: #5a189a;");
        value=1;
        btnLogOut.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnViewItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
    }

    public void btnViewItemOnMouseClicked(MouseEvent mouseEvent) {
        btnViewItem.setStyle("-fx-background-color: #5a189a;");
        value=2;
        btnLogOut.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
    }

    public void btnOrderHistoryOnMouseClicked(MouseEvent mouseEvent) {
        btnOrderHistory.setStyle("-fx-background-color: #5a189a;");
        value=3;
        btnLogOut.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnViewItem.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
    }

    public void btnPlaceOrderOnMouseClicked(MouseEvent mouseEvent) {
        btnPlaceOrder.setStyle("-fx-background-color: #5a189a;");
        value=4;
        btnLogOut.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnViewItem.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
    }

    public void btnLogOutOnMouseClicked(MouseEvent mouseEvent) {
        btnLogOut.setStyle("-fx-background-color: #5a189a;");

        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnViewItem.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
    }

    public void btnDashbordOnMouseClicked(MouseEvent mouseEvent) {
        btnDashbord.setStyle("-fx-background-color: #5a189a;");
        value=0;
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnViewItem.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnLogOut.setStyle("-fx-background-color: none;");
    }

    public void btnUserOnMouseClicked(MouseEvent mouseEvent) {
        btnUsers.setStyle("-fx-background-color: #5a189a;");
        value=5;
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnViewItem.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnLogOut.setStyle("-fx-background-color: none;");
    }
}
