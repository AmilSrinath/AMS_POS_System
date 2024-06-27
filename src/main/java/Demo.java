import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.controller.LoginFormController;
import lk.ijse.dao.Custom.Impl.SettingDAOImpl;
import lk.ijse.dao.Custom.Impl.UserAddDAOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.entity.Setting;
import lk.ijse.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class Demo extends Application {
    private static final LocalDate EXPIRY_DATE = LocalDate.of(2024, 6, 27);
    UserAddDAOImpl userDAO = (UserAddDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USERADD);
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            if (!isExpired()) {
                Session session;
                try {
                    session = FactoryConfiguration.getInstance().getSession();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Transaction transaction = session.beginTransaction();
                transaction.commit();
                session.close();

                //--------------------------------------------------------------

                boolean isEmpty = userDAO.isEmpty();
                if (isEmpty){
                    FXMLLoader loader = new FXMLLoader(Demo.class.getResource("/view/UserAddForm.fxml"));
                    AnchorPane anchorPane = loader.load();
                    Scene scene = new Scene(anchorPane);
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.centerOnScreen();
                    primaryStage.show();

                }else {
                    LoginFormController loginForm = new LoginFormController();
                    loginForm.start(primaryStage);
                }

                //--------------------------------------------------------------

            } else {
                FXMLLoader loader = new FXMLLoader(Demo.class.getResource("/view/TraialFrom.fxml"));
                AnchorPane anchorPane = loader.load();
                Scene scene = new Scene(anchorPane);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.centerOnScreen();
                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isExpired() {
        return LocalDate.now().isAfter(EXPIRY_DATE);
    }
}