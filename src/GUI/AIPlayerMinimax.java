package GUI;

import java.util.ArrayList;
import java.util.List;

/** AIPlayer using Minimax algorithm */
public class AIPlayerMinimax extends AIPlayer {

    /** Constructor with the given game board */
    public AIPlayerMinimax(Board board) {
        super(board);
    }

    /** Get next best move for computer. Return int[2] of {row, col} */
    @Override
    public int[] move() {
        int[] result = minimax(2, mySeed); // depth 2, maximizing player's turn
        return new int[] {result[1], result[2]};   // row, col
    }

    /** Recursive minimax algorithm for finding the best move */
    private int[] minimax(int depth, Seed player) {
        List<int[]> nextMoves = generateMoves(); // Generate all valid moves

        // Determine if maximizing or minimizing player
        int bestScore = (player == mySeed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                // Simulate move
                cells[move[0]][move[1]].content = player;
                if (player == mySeed) { // Maximizing player
                    currentScore = minimax(depth - 1, oppSeed)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else { // Minimizing player
                    currentScore = minimax(depth - 1, mySeed)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // Undo move
                cells[move[0]][move[1]].content = Seed.NO_SEED;
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    /** Generate all possible valid moves */
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>();
        if (hasWon(mySeed) || hasWon(oppSeed)) {
            return nextMoves; // Return empty list if game is over
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }

    /** Heuristic evaluation of board state */
    private int evaluate() {
        int score = 0;

        // Evaluate each of the 8 possible winning lines
        score += evaluateLine(0, 0, 0, 1, 0, 2); // Row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2); // Row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2); // Row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0); // Col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1); // Col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2); // Col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2); // Diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0); // Alternate diagonal

        return score;
    }

    /** Evaluate a single line of 3 cells */
    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (cells[row1][col1].content == mySeed) {
            score = 1;
        } else if (cells[row1][col1].content == oppSeed) {
            score = -1;
        }

        // Second cell
        if (cells[row2][col2].content == mySeed) {
            if (score == 1) {
                score = 10;
            } else if (score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (cells[row2][col2].content == oppSeed) {
            if (score == -1) {
                score = -10;
            } else if (score == 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        // Third cell
        if (cells[row3][col3].content == mySeed) {
            if (score > 0) {
                score *= 10;
            } else if (score < 0) {
                return 0;
            } else {
                score = 1;
            }
        } else if (cells[row3][col3].content == oppSeed) {
            if (score < 0) {
                score *= 10;
            } else if (score > 0) {
                return 0;
            } else {
                score = -1;
            }
        }

        return score;
    }

    /** Check if the player has won */
    private boolean hasWon(Seed thePlayer) {
        int pattern = 0b000000000;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == thePlayer) {
                    pattern |= (1 << (row * COLS + col));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) {
                return true;
            }
        }
        return false;
    }

    private final int[] winningPatterns = {
        0b111000000, 0b000111000, 0b000000111, // Rows
        0b100100100, 0b010010010, 0b001001001, // Columns
        0b100010001, 0b001010100               // Diagonals
    };
}
