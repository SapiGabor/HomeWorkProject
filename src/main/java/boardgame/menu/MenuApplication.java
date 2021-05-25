// CHECKSTYLE:OFF
package boardgame.menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuApplication extends Application {
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage.setTitle("JavaFX projekt");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
