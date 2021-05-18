package boardgame.jdbi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * This class records info about a player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInfo {
    /**
     * Records info of Player's name.
     */
    String name;
    /**
     * Records number of wons of a Player.
     */
    int winCount;
    /**
     * Records number of loses of a Player.
     */
    int loseCount;
}