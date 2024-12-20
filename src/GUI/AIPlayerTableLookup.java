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

    public AIPlayerTableLookup(Board board) {
        super(board);
    }

    @Override
    public int[] move() {
        // Rule 1: Take winning move
        int[] move = findWinningMove(mySeed);
        if (move != null) return move;

        // Rule 2: Block opponent's winning move
        move = findBlockingMove(oppSeed);
        if (move != null) return move;

        // Rule 3: Create a fork
        move = findFork(mySeed);
        if (move != null) return move;

        // Rule 4: Prevent opponent's fork
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED && !causesOpponentFork(mySeed, row, col)) {
                    return new int[] {row, col};
                }
            }
        }

        // Rule 5: Choose the position with the most winning ways
        move = findBestMove(mySeed);
        if (move != null) return move;

        // Default: Pick any empty cell
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return new int[] {row, col};
                }
            }
        }

        return null; // No valid move (shouldn't happen in a valid game)
    }

    private int[] findWinningMove(Seed seed) {
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    // Simulate move
                    cells[row][col].content = seed;
                    if (hasWon(seed, row, col)) {
                        cells[row][col].content = Seed.NO_SEED; // Undo
                        return new int[] {row, col};
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
                        return new int[] {row, col};
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
                        bestMove = new int[] {row, col};
                    }
                }
            }
        }
        return bestMove;
    }
}
