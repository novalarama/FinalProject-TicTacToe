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

public abstract class AIPlayer {
  protected int ROWS = 3;
  protected int COLS = 3;

  protected Cell[][] cells;
  protected Seed mySeed;
  protected Seed oppSeed;

  public AIPlayer(Board board) {
    cells = board.cells;
  }

  public void setSeed(Seed seed) {
    this.mySeed = seed;
    oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
  }

  private int evaluateLine(Cell c1, Cell c2, Cell c3, Seed seed) {
    int score = 0;

    int playerCount = 0, opponentCount = 0;
    Seed opponent = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

    if (c1.content == seed)
      playerCount++;
    if (c2.content == seed)
      playerCount++;
    if (c3.content == seed)
      playerCount++;
    if (c1.content == opponent)
      opponentCount++;
    if (c2.content == opponent)
      opponentCount++;
    if (c3.content == opponent)
      opponentCount++;

    // Scoring logic
    if (playerCount > 0 && opponentCount == 0) {
      if (playerCount == 3)
        score += 100;
      else if (playerCount == 2)
        score += 10;
      else
        score += 1;
    } else if (opponentCount > 0 && playerCount == 0) {
      if (opponentCount == 3)
        score -= 100;
      else if (opponentCount == 2)
        score -= 10;
      else
        score -= 1;
    }

    return score;
  }

  protected int evaluateBoard(Seed seed) {
    int score = 0;

    // Evaluate rows
    for (int row = 0; row < Board.ROWS; row++) {
      score += evaluateLine(cells[row][0], cells[row][1], cells[row][2], seed);
    }

    // Evaluate columns
    for (int col = 0; col < Board.COLS; col++) {
      score += evaluateLine(cells[0][col], cells[1][col], cells[2][col], seed);
    }

    // Evaluate diagonals
    score += evaluateLine(cells[0][0], cells[1][1], cells[2][2], seed);
    score += evaluateLine(cells[0][2], cells[1][1], cells[2][0], seed);

    return score;
  }

  abstract int[] move(); // to be implemented by subclasses
}