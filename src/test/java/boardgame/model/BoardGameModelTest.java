package boardgame.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    BoardGameModel model;

    @BeforeEach
    void init(){
        model = new BoardGameModel();
    }

    @Test
    void getPieceCount() {
        assertEquals(2, model.getPieceCount());
    }

    @Test
    void getPieceType() {
        assertEquals(PieceType.WHITE, model.getPieceType(0));
        assertEquals(PieceType.BLACK, model.getPieceType(1));
    }

    @Test
    void getPiecePosition() {
        assertEquals(new Position(2,0), model.getPiecePosition(0));
        assertEquals(new Position(3,7), model.getPiecePosition(1));
    }

    @Test
    void positionProperty() {
        assertEquals(new Position(2,0), model.positionProperty(0).get());
        assertEquals(new Position(3,7), model.positionProperty(1).get());
    }

    @Test
    void isValidMove() {
        assertTrue(model.isValidMove(0, KingDirection.DOWN));
        assertTrue(model.isValidMove(1, KingDirection.DOWN));
        assertFalse(model.isValidMove(0, KingDirection.LEFT));
        assertThrows(IllegalArgumentException.class, () -> {model.isValidMove(-1, KingDirection.DOWN);});
        model.removeSquare(new Position(1, 0));
        assertFalse(model.isValidMove(0, KingDirection.UP));
    }

    @Test
    void isOnBoard() {
        assertTrue(model.isOnBoard(new Position(0,0)));
        assertFalse(model.isOnBoard(new Position(-1,-1)));
    }

    @Test
    void getPiecePositions() {
        assertEquals(new Position(2,0), model.getPiecePositions().get(0));
    }

    @Test
    void move() {
        assertEquals(new Position(2,0 ), model.getPiecePosition(0));
        model.move(0, KingDirection.DOWN);
        assertEquals(new Position(3,0 ), model.getPiecePosition(0));
    }

    @Test
    void removeSquare() {
        assertFalse(model.isRemoved(new Position(0, 0)));
        model.removeSquare(new Position(0, 0));
        assertTrue(model.isRemoved(new Position(0, 0)));
    }

    @Test
    void getRemovableSquares() {
        assertTrue(model.getRemovableSquares().contains(new Position(0,0)));
        model.removeSquare(new Position(0, 0));
        assertFalse(model.getRemovableSquares().contains(new Position(0,0)));
    }

    @Test
    void isRemoved() {
        assertFalse(model.isRemoved(new Position(0, 0)));
        model.removeSquare(new Position(0, 0));
        assertTrue(model.isRemoved(new Position(0, 0)));
    }

    @Test
    void getAllPiecePositions() {
        assertEquals(2, model.getAllPiecePositions().size());
    }

    @Test
    void getPieceNumber() {
        assertEquals(1, model.getPieceNumber(new Position(3, 7)).getAsInt());
        assertEquals(OptionalInt.empty(), model.getPieceNumber(new Position(0, 0)));
    }

    @Test
    void getWhiteHasMoves() {
        assertTrue(model.getWhiteHasMoves());
        model.removeSquare(new Position(1, 0));
        model.removeSquare(new Position(1, 1));
        model.removeSquare(new Position(2,1));
        model.removeSquare(new Position(3,1));
        model.removeSquare(new Position(3,0));
        assertFalse(model.getWhiteHasMoves());
    }

    @Test
    void getBlackHasMoves() {
        assertTrue(model.getBlackHasMoves());
        model.removeSquare(new Position(2, 7));
        model.removeSquare(new Position(2, 6));
        model.removeSquare(new Position(3,6));
        model.removeSquare(new Position(4,6));
        model.removeSquare(new Position(4,7));
        assertFalse(model.getBlackHasMoves());
    }

    @Test
    void isGameOver() {
        assertFalse(model.isGameOver());
        model.removeSquare(new Position(2, 7));
        model.removeSquare(new Position(2, 6));
        model.removeSquare(new Position(3,6));
        model.removeSquare(new Position(4,6));
        model.removeSquare(new Position(4,7));
        assertTrue(model.isGameOver());
    }

}