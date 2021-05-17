package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This is a class which stores the information abaout a {@code Piece}
 */

public class Piece {
    /**
     * This is used for to store the actual {@code PieceType} that the Piece represeents.
     */
    private final PieceType type;
    /**
     * This is used for to store what {@code Position} this Piece is at.
     */
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    public PieceType getType() {
        return type;
    }

    public Position getPosition() {
        return position.get();
    }

    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    public String toString() {
        return type.toString() + position.get().toString();
    }

    public static void main(String[] args) {
        Piece piece = new Piece(PieceType.WHITE, new Position(2, 0));
        piece.positionProperty().addListener((observableValue, oldPosition, newPosition) -> {
            System.out.printf("%s -> %s\n", oldPosition.toString(), newPosition.toString());
        });
        System.out.println(piece);
        piece.moveTo(PawnDirection.DOWN_RIGHT);
        System.out.println(piece);
    }
}
