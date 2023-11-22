package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class PayFormController {
    public Label lblNetTotal;
    public TextField txtAmount;
    public Label lblCash;
    double netTotal = 0;
    public void setNetTotal(String netTotal) {
        this.netTotal= Double.parseDouble(netTotal);
    }

    public void OnKeyPressed(KeyEvent keyEvent) {

    }

    public void txtAmountOnAction(ActionEvent actionEvent) {
        if (!txtAmount.getStyle().equals("-fx-background-color: red; -fx-border-color: black; -fx-border-radius: 5px")){
            double cash = Double.parseDouble(txtAmount.getText())-netTotal;
            lblCash.setText(String.valueOf(cash));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cash " + cash);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/Style/Style.css").toExternalForm());
            alert.show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Not Enough Amount!").show();
        }
    }
}
