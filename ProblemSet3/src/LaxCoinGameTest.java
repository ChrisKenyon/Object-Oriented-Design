import org.junit.Test;

import static org.junit.Assert.*;

public class LaxCoinGameTest extends AbstractCoinGameTest {

  @Override
  protected AbstractCoinGame zero() {
    return new LaxCoinGame("");
  }

  @Override
  public void testBoardAfterMove() {
    LaxCoinGame game = new LaxCoinGame("O-O-O--O");
    game.move(3,6);
    game.move(1,1);
    assertEquals("OO--O-O-",game.toString());
  }

  @Test
  public void testJump() {
    LaxCoinGame game = new LaxCoinGame("O-O-O--O");
    game.move(3,3);
    game.move(3,1);
    assertEquals("OOOO----",game.toString());
    assertTrue(game.isGameOver());
  }
}
