package lk.ijse;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {
    public Label lblTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(String.valueOf(LocalDate.now()));
        TimeNow();
    }

    public AnchorPane HomeFom;
    public Label lblDate;

    private void TimeNow(){
        Thread thread = new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!false){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() ->{
                    lblTime.setText(timenow);
                });
            }
        });
        thread.start();
    }
}

