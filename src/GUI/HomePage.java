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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HomePage extends JPanel {
  private static final long serialVersionUID = 1L;

  private JTextField playerNameField;
  private JFrame parentFrame;
  private BufferedImage backgroundImg;

  public HomePage(JFrame parentFrame) {
    this.parentFrame = parentFrame;

    setPreferredSize(new Dimension(800, 600));

    try {
      URL imgUrl = getClass().getClassLoader().getResource("GUI/images/background.png");
      if (imgUrl != null) {
        backgroundImg = ImageIO.read(imgUrl);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    setLayout(new GridBagLayout());

    SoundEffect.volume = SoundEffect.Volume.MEDIUM;
    SoundEffect.BACKSONG.loop();

    JLabel titleLabel = new JLabel("TIC TAC TOE");

    try {
      Font titleFont = loadPixelFont(48).deriveFont(Font.BOLD);
      titleLabel.setFont(titleFont);
    } catch (Exception e) {
      titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
    }

    titleLabel.setForeground(new Color(85, 255, 85));

    JLabel descriptionLabel = new JLabel("Selamat datang di permainan Tic Tac Toe!");
    descriptionLabel.setForeground(Color.WHITE);
    try {
      Font descriptionFont = loadPixelFont(16);
      descriptionLabel.setFont(descriptionFont);
    } catch (Exception e) {
      descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    }

    playerNameField = new JTextField(15);
    playerNameField.setUI(new GUI.RoundedTextFieldUI());
    playerNameField.setHorizontalAlignment(JTextField.CENTER);
    playerNameField.setFocusable(true);
    playerNameField.setColumns(20);
    playerNameField.setFont(loadPixelFont(16));
    playerNameField.setBackground(new Color(30, 30, 30));
    playerNameField.setForeground(Color.WHITE);
    playerNameField.setCaretColor(Color.YELLOW);
    playerNameField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    playerNameField.setPreferredSize(new Dimension(300, 40));

    playerNameField.setOpaque(false);
    playerNameField.setUI(new RoundedTextFieldUI());

    JButton startButton = new JButton("Choose Character") {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(255, 165, 0));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

        super.paintComponent(g);

        g2d.dispose();
      }
    };
    startButton.setFont(loadPixelFont(16));
    startButton.setForeground(Color.BLACK);
    startButton.setFocusPainted(false);
    startButton.setFocusable(false);
    startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    startButton.setPreferredSize(new Dimension(300, 50));
    startButton.addActionListener(e -> {
      SoundEffect.CLICK_CHAR.play();
      startCharacterSelection(e);
    });
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.insets = new Insets(100, 10, 5, 10);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.0;
    add(titleLabel, gbc);

    gbc.gridy = 1;
    gbc.insets = new Insets(0, 10, 15, 10);
    gbc.weighty = 0.0;
    add(descriptionLabel, gbc);

    gbc.gridy = 2;
    gbc.insets = new Insets(50, 10, 80, 10);
    gbc.weighty = 0.0;
    add(playerNameField, gbc);

    gbc.gridy = 3;
    gbc.insets = new Insets(0, 10, 0, 10);
    gbc.weighty = 0.0;
    add(startButton, gbc);
  }

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
    return new Font("SansSerif", Font.BOLD, size);
  }

  private void startCharacterSelection(ActionEvent e) {
    String playerName = playerNameField.getText().trim();
    if (playerName.isEmpty()) {
      playerName = "Player";
    }

    if (playerName.length() > 15) {
      JOptionPane.showMessageDialog(this, "Name is too long! Please use up to 15 characters.",
              "Name Too Long", JOptionPane.WARNING_MESSAGE);
      return;
    }

    parentFrame.setContentPane(new CharacterSelectionPage(parentFrame, playerName));
    parentFrame.revalidate();
    parentFrame.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (backgroundImg != null) {
      Graphics2D g2d = (Graphics2D) g.create();

      int panelWidth = getWidth();
      int panelHeight = getHeight();

      int imgWidth = backgroundImg.getWidth();
      int imgHeight = backgroundImg.getHeight();

      double scaleX = (double) panelWidth / imgWidth;
      double scaleY = (double) panelHeight / imgHeight;
      double scale = Math.max(scaleX, scaleY);

      int scaledWidth = (int) (imgWidth * scale);
      int scaledHeight = (int) (imgHeight * scale);

      int x = (panelWidth - scaledWidth) / 2;
      int y = (panelHeight - scaledHeight) / 2;

      g2d.drawImage(backgroundImg, x, y, scaledWidth, scaledHeight, null);
      g2d.dispose();
    }
  }

  private static class RoundedTextFieldUI extends javax.swing.plaf.basic.BasicTextFieldUI {
    @Override
    protected void paintSafely(Graphics g) {
      Graphics2D g2d = (Graphics2D) g.create();
      g2d.setColor(new Color(30, 30, 30));
      g2d.fillRoundRect(0, 0, getComponent().getWidth(), getComponent().getHeight(), 40, 40);
      g2d.dispose();
      super.paintSafely(g);
    }
  }
}