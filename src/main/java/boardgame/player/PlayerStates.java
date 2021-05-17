package boardgame.player;

import boardgame.model.BoardGameModel;

public class PlayerStates {
    private static String firstPlayerName;
    private static String secondPlayerName;

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
