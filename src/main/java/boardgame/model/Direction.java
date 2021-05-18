package boardgame.model;

/**
 * It only has two functions.
 */

public interface Direction {

    /**
     * Gets the row change which happened last.
     * @return row change
     */

    int getRowChange();

    /**
     * Gets the col change which happened last.
     * @return col change
     */

    int getColChange();

}
