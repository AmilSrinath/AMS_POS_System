package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.dao.Custom.Impl.SettingDAOImpl;
import lk.ijse.dao.Custom.Impl.UserDAOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.util.FactoryConfiguration;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

public class LoginFormController extends Application implements Initializable {
    public JFXPasswordField txtPassword;
    public JFXTextField txtUsername;
    public AnchorPane LoginForm;
    private Stage loadingStage;
    private Stage PrimaryStage;
    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadUserName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        PrimaryStage = stage;  // Initialize PrimaryStage here

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoadingForm.fxml"));
        Scene scene = new Scene(root, 700, 700, Color.TRANSPARENT);
        loadingStage = new Stage();
        root.setStyle("-fx-background-color: transparent;");
        loadingStage.initStyle(StageStyle.TRANSPARENT);
        loadingStage.setScene(scene);
        loadingStage.centerOnScreen();
        showLoadingForm();
    }

    void openMainForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        PrimaryStage = new Stage();

        LoginFormController controller = loader.getController();
        controller.setPrimaryStage(PrimaryStage);

        PrimaryStage.setScene(scene);
        PrimaryStage.setResizable(false);
        PrimaryStage.show();
    }

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void loadUserName() throws IOException {
        List<String> id = userDAO.loadUserName();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id) {
            obList.add(un);
        }
        TextFields.bindAutoCompletion(txtUsername, obList);
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
        String username = txtUsername.getText();
        String passowrd = txtPassword.getText();

        if (userDAO.checkUsername(username)) {
            if (userDAO.checkPassword(username, passowrd)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeForm.fxml"));
                AnchorPane anchorPane = loader.load();
                Scene scene = new Scene(anchorPane);
                Stage stage = new Stage();

                HomeFormController controller = loader.getController();
                controller.setStage(stage, userDAO.getDisplayUserName(username));

                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                LoginForm.getScene().getWindow().hide();
            } else {
                txtPassword.setStyle("-fx-border-color: red;  -fx-border-width: 2px; -fx-border-radius: 10px");
            }
        }else {
            txtUsername.setStyle("-fx-border-color: red;  -fx-border-width: 2px; -fx-border-radius: 10px");
        }
    }

    public static Stage stage = new Stage();;

    public void UsernameOnKeyPressed(KeyEvent keyEvent) {
        txtUsername.setStyle("-fx-border-color: none;");
    }

    public void passwordOnKeyPressed(KeyEvent keyEvent) {
        txtPassword.setStyle("-fx-border-color: none;");
    }

    public void lblFogotPasswordOnMouseClick(MouseEvent mouseEvent) throws IOException {FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FogotPassword1Form.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        try {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();

            /*LoginForm.setDisable(true);*/

            stage.setOnCloseRequest(windowEvent -> {
                LoginForm.setDisable(false);
            });

            stage.show();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Please Close the System and try again...!").show();
        }
    }
}
