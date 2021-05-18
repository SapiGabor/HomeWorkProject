package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    static Piece testWhite;
    static Piece testBlack;

    @BeforeEach
    void init(){
        testWhite = new Piece(PieceType.WHITE,new Position(2,0));
        testBlack = new Piece(PieceType.BLACK,new Position(3,7));
    }

    @Test
    void getType() {
        assertEquals(PieceType.WHITE, testWhite.getType());
        assertEquals(PieceType.BLACK, testBlack.getType());
    }

    @Test
    void getPosition() {
        assertEquals(new Position(2,0), testWhite.getPosition());
        assertEquals(new Position(3,7), testBlack.getPosition());
    }

    @Test
    void moveTo() {
        testWhite.moveTo(KingDirection.DOWN);
        assertEquals(new Position(3, 0), testWhite.getPosition());
        testBlack.moveTo(KingDirection.DOWN);
        assertEquals(new Position(4,7), testBlack.getPosition());
    }

    @Test
    void positionProperty() {
        assertEquals(new Position(2,0), testWhite.positionProperty().get());
        assertEquals(new Position(3,7), testBlack.positionProperty().get());
    }

    @Test
    void testToString() {
        assertEquals("WHITE(2,0)", testWhite.toString());
        assertEquals("BLACK(3,7)", testBlack.toString());
    }
}