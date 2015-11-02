
public class StrictCoinGame extends AbstractCoinGame {

  public StrictCoinGame(String board) {
    super(board);
  }

  @Override
  protected CoinGame fromString(String board) {
    return new StrictCoinGame(board);
  }

  /**
   * Extra PRECONDITION: There are no coins in between
   * getCoinPosition(coinIndex) and newPosition
   *
   * @param coinIndex   which coin to move
   * @param newPosition where to move it to
   */
  @Override
  public void move(int coinIndex, int newPosition) {
    int checkForCoins = getCoinPosition(coinIndex);
    while (checkForCoins > newPosition + 1) {
      if (gameBoard.hasCoinAt(--checkForCoins)) {
        throw new IllegalMoveException("You can't move over another coin");
      }
    }
    super.move(coinIndex, newPosition);
  }
}
