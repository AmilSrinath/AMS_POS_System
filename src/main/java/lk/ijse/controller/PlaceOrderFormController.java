package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dao.Custom.ItemDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblItemQut;
    public TableColumn<?,?> colItemID;
    public TableColumn<?,?> colItemName;
    public TableColumn<?,?> colQuantity;
    public TableColumn<?,?> colTotal;
    public TableColumn<?,?> colAction;
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    public AnchorPane PlaceOrderForm;
    public JFXTextField txtQuantity;
    public JFXTextField txtItemName;
    Stage MainStage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadItemName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
        this.MainStage = mainStage;
    }

    public void txtItemNameOnAction(ActionEvent actionEvent) throws IOException {
        //Get Item Quantity
        ItemDTO itemDetails = itemDAO.getItemDetailsWithName(txtItemName.getText());
        lblItemQut.setText(String.valueOf(itemDetails.getItemQuantity()));

        txtQuantity.requestFocus();
    }

    private void loadItemName() throws IOException {
        List<String> id = itemDAO.loadItemName();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id) {
            obList.add(un);
        }
        TextFields.bindAutoCompletion(txtItemName, obList);
    }

    public void txtQuantityOnKeyReleased(KeyEvent keyEvent) {
        try{
            int qut = Integer.parseInt(txtQuantity.getText());
            int lblQut = Integer.parseInt(lblItemQut.getText());
            if (qut > lblQut | qut <= 0) {
                txtQuantity.setStyle("-fx-background-color: red;");
            }else {
                txtQuantity.setStyle("-fx-background-color: none;");
            }
        }catch (NumberFormatException e){}
    }

    public void btnAddOnAction(ActionEvent actionEvent) {

    }
}
