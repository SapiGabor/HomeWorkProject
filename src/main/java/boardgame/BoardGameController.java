package boardgame;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.stage.Stage;
import org.tinylog.Logger;

import boardgame.model.BoardGameModel;
import boardgame.model.PawnDirection;
import boardgame.model.Position;

public class BoardGameController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private BoardGameModel model;

    @FXML
    private GridPane board;

    @FXML
    private Button newGameButton;



    @FXML
    private void initialize() {
        model = new BoardGameModel();
        selectionPhase = SelectionPhase.SELECT_FROM;
        newGameButton.setDisable(true);
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
    }

    @FXML
    private void startNewGame(ActionEvent event) throws IOException
    {
        Logger.debug("Új játék kezdete");
        board.getChildren().clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/playersettings.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }


    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }


    private void createPieces() {
        for (int i = 0; i < model.getPieceCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceType(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);
        }
    }



    private Circle createPiece(Color color) {
        var piece = new Circle(30);
        piece.setFill(color);
        piece.setStroke(Color.BLACK);
        piece.setStrokeWidth(1);
        return piece;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        if(model.isGameOver() == false)
        {
            var square = (StackPane) event.getSource();
            var row = GridPane.getRowIndex(square);
            var col = GridPane.getColumnIndex(square);
            var position = new Position(row, col);
            Logger.debug("Click on square {}", position);
            handleClickOnSquare(position);
        }
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected).getAsInt();
                    var direction = PawnDirection.of(position.row() - selected.row(), position.col() - selected.col());
                    Logger.debug("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    deselectSelectedPosition();
                    alterSelectionPhase();
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        if(model.isGameOver() == false)
        {
            setSelectablePositions();
            showSelectablePositions();
        }
        else
        {
            //hideSelectablePositions();
            Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setHeaderText("Játék vége!");
            if(model.getWhiteHasMoves() == false)
            {
                Logger.info("A fekete játékos nyert");
                gameOverAlert.setContentText("A fekete játékos nyert.");
            }
            else
            {
                Logger.info("A fehér játékos nyert");
                gameOverAlert.setContentText("A fehér játékos nyert.");
            }
            gameOverAlert.show();
            newGameButton.setDisable(false);
        }
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.getPiecePositions());
            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected).getAsInt();
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }

    @FXML
    private void resetGame(){
        Logger.debug("Resetting game...");
        board.getChildren().clear();
        initialize();
    }
}
