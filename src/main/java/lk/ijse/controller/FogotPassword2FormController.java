package lk.ijse.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FogotPassword2FormController implements Initializable {

    public TextField txtOTP;
    public Label lblCountDown;
    public Button btnBack;
    public AnchorPane FogotPassword2;
    public Button btnConfirm;
    public Text lblError1;
    public Text lblError2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countdownTimer(1);
        btnBack.setDisable(true);
        lblError1.setVisible(false);
        lblError2.setVisible(false);
    }

    private void countdownTimer(int durationInMinutes) {
        long durationInSeconds = TimeUnit.MINUTES.toSeconds(durationInMinutes);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable countdownTask = new Runnable() {
            long remainingSeconds = durationInSeconds;

            @Override
            public void run() {
                if (remainingSeconds > 0) {
                    Platform.runLater(() -> lblCountDown.setText(String.valueOf(remainingSeconds)));
                    remainingSeconds--;
                } else {
                    scheduler.shutdown();
                    btnBack.setDisable(false);
                    btnConfirm.setDisable(true);
                    lblError1.setVisible(true);
                    lblError2.setVisible(true);
                    lblCountDown.setVisible(false);
                }
            }
        };

        scheduler.scheduleAtFixedRate(countdownTask, 0, 1, TimeUnit.SECONDS);
        scheduler.schedule(() -> scheduler.shutdown(), durationInSeconds, TimeUnit.SECONDS);
    }

    public void btnConfirmOnAction(ActionEvent actionEvent) {
        try {
            int txtOtp = Integer.parseInt(txtOTP.getText());
            int otp = FogotPassword1FormController.otp;

            if (txtOtp == otp) {
                System.out.println("Done");
            } else {
                System.out.println("Not Done");
            }
        }catch (Exception e){}
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
            parentContainer.getChildren().remove(FogotPassword2);
        });
        timeline.play();
    }
}
