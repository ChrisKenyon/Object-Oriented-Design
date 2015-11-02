public abstract class AbstractCoinGame implements CoinGame {

  /**
   * The board structure which has no game rules associated with it It is within this
   * AbstractCoinGame class because it is protected and has no implementation outside of the coin
   * games Optimized to use char array rather than string and allow easy use
   * Class invariants: {@code squares} only contains chars 'O' and '-'
   *                   length of the Board should not change
   *
   */
  protected class Board {

    private char[] squares;
    Board(String boardString) {
      squares = boardString.toCharArray();
    }

    /**
     * Returns a String copy of the char array
     * @return
     */
    @Override
    public String toString() {
      return String.copyValueOf(squares);
    }

    /**
     * Looks to see if there is a coin at the {@code index}
     * @param index
     * @return
     */
    public boolean hasCoinAt(int index) {
      return (squares[index] == 'O');
    }

    /**
     * Optimized by use of char array
     * @return
     */
    public boolean hasAllValidCharacters() {
      boolean valid = true;
      for (char spot : squares) {
        if (!(spot == 'O' || spot == '-')) {
          valid = false;
        }
      }
      return valid;
    }

    public int length() {
      return squares.length;
    }

    /**
     * @return returns number of coins
     */
    public int numberOfCoins() {
      int count = 0;
      for (char spot : squares) {
        if (spot == 'O')
          count++;
      }
      return count;
    }

    /**
     * Char array allows this method to not have to
     * reallocate strings to move a coin in the game
     * @param from position to move coin from
     * @param to position to move coin to
     */
    public void moveCoinFromTo(int from, int to) {
      squares[to] = 'O';
      squares[from] = '-';
    }

  }
  protected Board gameBoard;

  /**
   * PRECONDITION: Only contains characters 'O' and '-'
   * @param board
   */
  protected AbstractCoinGame(String board) {
    gameBoard = new Board(board);
    if (!gameBoard.hasAllValidCharacters()) {
      throw new IllegalArgumentException("String contains argument other than O or -");
    }
  }

  /**
   * Abstract method to create games that will ease testing
   * PRECONDITION: Only contains characters 'O' and '-'
   * @param board
   * @return
   */
  protected abstract CoinGame fromString(String board);

  /**
   * Displays the board as a string
   * Used only for testing so far
   * @return
   */
  @Override
  public String toString(){
    return gameBoard.toString();
  }

  @Override
  public int boardSize() {
    return gameBoard.length();
  }

  @Override
  public int coinCount() {
    return gameBoard.numberOfCoins();
  }

  @Override
  public int getCoinPosition(int coinIndex) throws ArrayIndexOutOfBoundsException {
    int position = -1;
    int coinNum = 0;
    while (coinNum - 1 != coinIndex) {
      position++;
      if (gameBoard.hasCoinAt(position)) {
        coinNum++;
      }
    }
    return position;
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < coinCount(); ++i) {
      if (getCoinPosition(i) != i) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    if (isGameOver()){
      throw new IllegalMoveException("Game Over!");
    }
    if (getCoinPosition(coinIndex)< newPosition) {
      throw new IllegalMoveException("Coins cannot be moved to the right");
    }
    if (getCoinPosition(coinIndex) == newPosition) {
      throw new IllegalMoveException("Can't move to the same spot");
    }
    if (gameBoard.hasCoinAt(newPosition)) {
      throw new IllegalMoveException("There is a coin in the desired square");
    }
    gameBoard.moveCoinFromTo(getCoinPosition(coinIndex), newPosition);
  }
}


