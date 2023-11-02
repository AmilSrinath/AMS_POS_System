package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.dao.Custom.ItemDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteItemFormController implements Initializable {
    public Button btnDelete;
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public JFXTextField txtItemName;
    public JFXTextField txtItemID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadItemID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (!itemBO.deleteItem(txtItemID.getText())) {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }
        txtItemID.clear();
        txtItemName.clear();
    }

    public void txtItemIDOnAction(ActionEvent actionEvent) throws IOException {
        String itemID = txtItemID.getText();
        ItemDTO itemDTO = itemDAO.getItemDetails(itemID);
        try{
            txtItemName.setText(itemDTO.getItemName());
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR,"Invalid Item ID!").show();
            txtItemID.clear();
            txtItemName.clear();
        }

        if (txtItemName.getText().equals("")){
            btnDelete.setDisable(true);
        }else {
            btnDelete.setDisable(false);
        }
    }

    private void loadItemID() throws IOException {
        List<String> id = itemDAO.loadItemID();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id) {
            obList.add(un);
        }
        TextFields.bindAutoCompletion(txtItemID,obList);
    }
}
