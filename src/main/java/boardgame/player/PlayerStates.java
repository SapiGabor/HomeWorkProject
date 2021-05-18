package boardgame.player;

import boardgame.model.BoardGameModel;

public class PlayerStates {
    /**
     * First {@code Player}'s name.
     */
    private static String firstPlayerName;
    /**
     * Second {@code Player}'s name.
     */
    private static String secondPlayerName;
    /**
     * Defines and gives the name to {@code Player}.
     * @param playerNum the enum of the {@code Player}
     * @param name this name is now in use for {@code Player} from now on.
     */
    public static void setPlayerName(int playerNum, String name)
    {
        if(playerNum == 1)
        {
            firstPlayerName = name;
        }
        else
        {
            secondPlayerName = name;
        }
    }
    /**
     * Gets the name of {@code Player}.
     * @return the name of the {@code Player}
     */
    public static String getPlayerName(int playerNum)
    {
        if(playerNum == 1)
        {
            return firstPlayerName;
        }
        else
        {
            return secondPlayerName;
        }
    }
}
