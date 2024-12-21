package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class HomePage extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField playerNameField;
    private JFrame parentFrame;
    private BufferedImage backgroundImg; // Background image

    public HomePage(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Set preferred size to control page height
        setPreferredSize(new Dimension(800, 600)); // Set width: 800, height: 600

        // Load background pixel-art style
        try {
            URL imgUrl = getClass().getClassLoader().getResource("GUI/images/background.png");
            if (imgUrl != null) {
                backgroundImg = ImageIO.read(imgUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new GridBagLayout());

        // Play background music (backsong)
        SoundEffect.volume = SoundEffect.Volume.MEDIUM; // Set volume to medium
        SoundEffect.BACKSONG.loop(); // Loop backsong

        // Title label
        JLabel titleLabel = new JLabel("TIC TAC TOE");

        try {
            // Load pixel font and apply bold
            Font titleFont = loadPixelFont(48).deriveFont(Font.BOLD);
            titleLabel.setFont(titleFont);
        } catch (Exception e) {
            // Fallback to default bold font if custom font fails
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        }

        titleLabel.setForeground(new Color(85, 255, 85)); // Retro green

        // Description label
        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Selamat datang di permainan Tic Tac Toe!<br>"
                + "Masukkan nama Anda dan tekan tombol Start untuk memulai.</div></html>");
        descriptionLabel.setFont(loadPixelFont(16));
        descriptionLabel.setForeground(Color.WHITE); // White text

        // Player name input
        playerNameField = new JTextField(15);
        playerNameField.setFont(loadPixelFont(16));
        playerNameField.setBackground(new Color(30, 30, 30)); // Dark gray input field
        playerNameField.setForeground(Color.WHITE);
        playerNameField.setCaretColor(Color.YELLOW); // Cursor color is yellow
        playerNameField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Margin dalam
        playerNameField.setPreferredSize(new Dimension(300, 40)); // Oval shape

        // Make input field rounded
        playerNameField.setOpaque(false);
        playerNameField.setUI(new RoundedTextFieldUI());

        // Start button
        JButton startButton = new JButton("START GAME") {
            @Override
            protected void paintComponent(Graphics g) {
                // Gambar latar belakang tombol
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(255, 165, 0)); // Retro orange
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40); // Rounded rectangle

                // Panggil metode asli untuk menggambar teks
                super.paintComponent(g);

                g2d.dispose();
            }
        };
        startButton.setFont(loadPixelFont(16));
        startButton.setForeground(Color.BLACK); // Text color black
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Margin dalam
        startButton.setPreferredSize(new Dimension(300, 50)); // Button size
        startButton.addActionListener(this::startGame);

        // Layout: Adjust positions
        GridBagConstraints gbc = new GridBagConstraints();

        // Title label at the top
        gbc.insets = new Insets(100, 10, 5, 10); // Small gap between title and description
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.0; // Push title slightly up
        add(titleLabel, gbc);

        // Description below title
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 15, 10); // Reduce space below description
        gbc.weighty = 0.0; // Adjust alignment closer to input field
        add(descriptionLabel, gbc);

        // Player name input in the middle
        gbc.gridy = 2;
        gbc.insets = new Insets(50, 10, 80, 10); // Equal spacing above and below input
        gbc.weighty = 0.0; // Push to the center
        add(playerNameField, gbc);

        // Start button below input field
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 10); // Reduce space below button
        gbc.weighty = 0.0; // Reduce bottom space
        add(startButton, gbc);
    }

    /** Load a custom pixel-style font */
    private Font loadPixelFont(int size) {
        try {
            URL fontUrl = getClass().getClassLoader().getResource("GUI/fonts/minecraftia.ttf");
            if (fontUrl != null) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
                return font.deriveFont((float) size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Font("SansSerif", Font.BOLD, size); // Fallback font
    }

    /** Handle Start Game button */
    private void startGame(ActionEvent e) {
        String playerName = playerNameField.getText().trim();
        if (playerName.isEmpty()) {
            playerName = "Player"; // Default name
        }

        // Stop backsong and play start sound
        SoundEffect.BACKSONG.stop(); // Stop backsong
        SoundEffect.START.play(); // Play start sound

        // Transition to the game
        parentFrame.setContentPane(new GameMain(parentFrame, playerName));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image with fit/crop logic
        if (backgroundImg != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = backgroundImg.getWidth();
            int imgHeight = backgroundImg.getHeight();

            // Scale the image to fit while maintaining aspect ratio
            double scaleX = (double) panelWidth / imgWidth;
            double scaleY = (double) panelHeight / imgHeight;
            double scale = Math.max(scaleX, scaleY); // Choose scale to fill entire area

            int scaledWidth = (int) (imgWidth * scale);
            int scaledHeight = (int) (imgHeight * scale);

            // Center the image
            int x = (panelWidth - scaledWidth) / 2;
            int y = (panelHeight - scaledHeight) / 2;

            g2d.drawImage(backgroundImg, x, y, scaledWidth, scaledHeight, null);
            g2d.dispose();
        }
    }

    /** Custom UI for rounded input field */
    private static class RoundedTextFieldUI extends javax.swing.plaf.basic.BasicTextFieldUI {
        @Override
        protected void paintSafely(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(30, 30, 30)); // Background color
            g2d.fillRoundRect(0, 0, getComponent().getWidth(), getComponent().getHeight(), 40, 40);
            g2d.dispose();
            super.paintSafely(g);
        }
    }
}
