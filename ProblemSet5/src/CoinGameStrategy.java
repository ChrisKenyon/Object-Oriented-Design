/**
 * An interface for representing coin game strategies. A strategy defines one
 * method, {@link #playOneMove}, which takes a reference to a {@link
 * CoinGame} and plays one move on it, if possible.
 */
@FunctionalInterface
public interface CoinGameStrategy {
  /**
   * Make one move in the given name, according to some strategy.
   * <p>
   * PRECONDITIONS: {@code !game.isGameOver()}
   *
   * @param game the game to make a move in
   */
  void playOneMove(CoinGame game);
}
