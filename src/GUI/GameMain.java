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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMain extends JPanel {
    private static final long serialVersionUID = 1L;

    // Game constants
    public static final String TITLE = "Tic Tac Toe - Enhanced UI";
    private static final int GAME_TIME = 30; // Total time in seconds

    // Game objects
    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private JLabel timerLabel;
    private AIPlayer aiPlayer;
    private Timer gameTimer;
    private int timeLeft;

    private String playerName = "Player"; // Default player name
    private int lastMoveRow = -1;
    private int lastMoveCol = -1;

    private boolean gameStarted = false; // To control board visibility

    /** Constructor to setup the UI and game components */
    public GameMain(JFrame parentFrame, String playerName) {
        this.playerName = playerName; // Save player name

        // Set up panel
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 40)); // Fit to board size

        // Mouse listener for board interactions
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gameStarted) {
                    return; // Ignore clicks until the game has started
                }

                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        makeMove(row, col, currentPlayer);
                        if (currentState == State.PLAYING && currentPlayer == aiPlayer.mySeed) {
                            aiMove();
                        }
                    }
                } else {
                    restartGame();
                }
                repaint();
            }
        });

        // Create status bar
        statusBar = new JLabel();
        statusBar.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusBar.setForeground(Color.WHITE);
        statusBar.setOpaque(true);
        statusBar.setBackground(new Color(50, 50, 50));
        statusBar.setHorizontalAlignment(JLabel.CENTER);

        // Create timer label
        timerLabel = new JLabel();
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        timerLabel.setForeground(Color.YELLOW);
        timerLabel.setOpaque(true);
        timerLabel.setBackground(new Color(30, 30, 30));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create status panel (with timer)
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusBar, BorderLayout.CENTER);
        statusPanel.add(timerLabel, BorderLayout.EAST);
        statusPanel.setBackground(Color.DARK_GRAY);
        statusPanel.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 40)); // Fixed height

        // Add components to the main panel
        add(statusPanel, BorderLayout.PAGE_END);

        // Initialize the game and start the timer
        initGame();
        startGame();

        // Adjust parent frame to fit the content
        parentFrame.setContentPane(this);
        parentFrame.pack();
        parentFrame.setResizable(false); // Lock size
        parentFrame.setLocationRelativeTo(null); // Center on screen
    }

    /** Initialize game components */
    private void initGame() {
        board = new Board();
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        aiPlayer = new AIPlayerMinimax(board);
        aiPlayer.setSeed(Seed.NOUGHT);
        timeLeft = GAME_TIME;
        updateTimerLabel();
        gameStarted = true;
    }

    /** Start the game timer */
    private void startGame() {
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            updateTimerLabel();
            if (timeLeft <= 0) {
                gameTimer.stop();
                endGame(); // Trigger end game when time is up
            }
        });
        gameTimer.start();
        updateStatus();
    }

    /** Restart the game */
    private void restartGame() {
        gameTimer.stop();
        SoundEffect.START.play(); // Play the start sound
        initGame();
        startGame();
        repaint();
    }

    /** End the game and display results */
    private void endGame() {
        if (currentState == State.PLAYING) {
            currentState = State.DRAW; // Treat as a draw if time is up
            updateStatus();
        }

        // Play sad trumpet sound when the game ends
        SoundEffect.LOSE.play();

        // Show a dialog to restart or stop
        showEndGameOptions();
        repaint();
    }

    /** Show options when the game ends */
    private void showEndGameOptions() {
        int option = JOptionPane.showOptionDialog(
            this,
            "Game Over! Would you like to restart or stop the game?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Restart", "Stop"},
            "Restart"
        );

        if (option == JOptionPane.YES_OPTION) {
            restartGame(); // Restart the game
        } else {
            System.exit(0); // Exit the application
        }
    }

    /** Update the timer label */
    private void updateTimerLabel() {
        timerLabel.setText("Time Left: " + timeLeft + "s");
    }

    /** Make a move on the board */
    private void makeMove(int row, int col, Seed seed) {
        board.cells[row][col].content = seed;
        lastMoveRow = row;
        lastMoveCol = col;
        currentState = board.stepGame(seed, row, col);

        if (currentState == State.PLAYING) {
            if (currentPlayer == aiPlayer.mySeed) {
                SoundEffect.EXPLODE.play(); // AI move sound
            } else {
                SoundEffect.EAT_FOOD.play(); // Player move sound
            }
        } else if (currentState == State.CROSS_WON) {
            SoundEffect.WIN.play();
        } else if (currentState == State.NOUGHT_WON) {
            SoundEffect.LOSE.play();
        } else if (currentState == State.DRAW) {
            SoundEffect.EXPLODE.play();
        }

        currentPlayer = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
        updateStatus();

        // Check if game has ended
        if (currentState != State.PLAYING) {
            endGame();
        }
    }

    /** Make AI move */
    private void aiMove() {
        new Thread(() -> {
            try {
                Thread.sleep(1000 + (int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int[] aiMove = aiPlayer.move();
            if (aiMove != null) {
                SwingUtilities.invokeLater(() -> {
                    makeMove(aiMove[0], aiMove[1], aiPlayer.mySeed);
                    repaint();
                });
            }
        }).start();
    }

    /** Update status bar message */
    private void updateStatus() {
        if (currentState == State.PLAYING) {
            statusBar.setText((currentPlayer == Seed.CROSS) ? playerName + "'s Turn" : "AI's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setText("It's a Draw!");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setText(playerName + " Wins!");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setText("AI Wins!");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.DARK_GRAY);
        if (gameStarted && board != null) {
            board.paint(g);
        }

        if (lastMoveRow != -1 && lastMoveCol != -1) {
            g.setColor(new Color(255, 215, 0, 128)); // Highlight color
            g.fillRect(lastMoveCol * Cell.SIZE, lastMoveRow * Cell.SIZE, Cell.SIZE, Cell.SIZE);
        }
    }
}
