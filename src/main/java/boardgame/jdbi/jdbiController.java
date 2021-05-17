package boardgame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;

public class jdbiController {
    private static Jdbi jdbi;
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
    public static ArrayList<PlayerInfo> firstTen()
    {
        new jdbiController();
        PlayerInfo[] players = jdbi.withExtension(ResultsDAO.class,dao -> dao.getFirstTenPlayers());
        int max = 10;
        ArrayList<PlayerInfo> firstTenPlayers = new ArrayList<PlayerInfo>();
        if(players.length < 10)
        {
            max = players.length;
        }
        for (int i = 0; i < max; i++)
        {
            firstTenPlayers.add(players[i]);
        }
        return firstTenPlayers;
    }
}
