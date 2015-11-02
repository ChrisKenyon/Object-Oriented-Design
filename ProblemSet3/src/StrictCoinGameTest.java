import org.junit.Test;

import static org.junit.Assert.*;
public class StrictCoinGameTest extends AbstractCoinGameTest {

  @Override
  protected AbstractCoinGame zero() {
    return new StrictCoinGame("");
  }

  @Override
  public void testBoardAfterMove() {
    StrictCoinGame game = new StrictCoinGame("O-O-O--O");
    game.move(3,6);
    assertEquals("O-O-O-O-",game.toString());
  }

  @Test
  public void testMoveTryToJump() throws CoinGame.IllegalMoveException {
    try {
      zero().fromString("--O-O--O-O-").move(3, 0);
    } catch (CoinGame.IllegalMoveException e) {
      assertEquals("You can't move over another coin",e.getMessage());
    }
  }


}