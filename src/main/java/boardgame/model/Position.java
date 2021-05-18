package boardgame.model;
/**
 * This record is used for to store the row and column number of the Position.
 * It contains a moveTo() and a toString() method too.
 * @param row new {@code Position}'s row.
 * @param col new {@code Position}'s column.
 */
public record Position(int row, int col) {
    /**
     * This moveTo() Moves the current {@code Position} to {@code Direction}.
     * @param direction is the name of {@code Direction} where we want to move.
     * @return gives back the new {@code Position} after the moving.
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }
    /**
     * This function formats a {@code String} from the current {@code Position}.
     * @return the formed {@code String}.
     */
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}