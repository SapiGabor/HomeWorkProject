package boardgame.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class MenuController {
    @FXML
    private void goToLeaderboard(ActionEvent event) throws IOException {
        Logger.debug("Eredmények");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Leaderboard.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void startNewGame(ActionEvent event) throws IOException {
        Logger.debug("Játékos menü");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void quitGame(ActionEvent event){
        Logger.debug("Kilépés");
        Platform.exit();
    }
}
