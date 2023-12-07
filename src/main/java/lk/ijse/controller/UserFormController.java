package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.ItemBO;
import lk.ijse.bo.Custom.UserBO;
import lk.ijse.dao.Custom.Impl.UserAddDAOImpl;
import lk.ijse.dao.Custom.Impl.UserDAOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.User;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    public TableColumn<?, ?> colName;
    public TableColumn<User, Void> colStatus;
    @FXML
    private JFXTextField txtUserID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtRePassword;

    @FXML
    private JFXTextField txtDisplayName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private Text lblError;

    @FXML
    private TableView<User> tblUser;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colDisplayName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colContactNumber;
    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    ObservableList<User> observableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
        try {
            getAll();
            setCellValueFactory();
            txtUserID.setText(userDAO.generateNewID());
            setupDeleteButtonColumn();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {

    }

    private void getAll() throws SQLException, IOException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<User> alluser = userBO.getAllUser();

        for (User user : alluser){
            observableList.add(new User(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getDisplayUsername(),user.getEmail(),user.getContactNumber()));
        }
        tblUser.setItems(observableList);
    }

    void setCellValueFactory(){
        colUserID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDisplayName.setCellValueFactory(new PropertyValueFactory<>("displayUsername"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    void btnSaveOnAtion(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (txtPassword.getText().equals(txtRePassword.getText())) {
            userDAO.add(new User(
                    txtUserID.getText(),
                    txtName.getText(),
                    txtUserName.getText(),
                    txtPassword.getText(),
                    txtDisplayName.getText(),
                    txtEmail.getText(),
                    txtContactNumber.getText()
            ));
            Notifications.create()
                    .title("Successfully...!")
                    .text("User Added Successfully...!")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .showConfirm();
        }else {
            Notifications.create()
                    .title("Not Successfully...!")
                    .text("User Added Not Successfully...!")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .showError();

            txtRePassword.setStyle("-fx-border-color: red;");
            lblError.setVisible(true);
        }
        getAll();
        txtUserID.setText(userDAO.generateNewID());
    }

    private void setupDeleteButtonColumn() {
        colStatus.setCellFactory(param -> new TableCell<User, Void>() {
            private final Button deleteButton = new Button();

            {
                javafx.scene.image.Image image = new Image(getClass().getResourceAsStream("/assest/bin.png"));
                ImageView imageView = new ImageView(image);

                imageView.setFitWidth(16);
                imageView.setFitHeight(16);

                setAlignment(Pos.CENTER);

                deleteButton.setGraphic(imageView);

                deleteButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    try {
                        removeUser(user);
                        getAll();
                        txtUserID.setText(userDAO.generateNewID());
                    } catch (SQLException | IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void removeUser(User user) throws SQLException, IOException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (userDAO.delete(user.getId())) {
                observableList.remove(user);
            }else {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtRePasswordOnKeyReleased(KeyEvent event) {

    }
}
