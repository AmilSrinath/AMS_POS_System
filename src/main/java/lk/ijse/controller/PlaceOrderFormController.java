package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    public AnchorPane PlaceOrderForm;
    public JFXTextField txtItemID;
    public JFXTextField txtQuantity;
    Stage MainStage = new Stage();

    public void btnBackOnMouceClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        HomeFormController controller = loader.getController();
        controller.setStage(MainStage);
        MainStage.setScene(scene);
        MainStage.setResizable(false);
        MainStage.show();
    }

    public void setStage(Stage mainStage) {
        this.MainStage=mainStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] s = {"I001","I002","I003"};
        TextFields.bindAutoCompletion(txtItemID,s);
    }

    public void txtItemIDOnAction(ActionEvent actionEvent) {
        txtQuantity.requestFocus();
    }
}
