package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void AddItemOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddItemForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void UpdateItemOnAction(ActionEvent actionEvent) {

    }

    public void DeleteItemOnAction(ActionEvent actionEvent) {

    }

    public void PlaceOrderOnAction(ActionEvent actionEvent) {

    }
}

