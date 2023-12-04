package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class LoginFormController extends Application {
    public JFXPasswordField txtPassword;
    public JFXTextField txtUsername;
    public AnchorPane LoginForm;
    private Stage loadingStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoadingForm.fxml"));
        Scene scene = new Scene(root, 700,700, Color.TRANSPARENT);
        loadingStage = new Stage();
        root.setStyle("-fx-background-color: transparent;");
        loadingStage.initStyle(StageStyle.TRANSPARENT);
        loadingStage.setScene(scene);
        loadingStage.centerOnScreen();
        showLoadingForm();
    }

    private void openMainForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void showLoadingForm() {
        loadingStage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(0));
        pause.setOnFinished(event -> {
            loadingStage.hide();
            try {
                openMainForm();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        pause.play();
    }

    public void btnSigninOnAction(ActionEvent actionEvent) throws IOException {
        boolean username = txtUsername.getText().equals("Amith");
        boolean password = txtPassword.getText().equals("123");

        if (true){//username
            if (true){//password
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeForm.fxml"));
                AnchorPane anchorPane = loader.load();
                Scene scene = new Scene(anchorPane);
                Stage stage = new Stage();
                HomeFormController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                LoginForm.getScene().getWindow().hide();
            }else {
                txtPassword.setStyle("-fx-border-color: red;  -fx-border-width: 2px; -fx-border-radius: 10px");
            }
        }else {
            txtUsername.setStyle("-fx-border-color: red;  -fx-border-width: 2px; -fx-border-radius: 10px");
        }
    }

    public void UsernameOnKeyPressed(KeyEvent keyEvent) {
        txtUsername.setStyle("-fx-border-color: none;");
    }

    public void passwordOnKeyPressed(KeyEvent keyEvent) {
        txtPassword.setStyle("-fx-border-color: none;");
    }
}
