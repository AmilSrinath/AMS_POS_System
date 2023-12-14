package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dao.Custom.Impl.UserDAOImpl;
import lk.ijse.dao.DAOFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FogotPassword3FormController implements Initializable {

    public Button btnConfirm;
    public Button btnBack;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtRePassword;
    public Text lblError;
    public AnchorPane FogotPassword3;

    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
    }

    public void btnConfirmOnAction(ActionEvent actionEvent) throws IOException {
        if (txtPassword.getText().equals(txtRePassword.getText())) {
            if(userDAO.updateUserWithUsername(FogotPassword1FormController.username,txtPassword.getText())){
                FogotPassword3.setDisable(true);
                FogotPassword3.getScene().getWindow().hide();
            }
        }else {
            txtRePassword.setStyle("-fx-border-color: red;");
            lblError.setVisible(true);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FogotPassword1Form.fxml"));
        Scene scene = btnBack.getScene();
        root.translateXProperty().set(-scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(FogotPassword3);
        });
        timeline.play();
    }

    public void txtPasswordOnKeyReleased(KeyEvent keyEvent) {
        txtRePassword.setStyle("-fx-border-color: none;");
        lblError.setVisible(false);
    }

    public void txtRePasswordOnKeyReleased(KeyEvent keyEvent) {
        txtRePassword.setStyle("-fx-border-color: none;");
        lblError.setVisible(false);
    }
}
