package edu.neu.ccs.cs3500.pset04;

/**
 * An interface for playing a coin game. The rules of a particular coin game
 * will be implemented by classes that implement this interface.
 */
public interface CoinGame {

  /* ASSUMPTIONS:
    The implementing classes will create players
      and instantiate them in the constructor
    Each player will have a 0 based number that
      can be used to reference whose turn it is
    move() will advance to the next player's
      turn in the circular list so long as a
      legal move is made, thus getPlayerTurn()
      will circle from 0 to playerCount()-1
  */

  /**
   * Gets the number of players
   * Should return greater than 1 else throw exception
   * @return
   */
  int playerCount();

  /**
   * Returns the number of the player whose turn it is
   * @return
   */
  int getPlayerTurn();

  /**
   * Gets the size of the board (the number of squares)
   * @return the board size
   */
  int boardSize();

  /**
   * Gets the number of coins.
   * CLASS INVARIANT: coinCount() will be the same throughout the game
   * @return the number of coins
   */
  int coinCount();

  /**
   * Gets the (zero-based) position of coin number {@code coinIndex}.
   *
   * PRECONDITION: coinIndex < coinCount()
   * @param coinIndex which coin to look up
   * @return the coin's position
   */
  int getCoinPosition(int coinIndex);

  /**
   * Returns whether the current game is over. The game is over if there are
   * no valid moves.
   *
   * @return whether the game is over
   */
  boolean isGameOver();

  /**
   * Moves coin {@code coinIndex} to position {@code newPosition}
   *
   * This method should determine if there is a winner, else advance player turn
   *
   * If the requested move is legal. Throws {@code IllegalMoveException} if
   * the requested move is illegal, which can happen in several ways:
   *
   * <ul>
   *   <li>There is no coin with the requested index.</li>
   *   <li>The new position is occupied by another coin.</li>
   *   <li>There is some other reason the move is illegal,
   *   as specified by the concrete game.</li>
   * </ul>
   * PRECONDITIONS: The move is to the left (newPosition < getCoinPosition(coinIndex)
   *                The game is not over (!isGameOver())
   *                The move is an actual move, (getCoinPosition(coinIndex) != newPosition)
   *                There is no coin in the desired position
   *                newPosition < boardSize()
   *                Plus, any rules set by individual implementations of the game
   *
   * @param coinIndex   which coin to move
   * @param newPosition where to move it to
   * @throws IllegalMoveException the move is illegal
   */
  void move(int coinIndex, int newPosition);

  /**
   * The exception thrown by {@code move} when the requested move is illegal.
   *
   * <p>(Implementation Note: Implementing this interface doesn't require
   * "implementing" the {@code IllegalMoveException} class. Nesting a class
   * within an interface is a way to strongly associate that class with the
   * interface, which makes sense here because the exception is intended to be
   * used specifically by implementations and clients of this interface.)
   */
  static class IllegalMoveException extends IllegalArgumentException {
    /**
     * Constructs a illegal move exception with no description.
     */
    public IllegalMoveException() {
      super();
    }

    /**
     * Constructs a illegal move exception with the given description.
     *
     * @param msg the description
     */
    public IllegalMoveException(String msg) {
      super(msg);
    }
  }
}
