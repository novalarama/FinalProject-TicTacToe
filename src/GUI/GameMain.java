package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphic version with AI.
 */
public class GameMain extends JPanel {
    private static final long serialVersionUID = 1L;

    // Game constants
    public static final String TITLE = "Tic Tac Toe with Minimax AI";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);

    // Game objects
    private Board board;         // The game board
    private State currentState;  // The current state of the game
    private Seed currentPlayer;  // The current player
    private JLabel statusBar;    // For displaying status message
    private AIPlayer aiPlayer;   // The AI player

    /** Constructor to setup the UI and game components */
    public GameMain() {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        // Player's move
                        makeMove(row, col, currentPlayer);

                        // Check if the game is still ongoing, and AI should move
                        if (currentState == State.PLAYING && currentPlayer == aiPlayer.mySeed) {
                            aiMove();
                        }
                    }
                } else {
                    newGame();  // Restart the game
                }
                repaint();  // Refresh the display
            }
        });

        // Setup the status bar (JLabel) for displaying status messages
        statusBar = new JLabel();
        statusBar.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END); // Add status bar
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));

        initGame();  // Initialize the game
        newGame();   // Start a new game
    }

    /** Initialize the game components */
    public void initGame() {
        board = new Board();  // Create the game board
    }

    /** Reset the game-board contents and the current state, ready for a new game */
    public void newGame() {
        board.initGame();
        currentPlayer = Seed.CROSS;   // Player X starts first
        currentState = State.PLAYING; // Ready to play

        // Initialize the AI player
        aiPlayer = new AIPlayerMinimax(board);
        aiPlayer.setSeed(Seed.NOUGHT); // AI plays as 'O'

        updateStatus();  // Display initial status
    }

    /** Make a move for the given player at the given position */
    private void makeMove(int row, int col, Seed seed) {
      board.cells[row][col].content = seed; // Update sel
      currentState = board.stepGame(seed, row, col); // Perbarui status game
  
      // Putar suara berdasarkan kondisi
      if (currentState == State.PLAYING) {
          SoundEffect.EAT_FOOD.play();
      } else if (currentState == State.CROSS_WON) {
          SoundEffect.WIN.play();
      } else if (currentState == State.NOUGHT_WON){
        SoundEffect.LOSE.play();
      }
      else if (currentState == State.DRAW) {
          SoundEffect.EXPLODE.play();
      }
  
      // Ganti pemain
      currentPlayer = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
  
      updateStatus(); // Perbarui status
  }
  

    /** Let the AI player make its move */
    private void aiMove() {
      // Run AI move in a separate thread to avoid blocking the UI
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            // Introduce a delay of 1-2 seconds
            Thread.sleep(1000 + (int) (Math.random() * 1000));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          // Get AI's best move
          int[] aiMove = aiPlayer.move();
          if (aiMove != null) {
            // Make the AI's move on the Event Dispatch Thread
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                makeMove(aiMove[0], aiMove[1], aiPlayer.mySeed);
                // Repaint the game panel to reflect the AI's move
                repaint();
              }
            });
          }
        }
      }).start();
    }

    /** Update the status message on the status bar */
    private void updateStatus() {
      if (currentState == State.PLAYING) {
        statusBar.setText((currentPlayer == Seed.CROSS) ? "Your Turn" : "AI's Turn");
      } else if (currentState == State.DRAW) {
        statusBar.setText("It's a Draw! Click to play again.");
      } else if (currentState == State.CROSS_WON) {
        statusBar.setText("You Won! Click to play again.");
        SoundEffect.WIN.play();
      } else if (currentState == State.NOUGHT_WON) {
        statusBar.setText("AI Won! Click to play again.");
        SoundEffect.LOSE.play();
      }
    }

    /** Custom painting codes on this JPanel */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG); // Set background color
        board.paint(g);  // Draw the game board
    }

    /** The entry "main" method */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GameMain());
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }
}
