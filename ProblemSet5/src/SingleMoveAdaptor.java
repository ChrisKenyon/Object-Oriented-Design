/**
 * Wraps a {@link CoinGame} instance to allow only one move. This allows us
 * to safely hand off a reference to a {@code CoinGame} to some strategy,
 * while ensuring that the strategy can make one move in its turn. Otherwise,
 * a strategy might, once given a {@code CoinGame}, attempt to make multiple
 * moves before returning. If the client attempts making multiple moves via
 * the same {@code SingleMoveAdaptor} instance, method {@link #move(int,
 * int)} throws an {@link IllegalStateException}.
 */
public class SingleMoveAdaptor implements CoinGame {
  // The underlying coin game that we are wrapping:
  private final CoinGame game;
  // Whether the capability to move is still available:
  private boolean mayMove;

  /**
   * Constructors a wrapper for coin game {@code game} that permits only one
   * successful move. All other methods delegate to the game,
   * to allow clients of this object to learn about the state of the game.
   *
   * @param game the game object to protect
   */
  public SingleMoveAdaptor(CoinGame game) {
    this.game = game;
    mayMove = true;
  }

  @Override
  public int boardSize() {
    return game.boardSize();
  }

  @Override
  public int coinCount() {
    return game.coinCount();
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    return game.getCoinPosition(coinIndex);
  }

  @Override
  public boolean isGameOver() {
    return game.isGameOver();
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    if (mayMove) {
      // Note that if the next line throws an exception, we do not lose the
      // capability to move and can try again.
      game.move(coinIndex, newPosition);
      mayMove = false;
    } else {
      throw new IllegalStateException("already moved");
    }
  }

  @Override
  public String toString() {
    return game.toString();
  }
}
