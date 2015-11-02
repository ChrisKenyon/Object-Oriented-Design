import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleCoinGameStrategyTest {

    StrictCoinGame game1 = new StrictCoinGame("---OO-OO-O");
    StrictCoinGame game2 = new StrictCoinGame("OOOOO---");
    StrictCoinGame game3 = new StrictCoinGame("-----");
    SimpleCoinGameStrategy strategy = new SimpleCoinGameStrategy();

    @Test
    public void testPlayOneMove() throws Exception {
        strategy.playOneMove(game1);
    }

    @Test(expected = IllegalStateException.class)
    public void testPlayOneMoveGameOver() throws Exception {
        strategy.playOneMove(game2);
    }

    @Test(expected = IllegalStateException.class)
    public void testPlayOneMoveNoCoin() throws Exception {
        strategy.playOneMove(game3);
    }

}