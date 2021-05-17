package boardgame.player;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class PlayerController {
    @FXML
    private TextField firstplayer;
    @FXML
    private TextField secondplayer;
    @FXML
    private void handleContinue(ActionEvent event) throws IOException {
        Logger.debug("Új játék!");
        setPlayerNames();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handlePrevious(ActionEvent event) throws IOException {
        Logger.debug("Főmenü");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    private void setPlayerNames(){
        if(firstplayer.getText() == "")
        {
            PlayerStates.setPlayerName(1,"Player1");
        }
        else
        {
            PlayerStates.setPlayerName(1,firstplayer.getText());
        }
        if(firstplayer.getText() == "")
        {
            PlayerStates.setPlayerName(2,"Player2");
        }
        else
        {
            PlayerStates.setPlayerName(2,secondplayer.getText());
        }
    }
}
