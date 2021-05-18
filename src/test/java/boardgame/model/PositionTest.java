package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PositionTest {

    Position testPosition;
    @BeforeEach
    void testInit(){
        testPosition = new Position(0,0);
    }
    @Test
    void moveTo() {
        assertEquals(new Position(1,0),testPosition.moveTo(KingDirection.DOWN));
        assertEquals(new Position(1,-1),testPosition.moveTo(KingDirection.DOWN_LEFT));
        assertEquals(new Position(0,-1),testPosition.moveTo(KingDirection.LEFT));
        assertEquals(new Position(-1,-1),testPosition.moveTo(KingDirection.UP_LEFT));
        assertEquals(new Position(-1,0),testPosition.moveTo(KingDirection.UP));
        assertEquals(new Position(-1,1),testPosition.moveTo(KingDirection.UP_RIGHT));
        assertEquals(new Position(0,1),testPosition.moveTo(KingDirection.RIGHT));
        assertEquals(new Position(1,1),testPosition.moveTo(KingDirection.DOWN_RIGHT));
    }

    @Test
    void testToString() {
        assertEquals("(0,0)",testPosition.toString());
        assertEquals("(-1,0)",testPosition.moveTo(KingDirection.UP).toString());
        assertEquals("(0,-1)",testPosition.moveTo(KingDirection.LEFT).toString());
    }
}