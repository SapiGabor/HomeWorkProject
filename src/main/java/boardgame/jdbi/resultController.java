package boardgame.jdbi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class resultController {
    @FXML
    TextFlow resultsID;
    @FXML
    private void  initialize (){
        boardgame.jdbi.jdbiController resultsController = new boardgame.jdbi.jdbiController();
        ArrayList<PlayerResult> list = resultsController.firstTen();
        int counter = 1;
        for (var player: list){
            resultsID.getChildren().add(new Text(counter+".\tNév: "+ player.getName() +"\tGyőzelem: "+player.getWinCount()+"\tVereség: "+player.getLoseCount()+"\n\n"));
            counter+=1;
        }
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
