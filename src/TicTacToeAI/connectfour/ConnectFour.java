package TicTacToeAI.connectfour;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class ConnectFour extends JPanel {
  private static final long serialVersionUID = 1L; // to prevent serializable warning

  // Define named constants for the drawing graphics
  public static final String TITLE = "Tic Tac Toe";
  public static final Color COLOR_BG = Color.WHITE;
  public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
  public static final Color COLOR_CROSS = new Color(239, 105, 80); // Red #EF6950
  public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1
  public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

  // Define game objects
  private Board board; // the game board
  private State currentState; // the current state of the game
  private Seed currentPlayer; // the current player
  private JLabel statusBar; // for displaying status message

  /** Constructor to setup the UI and game components */
  public ConnectFour() {

    // This JPanel fires MouseEvent
    super.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) { // mouse-clicked handler
        int mouseX = e.getX();
        int mouseY = e.getY();
        // Get the row and column clicked
        int row = mouseY / Cell.SIZE;
        int col = mouseX / Cell.SIZE;

        if (currentState == State.PLAYING) {
          if (col >= 0 && col < Board.COLS) {
            for (int rowI = Board.ROWS - 1; rowI >= 0; rowI--) {
              if (board.cells[rowI][col].content == Seed.NO_SEED) {
                board.cells[rowI][col].content = currentPlayer; // Make a move
                board.stepGame(currentPlayer, rowI, col);
                // Switch player
                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                // set the winner and check if cross is 
                break;
              }
            }
          }
        } else { // game over
          newGame(); // restart the game
        }
        // Refresh the drawing canvas
        repaint(); // Callback paintComponent().
      }
    });

    // Setup the status bar (JLabel) to display status message
    statusBar = new JLabel();
    statusBar.setFont(FONT_STATUS);
    statusBar.setBackground(COLOR_BG_STATUS);
    statusBar.setOpaque(true);
    statusBar.setPreferredSize(new Dimension(300, 30));
    statusBar.setHorizontalAlignment(JLabel.LEFT);
    statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

    super.setLayout(new BorderLayout());
    super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
    super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
    // account for statusBar in height
    super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

    // Set up Game
    initGame();
    newGame();
  }

  /** Initialize the game (run once) */
  public void initGame() {
    board = new Board(); // allocate the game-board
  }

  /** Reset the game-board contents and the current-state, ready for new game */
  public void newGame() {
    for (int row = 0; row < Board.ROWS; ++row) {
      for (int col = 0; col < Board.COLS; ++col) {
        board.cells[row][col].content = Seed.NO_SEED; // all cells empty
      }
    }
    currentPlayer = Seed.CROSS; // cross plays first
    currentState = State.PLAYING; // ready to play
  }

  /** Custom painting codes on this JPanel */
  @Override
  public void paintComponent(Graphics g) { // Callback via repaint()
    super.paintComponent(g);
    setBackground(COLOR_BG); // set its background color

    board.paint(g); // ask the game board to paint itself

    // Print status-bar message
    if (currentState == State.PLAYING) {
      statusBar.setForeground(Color.BLACK);
      statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
      if (currentPlayer == Seed.CROSS) {
        SoundEffect.EXPLODE.play();
      } else {
        SoundEffect.EAT_FOOD.play();
      }
    } else if (currentState == State.DRAW) {
      SoundEffect.DIE.play();

      statusBar.setForeground(Color.RED);
      statusBar.setText("It's a Draw! Click to play again.");
    } else if (currentState == State.CROSS_WON) {
      SoundEffect.EXPLODE.play();
      statusBar.setForeground(Color.RED);
      statusBar.setText("'X' Won! Click to play again.");
    } else if (currentState == State.NOUGHT_WON) {
      SoundEffect.EXPLODE.play();

      statusBar.setForeground(Color.RED);
      statusBar.setText("'O' Won! Click to play again.");
    }
  }

  /** The entry "main" method */
  public void play() {
    // Run GUI construction codes in Event-Dispatching thread for thread safety
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame(TITLE);
        // Set the content-pane of the JFrame to an instance of main JPanel
        frame.setContentPane(new ConnectFour());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // center the application window
        frame.setVisible(true); // show it
      }
    });
  }

  public void checkWin(){

  }
}