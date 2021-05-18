package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingDirectionTest {

    @Test
    void getRowChange() {
        assertEquals(1, KingDirection.DOWN.getRowChange());
        assertEquals(1, KingDirection.DOWN_LEFT.getRowChange());
        assertEquals(0, KingDirection.LEFT.getRowChange());
        assertEquals(-1, KingDirection.UP_LEFT.getRowChange());
        assertEquals(-1, KingDirection.UP.getRowChange());
        assertEquals(-1, KingDirection.UP_RIGHT.getRowChange());
        assertEquals(0, KingDirection.RIGHT.getRowChange());
        assertEquals(1, KingDirection.DOWN_RIGHT.getRowChange());
    }

    @Test
    void getColChange() {
        assertEquals(0, KingDirection.DOWN.getColChange());
        assertEquals(-1, KingDirection.DOWN_LEFT.getColChange());
        assertEquals(-1, KingDirection.LEFT.getColChange());
        assertEquals(-1, KingDirection.UP_LEFT.getColChange());
        assertEquals(0, KingDirection.UP.getColChange());
        assertEquals(1, KingDirection.UP_RIGHT.getColChange());
        assertEquals(1, KingDirection.RIGHT.getColChange());
        assertEquals(1, KingDirection.DOWN_RIGHT.getColChange());
    }

    @Test
    void of() {
        assertEquals(KingDirection.DOWN, KingDirection.of(1, 0));
        assertEquals(KingDirection.DOWN_LEFT, KingDirection.of(1, -1));
        assertEquals(KingDirection.LEFT, KingDirection.of(0, -1));
        assertEquals(KingDirection.UP_LEFT, KingDirection.of(-1, -1));
        assertEquals(KingDirection.UP, KingDirection.of(-1, 0));
        assertEquals(KingDirection.UP_RIGHT, KingDirection.of(-1, 1));
        assertEquals(KingDirection.RIGHT, KingDirection.of(0, 1));
        assertEquals(KingDirection.DOWN_RIGHT, KingDirection.of(1, 1));
        assertThrows(IllegalArgumentException.class, ()-> {
            KingDirection.of(-2, -2);
        });
    }
}