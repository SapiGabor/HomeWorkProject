package boardgame.jdbi;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * It contains info and operations about the database.
 */

@RegisterBeanMapper(PlayerInfo.class)
public interface ResultsDAO {

    /**
     * Creates the results table.
     */
    @SqlUpdate("CREATE TABLE results(name VARCHAR PRIMARY KEY, winCount INTEGER, loseCount INTEGER)")
    void createTable();

    /**
     * Inserts a player with no results.
     * @param name The name of the player.
     */
    @SqlUpdate("INSERT INTO results VALUES(:name,0,0)")
    void insertPlayer(@Bind("name") String name);

    /**
     * To the current player it raises win number by one.
     * @param name The name of the player.
     */
    @SqlUpdate("UPDATE results SET winCount = winCount + 1 WHERE name = :name")
    void raisePlayerWin(@Bind("name") String name);

    /**
     * To the current player it raises lose number by one.
     * @param name The name of the player.
     */
    @SqlUpdate("UPDATE results SET loseCount = loseCount + 1 WHERE name = :name")
    void raisePlayerLose(@Bind("name") String name);

    /**
     * Checking if the player is in the database already or not.
     * @param name The name of the player.
     * @return exists or not.
     */
    @SqlQuery("SELECT EXISTS (SELECT * FROM results WHERE name = (:name))")
    boolean playerExists(@Bind("name") String name);

    /**
     * Get the first ten players info order by winCount and loseCount
     * @return The first ten players.
     */
    @SqlQuery("SELECT name, winCount, loseCount FROM results ORDER BY winCount DESC, loseCount LIMIT 10")
    PlayerInfo[] getFirstTenPlayers();
}
