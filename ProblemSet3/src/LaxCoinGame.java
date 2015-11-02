/**
 * Created by Chris on 9/28/2014.
 */
public class LaxCoinGame extends AbstractCoinGame {

  LaxCoinGame(String board) {
    super(board);
  }

  @Override
  protected CoinGame fromString(String board) {
    return new LaxCoinGame(board);
  }

  // Lax Game does not need to override the super move class because
  // it does not add any additional rules
}
