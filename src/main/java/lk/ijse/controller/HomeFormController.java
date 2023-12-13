package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dao.Custom.Impl.SettingDAOImpl;
import lk.ijse.dao.Custom.Impl.UserDAOImpl;
import lk.ijse.dao.DAOFactory;
import org.controlsfx.control.Notifications;

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
    public Button btnUsers;
    public Button btnAddItem;
    public Label lblUsername;
    public Button btnLogOut;
    public Button btnSetting;
    public ImageView imgDate;
    public ImageView imgTime;
    SettingDAOImpl settingDAO = (SettingDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SETTING);
    static Stage homeFormStage;
    public Label lblDate;
    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    String notify;
    String settingID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(String.valueOf(LocalDate.now()));
        TimeNow();
        setBtnDashbord();
        try {
            navigation("/view/DashbordForm.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayUsername() throws IOException {
        if (!settingDAO.displayUsername(settingID)) {
            lblUsername.setVisible(false);
        }
    }

    public void displayDate() throws IOException {
        if (!settingDAO.displayDate(settingID)) {
            lblDate.setVisible(false);
            imgDate.setVisible(false);
        }
    }

    public void displayTime() throws IOException {
        if (!settingDAO.displayTime(settingID)){
            lblTime.setVisible(false);
            imgTime.setVisible(false);

            lblDate.setLayoutX(1200);
            imgDate.setLayoutX(1164);
        }
    }

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

        AddItemFormController controller = loader.getController();
        controller.setStage(homeFormStage,HomeForm,lblUsername.getText());

        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
    }

    public void PlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlaceOrderForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);

        PlaceOrderFormController controller = loader.getController();
        controller.setStage(homeFormStage,profit,displayUsername);

        homeFormStage.setScene(scene);
        homeFormStage.setResizable(false);
        homeFormStage.centerOnScreen();
        homeFormStage.show();
    }

    double profit = 0;
    String displayUsername;

    public void setStage(Stage homeFormStage, String displayUsername) throws IOException {
        this.homeFormStage = homeFormStage;
        this.displayUsername = displayUsername;
        lblUsername.setText(displayUsername);

        settingID = userDAO.getSettingIDWithDisplayUsername(displayUsername);
        notify = settingDAO.getNotificationSide(settingID);

        displayUsername();
        displayDate();
        displayTime();
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
        new LoginFormController().openMainForm();
        HomeForm.getScene().getWindow().hide();
    }

    public void DashbordOnAction(ActionEvent actionEvent) throws IOException {
        navigation("/view/DashbordForm.fxml");
    }

    int value = -1;

    public void UserOnAction(ActionEvent actionEvent) throws IOException {
        if (userDAO.isAdmin(lblUsername.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserForm.fxml"));
            AnchorPane anchorPane = loader.load();

            ControllArea.getChildren().removeAll();
            ControllArea.getChildren().setAll(anchorPane);

            UserFormController controller = loader.getController();
            controller.setSettingID(settingID);
        }else {
            Notifications.create()
                    .title("Not Access...!")
                    .text("You can't access User Form...!\n Only Admin")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.valueOf(notify))
                    .showError();
        }
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
        btnDashbord.setStyle("-fx-background-color: none;");
        btnSetting.setStyle("-fx-background-color: none;");
    }

    public void btnOrderHistoryOnMouseClicked(MouseEvent mouseEvent) {
        btnOrderHistory.setStyle("-fx-background-color: #5a189a;");
        value=3;
        btnLogOut.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
        btnSetting.setStyle("-fx-background-color: none;");
    }

    public void btnPlaceOrderOnMouseClicked(MouseEvent mouseEvent) {
        btnPlaceOrder.setStyle("-fx-background-color: #5a189a;");
        value=4;
        btnLogOut.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
        btnSetting.setStyle("-fx-background-color: none;");
    }

    public void btnLogOutOnMouseClicked(MouseEvent mouseEvent) {
        btnLogOut.setStyle("-fx-background-color: #5a189a;");

        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
        btnSetting.setStyle("-fx-background-color: none;");
    }

    public void btnDashbordOnMouseClicked(MouseEvent mouseEvent) {
        setBtnDashbord();
    }

    void setBtnDashbord(){
        btnDashbord.setStyle("-fx-background-color: #5a189a;");
        value=0;
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnUsers.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnLogOut.setStyle("-fx-background-color: none;");
        btnSetting.setStyle("-fx-background-color: none;");
    }

    public void btnUserOnMouseClicked(MouseEvent mouseEvent) {
        btnUsers.setStyle("-fx-background-color: #5a189a;");
        value=5;
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnLogOut.setStyle("-fx-background-color: none;");
        btnSetting.setStyle("-fx-background-color: none;");
    }

    public void btnSettingOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SettingForm.fxml"));
        AnchorPane anchorPane = loader.load();

        SettingFormController controller = loader.getController(); // Retrieve the controller
        controller.setDisplayUsername(settingID);

        ControllArea.getChildren().clear(); // Use clear() to remove all children
        ControllArea.getChildren().add(anchorPane);
    }

    public void btnSettingOnMouseEntered(MouseEvent mouseEvent) {
        btnSetting.setStyle("-fx-background-color: #5a189a;");
    }

    public void btnSettingOnMouseExited(MouseEvent mouseEvent) {
        if (value!=6) {
            btnSetting.setStyle("-fx-background-color: none;");
        }
    }

    public void btnSettingOnMouseClicked(MouseEvent mouseEvent) {
        btnSetting.setStyle("-fx-background-color: #5a189a;");
        value=6;
        btnUsers.setStyle("-fx-background-color: none;");
        btnPlaceOrder.setStyle("-fx-background-color: none;");
        btnDashbord.setStyle("-fx-background-color: none;");
        btnOrderHistory.setStyle("-fx-background-color: none;");
        btnAddItem.setStyle("-fx-background-color: none;");
        btnLogOut.setStyle("-fx-background-color: none;");
    }
}
