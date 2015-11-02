import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class CoinGameControllerTest {

    SimpleCoinGameStrategy[] players3 = new SimpleCoinGameStrategy[3];
    SimpleCoinGameStrategy[] players5 = new SimpleCoinGameStrategy[5];
    SimpleCoinGameStrategy[] players10 = new SimpleCoinGameStrategy[10];
    SimpleCoinGameStrategy[] playersNotInit = new SimpleCoinGameStrategy[5];
    private boolean filled = false;

    //initialize all players once before testing
    @Before
    public void init() {
        if (filled) {
            return;
        }
        //ok to reference the same object, index is only important difference in players
        Arrays.fill(players3, new SimpleCoinGameStrategy());
        Arrays.fill(players5, new SimpleCoinGameStrategy());
        Arrays.fill(players10, new SimpleCoinGameStrategy());
    }


    @Test
    public void testRunCoinGame() {
        String board1 = "O---O---O--O";
        String board2 = "-------OOO---O---O--O";

        assertEquals(2, CoinGameController.runCoinGame(board1, players3));
        assertEquals(0, CoinGameController.runCoinGame(board2, players5));
        assertEquals(5, CoinGameController.runCoinGame(board2, players10));
    }

    @Test
    public void testRunCoinGameOtherOverload() {
        String board = "-------OOOOOOOOOOOOOOOOOOO"; //18
        StrictCoinGame strictCoinGame1 = new StrictCoinGame(board);

        assertEquals(strictCoinGame1.coinCount() % 3 - 1,
                CoinGameController.runCoinGame(strictCoinGame1, players3));
        strictCoinGame1 = new StrictCoinGame(board);
        assertEquals(strictCoinGame1.coinCount() % 5 - 1,
                CoinGameController.runCoinGame(strictCoinGame1, players5));
        strictCoinGame1 = new StrictCoinGame(board);
        assertEquals(strictCoinGame1.coinCount() % 10 - 1,
                CoinGameController.runCoinGame(strictCoinGame1, players10));
    }


    @Test(expected = IllegalStateException.class)
    public void testRunWithGameOver() {
        String board = "OOOOO";

        CoinGameController.runCoinGame(board, players3);
    }

    @Test(expected = IllegalStateException.class)
    public void testRunCoinGameTwice() {
        String board = "-------OOOOOOOOOOOOOOOOOOO"; //18
        StrictCoinGame strictCoinGame1 = new StrictCoinGame(board);

        CoinGameController.runCoinGame(strictCoinGame1, players3);
        CoinGameController.runCoinGame(strictCoinGame1, players5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunWithInvalidInputs() {
        String board = "WRONG-INPUTS--O--O";

        CoinGameController.runCoinGame(board, players3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunWithPlayerNotInitialized() {
        String board = "OOOOO";

        CoinGameController.runCoinGame(board, playersNotInit);
    }


    @Test(expected = IllegalStateException.class)
    public void testPlayOneMoveNoCoin() throws Exception {
        StrictCoinGame game = new StrictCoinGame("------");
        CoinGameController.runCoinGame(game, players3);
    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyBoard() {
        StrictCoinGame game = new StrictCoinGame("");
        CoinGameController.runCoinGame(game, players3);
    }
}