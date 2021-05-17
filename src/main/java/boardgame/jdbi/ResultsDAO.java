package boardgame.jdbi;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(PlayerInfo.class)
public interface ResultsDAO {
    @SqlUpdate("CREATE TABLE results(name VARCHAR PRIMARY KEY, winCount INTEGER, loseCount INTEGER)")
    void createTable();
    @SqlUpdate("INSERT INTO results VALUES(:name,0,0)")
    void insertPlayer(@Bind("name") String name);
    @SqlUpdate("UPDATE results SET winCount = winCount + 1 WHERE name = :name")
    void raisePlayerWin(@Bind("name") String name);
    @SqlUpdate("UPDATE results SET loseCount = loseCount + 1 WHERE name = :name")
    void raisePlayerLose(@Bind("name") String name);
    @SqlQuery("SELECT EXISTS (SELECT * FROM results WHERE name = (:name))")
    boolean playerExists(@Bind("name") String name);
    @SqlQuery("SELECT name, winCount, loseCount FROM results ORDER BY winCount DESC, loseCount LIMIT 10")
    PlayerInfo[] getFirstTenPlayers();
}
