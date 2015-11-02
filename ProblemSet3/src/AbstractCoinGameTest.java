import org.junit.Test;

import static org.junit.Assert.*;

  /**
   * Defines tests for {@code AbstractCoinGame}s, without knowing or caring
   * which concrete implementation is being tested. We can then subclass
   * this and override the {@code zero} method to specify how to obtain a
   * specific board for the class we would like to test.
   */
public abstract class AbstractCoinGameTest {

    /**
     * Gets the zero duration of the class to test.
     * @return the zero duration
     */
    protected abstract AbstractCoinGame zero();

    private CoinGame fromString(String board){
      return zero().fromString(board);
    }

    @Test
    public void testBoardSize() {
      assertEquals(9, zero().fromString("O-O-O-O-O").boardSize());
    }

    @Test
    public void testBoardSizeZero() {
      assertEquals(0, zero().fromString("").boardSize());
    }

    @Test
    public void testCoinCount() {
      assertEquals(8, fromString("OO---OOO-O-OO").coinCount());
    }

    @Test
    public void testCoinCountZero() {
      assertEquals(0, zero().fromString("----").coinCount());
    }

    @Test
    public void testGameOver() {
      CoinGame game = fromString("O-O-O--O");
      assertFalse(game.isGameOver());
      game.move(1,1);
      game.move(2,3);
      game.move(2,2);
      game.move(3,3);
      assertTrue(game.isGameOver());
    }

    /*
     * The below tests were factored so that they can catch the exceptions
     * And then they can compare the message to the expected form of
     * their exception. This will help if there is a bug because most
     * bugs will raise an IllegalMoveException, although there could be
     * many different reasons for this. They will fail if an exception is not
     * thrown
     */

    @Test
    public void testInvalidCharacters() {
      try {
        fromString("Invalid Characters");
        fail();
      } catch (IllegalArgumentException e) {
        assertEquals("String contains argument other than O or -",e.getMessage());
      }
    }

    @Test
    public void testGetCoinPosition(){
      assertEquals(2,fromString("--O-O--O-O-").getCoinPosition(0));
      assertEquals(4,fromString("--O-O--O-O-").getCoinPosition(1));
      assertEquals(7, fromString("--O-O--O-O-").getCoinPosition(2));
      assertEquals(9,fromString("--O-O--O-O-").getCoinPosition(3));
    }

    @Test
    public void testMoveCoinIndexOutOfBounds() {
      try {
        fromString("--O-O--O-O-").move(4, 0);
        fail();
      } catch (ArrayIndexOutOfBoundsException e) {
        assertEquals("11",e.getMessage());
      }
    }

    @Test
    public void testMoveAfterGameOver() {
      try {
        fromString("OOOO------").move(3, 2);
        fail();
      } catch (CoinGame.IllegalMoveException e) {
        assertEquals("Game Over!",e.getMessage());
      }
    }

    @Test
    public void testMoveOnAnotherCoin() {
      try {
        fromString("--O-O--O-O-").move(1, 2);
        fail();
      } catch (CoinGame.IllegalMoveException e) {
        assertEquals("There is a coin in the desired square", e.getMessage());
      }
    }

    @Test
    public void testMoveRight() {
      try {
        fromString("OO--OO----").move(2,8);
        fail();
      } catch (CoinGame.IllegalMoveException e) {
        assertEquals("Coins cannot be moved to the right", e.getMessage());
      }
    }

    /**
     * Could be implemented here, but because CoinGame does not
     * contain a method to expose the board string, it needs to
     * be implemented for each subclass directly. This abstract
     * declaration ensures it should be tested in all sub classes
     * of this test
     */
    @Test
    public abstract void testBoardAfterMove();

}