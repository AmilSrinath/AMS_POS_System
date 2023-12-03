import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.controller.LoginFormController;

import java.io.IOException;
import java.time.LocalDate;

public class Demo extends Application {
    private static final LocalDate EXPIRY_DATE = LocalDate.of(2023, 12, 5);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            if (!isExpired()) {
                System.out.println("Welcome to the trial version!");
                LoginFormController loginForm = new LoginFormController();
                loginForm.start(primaryStage);
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