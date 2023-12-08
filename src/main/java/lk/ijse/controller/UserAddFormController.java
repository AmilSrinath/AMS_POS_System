package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.dao.Custom.Impl.SettingDAOImpl;
import lk.ijse.dao.Custom.Impl.UserAddDAOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.entity.Setting;
import lk.ijse.entity.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserAddFormController implements Initializable {
    public AnchorPane root;
    public JFXPasswordField txtRePassword;
    public Text lblError;
    @FXML
    private JFXTextField txtUserID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtDisplayName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtContactNumber;
    UserAddDAOImpl userDAO = (UserAddDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USERADD);
    SettingDAOImpl settingDAO = (SettingDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SETTING);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtUserID.setText(userDAO.generateNewID());
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        lblError.setVisible(false);
        try {
            settingDAO.add(new Setting("S001","false"));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnSaveOnAtion(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (txtPassword.getText().equals(txtRePassword.getText())) {
            userDAO.add(new User(
                    txtUserID.getText(),
                    txtName.getText(),
                    txtUserName.getText(),
                    txtPassword.getText(),
                    txtDisplayName.getText(),
                    txtEmail.getText(),
                    txtContactNumber.getText()
            ));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
            AnchorPane anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            this.root.getScene().getWindow().hide();
        }else {
            txtRePassword.setStyle("-fx-border-color: red;");
            lblError.setVisible(true);
        }
    }

    public void txtPasswordOnKeyReleased(KeyEvent keyEvent) {
        lblError.setVisible(false);
        txtRePassword.setStyle("-fx-border-color: none;");
    }

    public void txtRePasswordOnKeyReleased(KeyEvent keyEvent) {
        lblError.setVisible(false);
        txtRePassword.setStyle("-fx-border-color: none;");
    }
}
