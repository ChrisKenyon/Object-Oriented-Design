/**
 * Class for running a coin game by allowing some number of strategies to
 * take turns. This class defines two public, static methods,
 * and is not intended to be instantiated.
 */
public class CoinGameController {
    private CoinGameController() {} // prevents instantiation

    /**
     * Runs a strict coin game with the given initial game board (in the external
     * string representation) and the given players represented as {@link
     * CoinGameStrategy}s.
     *
     * @param initialBoard the initial board configuration
     * @param players the players, in order
     * @return the index of the winning player (zero-based)
     * @throws IllegalStateException if the game is over already
     * @throws IllegalArgumentException if no players are provided
     */
    public static int runCoinGame(String initialBoard,
                                  CoinGameStrategy... players) {
        return runCoinGame(new StrictCoinGame(initialBoard), players);
    }

    /**
     * Runs a strict coin game with the given initial game state and the given
     * players represented as {@link CoinGameStrategy}s. Reports which player
     * won the game, by their index into the array {@code players}.
     *
     * @param game the coin game to play
     * @param players the players, in turn order
     * @return the index of the winning player (zero-based)
     * @throws IllegalStateException if the game is over already
     * @throws IllegalArgumentException if no players are provided
     */
    public static int runCoinGame(CoinGame game, CoinGameStrategy... players) {
        //check that players exists and the elements are initialized
        if (players == null || players[0] == null)
        {
            throw new IllegalArgumentException("No players provided");
        } else if (game.isGameOver())
        {
            throw new IllegalStateException("Game is already over!");
        }
        int playerIndex=-1;
        //should always return game.CointCount()%players.length - 1
        while(!game.isGameOver())
        {
            if (playerIndex == players.length-1)
            {
                playerIndex = 0;
            } else {
                playerIndex++;
            }
            players[playerIndex].playOneMove(game);
        }
        return playerIndex;
    }
}