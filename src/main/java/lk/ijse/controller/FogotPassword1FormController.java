package lk.ijse.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dao.Custom.Impl.UserDAOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.util.JavaMailUtil;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class FogotPassword1FormController implements Initializable {
    public TextField txtUsername;
    public Label lblError;
    public Button btnNext;
    public AnchorPane FogotPassword1;

    public static int otp;
    public static String username;

    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
    }

    public void btnNextOnAction(ActionEvent actionEvent) throws IOException, MessagingException {
        if (userDAO.checkUsername(txtUsername.getText())) {
            username = txtUsername.getText();
            Parent root = FXMLLoader.load(getClass().getResource("/view/FogotPassword2Form.fxml"));
            Scene scene = btnNext.getScene();
            root.translateXProperty().set(scene.getWidth());

            AnchorPane parentContainer = (AnchorPane) scene.getRoot();
            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(FogotPassword1);
            });
            timeline.play();

            otp = new Random().nextInt(9999)+1000;
            System.out.println(otp);
            Task<Void> emailTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    JavaMailUtil.sendMail(userDAO.getEmailWithUsername(txtUsername.getText()), otp);
                    return null;
                }
            };

            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setMaxSize(40, 40);
            progressIndicator.progressProperty().bind(emailTask.progressProperty());

            new Thread(emailTask).start();
        }else {
            lblError.setVisible(true);
        }
    }

    public void txtUsernameOnKeyReleased(KeyEvent keyEvent) {
        lblError.setVisible(false);
    }
}
