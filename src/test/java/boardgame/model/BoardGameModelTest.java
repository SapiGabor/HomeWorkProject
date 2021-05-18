package boardgame.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    static BoardGameModel model;

    @BeforeAll
    static void init(){
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
    }

    @Test
    void getWhiteHasMoves() {
        assertTrue(model.getWhiteHasMoves());
    }

    @Test
    void getBlackHasMoves() {
        assertTrue(model.getBlackHasMoves());
    }

    @Test
    void isGameOver() {
        assertFalse(model.isGameOver());
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
}