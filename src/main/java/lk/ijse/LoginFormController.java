package lk.ijse;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginFormController extends Application {
    public JFXPasswordField txtPassword;
    public JFXTextField txtUsername;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        /* prefHeight="750.0" prefWidth="1279.0" */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void btnSigninOnAction(ActionEvent actionEvent) throws IOException {
        boolean username = txtUsername.getText().equalsIgnoreCase("amith");
        boolean password = txtPassword.getText().equalsIgnoreCase("123");

        if (username & password){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeForm.fxml"));
            AnchorPane anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Please Check Username or Password");
        }
    }

    public void btnCloseOnMouseClicked(MouseEvent mouseEvent) {
        System.exit(1);
    }
}
