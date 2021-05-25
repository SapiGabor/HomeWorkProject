package boardgame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;
/**
 * This class is made for handle JDBI operations and connections.
 */
public class jdbiController {
    /**
     * This field stores the JDBI's state.
     */
    private static Jdbi jdbi;
    /**
     * It creates database, installing plugins.
     * There's a try catch which can log out when the table already exists.
     */
    public jdbiController() {
        jdbi = Jdbi.create("jdbc:h2:file:~/.boardgame-2_26/Results", "as", "");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
        try {
            jdbi.withExtension(ResultsDAO.class, dao -> {
                dao.createTable();
                return true;
            });
        } catch (Exception exception) {
            Logger.debug("A tábla már létezik! (" + exception.getClass() + ")");
        }
    }
    /**
     * Stores a win for the player.
     * If user was already in the database, it will increase {@code winCount} +1.
     * If user wasn't in the database, then it inserts to database and set the {@code winCount} to 1.
     * @param name already given in the database.
     */
    public static void winUpdate(String name)
    {
        new jdbiController();
        boolean existPlayer = jdbi.withExtension(ResultsDAO.class,dao -> dao.playerExists(name));
        if(existPlayer)
        {
            jdbi.withExtension(ResultsDAO.class,dao -> {dao.raisePlayerWin(name); return true;});
        }
        else
        {
            jdbi.withExtension(ResultsDAO.class,dao -> {dao.insertPlayer(name); dao.raisePlayerWin(name);
                return true;});
        }
    }
    /**
     * Stores a lose for the player.
     * If user was already in the database, it will increase {@code loseCount} +1.
     * If user wasn't in the database, then it inserts to database and set the {@code loseCount} to 1.
     * @param name already given in the database.
     */
    public static void loseUpdate(String name)
    {
        new jdbiController();
        boolean existPlayer = jdbi.withExtension(ResultsDAO.class,dao -> dao.playerExists(name));
        if(existPlayer)
        {
            jdbi.withExtension(ResultsDAO.class,dao -> {dao.raisePlayerLose(name); return true;});
        }
        else
        {
            jdbi.withExtension(ResultsDAO.class,dao -> {dao.insertPlayer(name); dao.raisePlayerLose(name);
                return true;});
        }
    }
    /**
     * Gets the first ten players from database.
     * If there are no 10 player names stored, it will return as many as it can.
     * @return {@code ArrayList} stores {@code PlayerInfo}'s.
     */
    public static ArrayList<PlayerResult> firstTen()
    {
        new jdbiController();
        return jdbi.withExtension(ResultsDAO.class,dao -> dao.getFirstTenPlayers());
    }
}