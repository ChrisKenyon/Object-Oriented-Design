/**
 * Created by Chris on 10/15/2014.
 */
public class SimpleCoinGameStrategy implements CoinGameStrategy {

  @Override
  public void playOneMove(CoinGame game) {
    //gets first coin not all the way to left, moves it, returns
    for (int i = 0; i < game.coinCount(); i++) {
      if (game.getCoinPosition(i) != i) {
        game.move(i, i);
        return;
      }
    }
    throw new IllegalStateException("Game is already over!");
  }
}
