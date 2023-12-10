package lk.ijse.controller;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dao.Custom.Impl.SettingDAOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.entity.Setting;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {
    public JFXToggleButton btnDisplayUsername;
    public JFXToggleButton btnDisplayDate;
    public JFXToggleButton btnDisplayTime;
    public RadioButton btnTop_Right,btnBottom_Right,btnBottom_Left,btnTop_Left;
    SettingDAOImpl settingDAO = (SettingDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SETTING);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(settingDAO.displayUsername() ){
                btnDisplayUsername.setSelected(true);
            }else {
                btnDisplayUsername.setSelected(false);
            }

            if(settingDAO.displayDate() ){
                btnDisplayDate.setSelected(true);
            }else {
                btnDisplayDate.setSelected(false);
            }

            if(settingDAO.displayTime() ){
                btnDisplayTime.setSelected(true);
            }else {
                btnDisplayTime.setSelected(false);
            }
            setNotificationButton();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ToggleGroup toggleGroup = new ToggleGroup();
        btnTop_Right.setToggleGroup(toggleGroup);
        btnTop_Left.setToggleGroup(toggleGroup);
        btnBottom_Left.setToggleGroup(toggleGroup);
        btnBottom_Right.setToggleGroup(toggleGroup);
    }

    public void setNotificationButton() throws IOException {
        String nofity = settingDAO.getNotificationSide();
        if (nofity.equals("TOP_RIGHT")){
            btnTop_Right.setSelected(true);
        } else if (nofity.equals("BOTTOM_RIGHT")) {
            btnBottom_Right.setSelected(true);
        } else if (nofity.equals("BOTTOM_LEFT")) {
            btnBottom_Left.setSelected(true);
        }else {
            btnTop_Left.setSelected(true);
        }
    }

    public void btnApplyOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        String isSelectDisplayUsername = String.valueOf(btnDisplayUsername.isSelected());
        String isDisplayDate = String.valueOf(btnDisplayDate.isSelected());
        String isDisplayTime = String.valueOf(btnDisplayTime.isSelected());

        String notification;
        if (btnTop_Right.isSelected()){
            notification = "TOP_RIGHT";
        } else if (btnBottom_Right.isSelected()) {
            notification = "BOTTOM_RIGHT";
        } else if (btnBottom_Left.isSelected()) {
            notification = "BOTTOM_LEFT";
        }else {
            notification = "TOP_LEFT";
        }

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to change setting...! \nAnd Restart the system", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            settingDAO.update(new Setting("S001", isSelectDisplayUsername,isDisplayDate,isDisplayTime,notification));
            callLoginForm();
        }
    }

    public void btnResetSettingsOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to change setting...! \nAnd Restart the system", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            settingDAO.update(new Setting("S001", "true", "true", "true","TOP_RIGHT"));
            callLoginForm();
        }
    }

    public void callLoginForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        HomeFormController.homeFormStage.setScene(scene);
        HomeFormController.homeFormStage.setResizable(false);
        HomeFormController.homeFormStage.show();
    }

    public void tsetNotification(String side){
        Notifications.create()
                .title("Successfully...!")
                .text("Notification Test..!")
                .hideAfter(Duration.seconds(2))
                .position(Pos.valueOf(side))
                .showConfirm();
    }

    public void btnTop_RightOnAction(ActionEvent actionEvent) {
        tsetNotification("TOP_RIGHT");
    }

    public void btnBottom_RightOnAction(ActionEvent actionEvent) {
        tsetNotification("BOTTOM_RIGHT");
    }

    public void btnBottom_LeftOnAction(ActionEvent actionEvent) {
        tsetNotification("BOTTOM_LEFT");
    }

    public void btnTop_LeftOnAction(ActionEvent actionEvent) {
        tsetNotification("TOP_LEFT");
    }
}
