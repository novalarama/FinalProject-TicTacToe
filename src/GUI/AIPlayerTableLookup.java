/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #3
 * 1 - 5026231006 - Noval Akbar Ramadhany
 * 2 - 5026231007 - Nadia Lovely
 * 3 - 5026231090 - Nadia Ayula Assyaputri
 */
package GUI;

public class AIPlayerTableLookup extends AIPlayer {

  public AIPlayerTableLookup(Board board, String difficultyLevel) {
    super(board, difficultyLevel);
  }

  @Override
  public int[] move() {
    if (difficultyLevel.equalsIgnoreCase("easy")) {
      // Only use Rule 5 and default behavior
      return findBestMove(mySeed) != null ? findBestMove(mySeed) : findAnyEmptyCell();
    } else if (difficultyLevel.equalsIgnoreCase("medium")) {
      // Use Rule 1, Rule 2, and Rule 5
      int[] move = findWinningMove(mySeed);
      if (move != null)
        return move;

      move = findBlockingMove(oppSeed);
      if (move != null)
        return move;

      return findBestMove(mySeed) != null ? findBestMove(mySeed) : findAnyEmptyCell();
    } else {
      // Hard uses all rules
      // Use all rules: Rule 1, Rule 2, Rule 3, Rule 4, and Rule 5
      int[] move = findWinningMove(mySeed);
      if (move != null)
        return move;

      move = findBlockingMove(oppSeed);
      if (move != null)
        return move;

      move = findFork(mySeed);
      if (move != null)
        return move;

      move = findFork(oppSeed);
      if (move != null)
        return move;

      return findBestMove(mySeed) != null ? findBestMove(mySeed) : findAnyEmptyCell();
    }
  }

  private int[] findAnyEmptyCell() {
    for (int row = 0; row < Board.ROWS; row++) {
      for (int col = 0; col < Board.COLS; col++) {
        if (cells[row][col].content == Seed.NO_SEED) {
          return new int[] { row, col };
        }
      }
    }
    return null; // No empty cells available
  }

  private int[] findWinningMove(Seed seed) {
    for (int row = 0; row < Board.ROWS; row++) {
      for (int col = 0; col < Board.COLS; col++) {
        if (cells[row][col].content == Seed.NO_SEED) {
          // Simulate move
          cells[row][col].content = seed;
          if (hasWon(seed, row, col)) {
            cells[row][col].content = Seed.NO_SEED; // Undo
            return new int[] { row, col };
          }
          cells[row][col].content = Seed.NO_SEED; // Undo
        }
      }
    }
    return null;
  }

  private boolean hasWon(Seed seed, int row, int col) {
    // Check all win conditions
    return (cells[row][0].content == seed && cells[row][1].content == seed && cells[row][2].content == seed) ||
        (cells[0][col].content == seed && cells[1][col].content == seed && cells[2][col].content == seed) ||
        (row == col && cells[0][0].content == seed && cells[1][1].content == seed && cells[2][2].content == seed) ||
        (row + col == 2 && cells[0][2].content == seed && cells[1][1].content == seed && cells[2][0].content == seed);
  }

  private int[] findBlockingMove(Seed opponentSeed) {
    return findWinningMove(opponentSeed);
  }

  private int[] findFork(Seed seed) {
    for (int row = 0; row < Board.ROWS; row++) {
      for (int col = 0; col < Board.COLS; col++) {
        if (cells[row][col].content == Seed.NO_SEED) {
          // Simulate move
          cells[row][col].content = seed;
          int winningMoves = countWinningMoves(seed);
          cells[row][col].content = Seed.NO_SEED; // Undo

          if (winningMoves > 1) { // Create a fork if more than one winning move
            return new int[] { row, col };
          }
        }
      }
    }
    return null;
  }

  private boolean causesOpponentFork(Seed seed, int row, int col) {
    Seed opponent = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

    // Simulate move
    cells[row][col].content = seed;
    boolean forkPossible = findFork(opponent) != null;
    cells[row][col].content = Seed.NO_SEED; // Undo

    return forkPossible;
  }

  private int countWinningMoves(Seed seed) {
    int count = 0;
    for (int row = 0; row < Board.ROWS; row++) {
      for (int col = 0; col < Board.COLS; col++) {
        if (cells[row][col].content == Seed.NO_SEED) {
          cells[row][col].content = seed;
          if (hasWon(seed, row, col)) {
            count++;
          }
          cells[row][col].content = Seed.NO_SEED;
        }
      }
    }
    return count;
  }

  private int[] findBestMove(Seed seed) {
    int maxWays = -1;
    int[] bestMove = null;

    for (int row = 0; row < Board.ROWS; row++) {
      for (int col = 0; col < Board.COLS; col++) {
        if (cells[row][col].content == Seed.NO_SEED) {
          // Simulate move
          cells[row][col].content = seed;
          int waysToWin = countWinningMoves(seed);
          cells[row][col].content = Seed.NO_SEED; // Undo

          if (waysToWin > maxWays) {
            maxWays = waysToWin;
            bestMove = new int[] { row, col };
          }
        }
      }
    }
    return bestMove;
  }
}
