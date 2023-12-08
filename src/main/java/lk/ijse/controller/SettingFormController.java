package lk.ijse.controller;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dao.Custom.Impl.SettingDAOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.entity.Setting;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {
    public JFXToggleButton btnDisplayUsername;
    SettingDAOImpl settingDAO = (SettingDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SETTING);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(settingDAO.displayUsername()){
                btnDisplayUsername.setSelected(true);
            }else {
                btnDisplayUsername.setSelected(false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDisplayUsernameOnAction(ActionEvent actionEvent) {

    }

    public void btnApplyOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        boolean selected = btnDisplayUsername.isSelected();
        String isSelect = String.valueOf(selected);

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to change setting...! \nAnd Restart the system", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            settingDAO.update(new Setting("S001", isSelect));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
            AnchorPane anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            HomeFormController.homeFormStage.setScene(scene);
            HomeFormController.homeFormStage.setResizable(false);
            HomeFormController.homeFormStage.show();
        }
    }
}
