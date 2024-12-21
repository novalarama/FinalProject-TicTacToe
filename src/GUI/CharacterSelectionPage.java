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
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CharacterSelectionPage extends JPanel {
  private static final long serialVersionUID = 1L;

  private JFrame parentFrame;
  private String playerName;
  private String selectedCharacter;
  private String selectedOpponent;
  private String difficultyLevel = "Medium"; // Default difficulty level
  private List<ImageIcon> characterIcons;
  private int currentCharacterIndex = 0;
  private int currentOpponentIndex = 0;
  private JLabel characterLabel;
  private JLabel opponentLabel;
  private BufferedImage backgroundImg;

  public CharacterSelectionPage(JFrame parentFrame, String playerName) {
    this.parentFrame = parentFrame;
    this.playerName = playerName;

    setPreferredSize(new Dimension(800, 600));
    setFocusable(false); // Prevent panel from getting focus

    try {
      URL imgUrl = getClass().getClassLoader().getResource("GUI/images/background.png");
      if (imgUrl != null) {
        backgroundImg = ImageIO.read(imgUrl);
      } else {
        System.err.println("Couldn't find file: GUI/images/background.png");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(15, 15, 15, 15); // Add consistent spacing

    JLabel titleLabel = new JLabel("Select Your Character");
    try {
      Font titleFont = loadPixelFont(24).deriveFont(Font.BOLD);
      titleLabel.setFont(titleFont);
      titleLabel.setForeground(Color.WHITE);
    } catch (Exception e) {
      titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    }

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    add(titleLabel, gbc);

    characterIcons = loadCharacterImages();

    JButton prevCharacterButton = new JButton("<");
    prevCharacterButton.setFocusPainted(false);
    prevCharacterButton.setFocusable(false);
    prevCharacterButton.addActionListener(e -> {
      showPreviousCharacter();
      SoundEffect.CLICK_CHAR.play();
    });
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    add(prevCharacterButton, gbc);

    characterLabel = new JLabel(resizeIcon(characterIcons.get(currentCharacterIndex), 120, 120));
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    add(characterLabel, gbc);

    JButton nextCharacterButton = new JButton(">");
    nextCharacterButton.setFocusPainted(false);
    nextCharacterButton.setFocusable(false);
    nextCharacterButton.addActionListener(e -> {
      showNextCharacter();
      SoundEffect.CLICK_CHAR.play();
    });
    gbc.gridx = 2;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    add(nextCharacterButton, gbc);

    JLabel opponentLabelTitle = new JLabel("Select Your Opponent");
    opponentLabelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
    opponentLabelTitle.setForeground(Color.WHITE);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    add(opponentLabelTitle, gbc);

    JButton prevOpponentButton = new JButton("<");
    prevOpponentButton.setFocusPainted(false);
    prevOpponentButton.setFocusable(false);
    prevOpponentButton.addActionListener(e -> {
      showPreviousOpponent();
      SoundEffect.CLICK_CHAR.play();
    });
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    add(prevOpponentButton, gbc);

    opponentLabel = new JLabel(resizeIcon(characterIcons.get(currentOpponentIndex), 120, 120));
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    add(opponentLabel, gbc);

    JButton nextOpponentButton = new JButton(">");
    nextOpponentButton.setFocusPainted(false);
    nextOpponentButton.setFocusable(false);
    nextOpponentButton.addActionListener(e -> {
      showNextOpponent();
      SoundEffect.CLICK_CHAR.play();
    });
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    add(nextOpponentButton, gbc);

    JLabel difficultyLabel = new JLabel("Select Difficulty Level");
    try {
      Font titleFont = loadPixelFont(12).deriveFont(Font.BOLD);
      difficultyLabel.setFont(titleFont);
      difficultyLabel.setForeground(Color.WHITE);
    } catch (Exception e) {
      difficultyLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
    }

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(20, 0, 20, 0);
    add(difficultyLabel, gbc);

    JPanel difficultyPanel = new JPanel(new GridLayout(1, 3, 10, 0));
    difficultyPanel.setOpaque(false);
    JButton easyButton = new JButton("Easy");
    JButton mediumButton = new JButton("Medium");
    JButton hardButton = new JButton("Hard");

    // set easy button, medium button, and hard button font to pixel font
    easyButton.setFont(loadPixelFont(12));
    mediumButton.setFont(loadPixelFont(12));
    hardButton.setFont(loadPixelFont(12));

    // add padding vertically to the buttons
    easyButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    mediumButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    hardButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    // set easy button background color to green
    easyButton.setBackground(Color.GREEN);
    easyButton.setOpaque(true);
    mediumButton.setBackground(Color.YELLOW);
    mediumButton.setOpaque(true);
    hardButton.setBackground(Color.RED);
    hardButton.setOpaque(true);

    easyButton.addActionListener(e -> {
      difficultyLevel = "Easy";
      easyButton.setBackground(Color.BLACK);
      easyButton.setForeground(Color.WHITE);
      mediumButton.setBackground(Color.YELLOW);
      mediumButton.setForeground(Color.BLACK);
      hardButton.setBackground(Color.RED);
      hardButton.setForeground(Color.BLACK);
      SoundEffect.CLICK_CHAR.play();
    });

    mediumButton.addActionListener(e -> {
      difficultyLevel = "Medium";
      mediumButton.setBackground(Color.BLACK);
      mediumButton.setForeground(Color.WHITE);
      easyButton.setBackground(Color.GREEN);
      easyButton.setForeground(Color.BLACK);
      hardButton.setBackground(Color.RED);
      hardButton.setForeground(Color.BLACK);
      SoundEffect.CLICK_CHAR.play();
    });

    hardButton.addActionListener(e -> {
      difficultyLevel = "Hard";
      hardButton.setBackground(Color.BLACK);
      hardButton.setForeground(Color.WHITE);
      easyButton.setBackground(Color.GREEN);
      easyButton.setForeground(Color.BLACK);
      mediumButton.setBackground(Color.YELLOW);
      mediumButton.setForeground(Color.BLACK);
      SoundEffect.CLICK_CHAR.play();
    });

    difficultyPanel.add(easyButton);
    difficultyPanel.add(mediumButton);
    difficultyPanel.add(hardButton);

    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(20, 0, 20, 0);
    add(difficultyPanel, gbc);

    JButton startGameButton = new JButton("Start Game");
    startGameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (currentCharacterIndex != currentOpponentIndex) {
          selectedCharacter = characterIcons.get(currentCharacterIndex).getDescription();
          selectedOpponent = characterIcons.get(currentOpponentIndex).getDescription();
          Seed.CROSS.setImage(selectedCharacter);
          Seed.NOUGHT.setImage(selectedOpponent);
          parentFrame.setContentPane(new GameMain(parentFrame, playerName, difficultyLevel));
          parentFrame.revalidate();
          parentFrame.repaint();
        } else {
          JOptionPane.showMessageDialog(parentFrame, "Please select different characters for player and opponent.");
        }
      }
    });

    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 3;
    add(startGameButton, gbc);
  }

  private List<ImageIcon> loadCharacterImages() {
    List<ImageIcon> characterIcons = new ArrayList<>();
    characterIcons.add(loadImageIcon("GUI/images/char-crown.png"));
    characterIcons.add(loadImageIcon("GUI/images/char-green-mushroom.png"));
    characterIcons.add(loadImageIcon("GUI/images/char-mushroom.png"));
    characterIcons.add(loadImageIcon("GUI/images/char-plant.png"));
    characterIcons.add(loadImageIcon("GUI/images/char-sleep.png"));
    characterIcons.add(loadImageIcon("GUI/images/char-star.png"));
    return characterIcons;
  }

  private ImageIcon loadImageIcon(String path) {
    URL imgUrl = getClass().getClassLoader().getResource(path);
    if (imgUrl != null) {
      ImageIcon icon = new ImageIcon(imgUrl);
      icon.setDescription(path);
      return icon;
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }

  private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
    Image img = icon.getImage();
    Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImg);
  }

  private void showPreviousCharacter() {
    currentCharacterIndex = (currentCharacterIndex - 1 + characterIcons.size()) % characterIcons.size();
    characterLabel.setIcon(resizeIcon(characterIcons.get(currentCharacterIndex), 120, 120));
  }

  private void showNextCharacter() {
    currentCharacterIndex = (currentCharacterIndex + 1) % characterIcons.size();
    characterLabel.setIcon(resizeIcon(characterIcons.get(currentCharacterIndex), 120, 120));
  }

  private void showPreviousOpponent() {
    currentOpponentIndex = (currentOpponentIndex - 1 + characterIcons.size()) % characterIcons.size();
    opponentLabel.setIcon(resizeIcon(characterIcons.get(currentOpponentIndex), 120, 120));
  }

  private void showNextOpponent() {
    currentOpponentIndex = (currentOpponentIndex + 1) % characterIcons.size();
    opponentLabel.setIcon(resizeIcon(characterIcons.get(currentOpponentIndex), 120, 120));
  }

  private Font loadPixelFont(int size) {
    try {
      URL fontUrl = getClass().getClassLoader().getResource("GUI/fonts/minecraftia.ttf");
      if (fontUrl != null) {
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        return font.deriveFont((float) size);
      } else {
        System.err.println("Couldn't find font file: GUI/fonts/minecraftia.ttf");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new Font("SansSerif", Font.BOLD, size);
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
      double scale = Math.max(scaleX, scaleY); // Samakan pendekatan dengan HomePage

      int scaledWidth = (int) (imgWidth * scale);
      int scaledHeight = (int) (imgHeight * scale);

      int x = (panelWidth - scaledWidth) / 2;
      int y = (panelHeight - scaledHeight) / 2;

      g2d.drawImage(backgroundImg, x, y, scaledWidth, scaledHeight, null);
      g2d.dispose();
    }
  }
}
