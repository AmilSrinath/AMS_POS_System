package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class LoginFormController extends Application {
    public JFXPasswordField txtPassword;
    public JFXTextField txtUsername;
    public AnchorPane LoginForm;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void btnSigninOnAction(ActionEvent actionEvent) throws IOException {
        boolean username = txtUsername.getText().equalsIgnoreCase("amith");
        boolean password = txtPassword.getText().equalsIgnoreCase("123");

        /*username & password*/
        if (true){
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
            new Alert(Alert.AlertType.ERROR,"Please Check Username or Password").show();
        }
    }
}
