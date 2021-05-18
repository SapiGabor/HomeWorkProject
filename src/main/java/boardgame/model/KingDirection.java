package boardgame.model;
/**
 * This enum contains which {@code Direction}'s can we choose from.
 */
public enum KingDirection implements Direction {

    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    RIGHT(0, 1),
    DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DOWN_LEFT(1, -1),
    LEFT(0, -1);
    /**
     * Records the row changes.
     */
    private final int rowChange;
    /**
     * Records the column changes.
     */
    private final int colChange;
    /**
     * Default constructor of {@code KingDirection}.
     * @param rowChange present row change.
     * @param colChange present column change.
     */
    KingDirection(int rowChange, int colChange) {


        this.rowChange = rowChange;

        this.colChange = colChange;
    }
    /**
     * Get the present row change of {@code KingDirection}.
     * @return row change.
     */
    public int getRowChange() {
        return rowChange;
    }
    /**
     * Get the present column change of {@code KingDirection}.
     * @return column change (integer).
     */
    public int getColChange() {
        return colChange;
    }
    /**
     * Determines which enum can be used with the given changes.
     * @param rowChange change of the row.
     * @param colChange change of the cols.
     */
    public static KingDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        System.out.println(of(1, -1));
    }

}
