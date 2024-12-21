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

    JLabel titleLabel = new JLabel("Select Your Character");
    try {
      Font titleFont = loadPixelFont(24).deriveFont(Font.BOLD);
      titleLabel.setFont(titleFont);
      titleLabel.setForeground(Color.WHITE);
    } catch (Exception e) {
      titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
    }

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(20, 0, 20, 0);
    add(titleLabel, gbc);

    characterIcons = loadCharacterImages();

    JButton prevCharacterButton = new JButton("<");
    prevCharacterButton.addActionListener(e -> showPreviousCharacter());
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(prevCharacterButton, gbc);

    characterLabel = new JLabel(resizeIcon(characterIcons.get(currentCharacterIndex), 100, 100));
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(characterLabel, gbc);

    JButton nextCharacterButton = new JButton(">");
    nextCharacterButton.addActionListener(e -> showNextCharacter());
    gbc.gridx = 2;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(nextCharacterButton, gbc);

    JLabel opponentLabelTitle = new JLabel("Select Your Opponent");
    try {
      Font titleFont = loadPixelFont(24).deriveFont(Font.BOLD);
      opponentLabelTitle.setFont(titleFont);
      opponentLabelTitle.setForeground(Color.WHITE);
    } catch (Exception e) {
      opponentLabelTitle.setFont(new Font("SansSerif", Font.BOLD, 48));
    }

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(20, 0, 20, 0);
    add(opponentLabelTitle, gbc);

    JButton prevOpponentButton = new JButton("<");
    prevOpponentButton.addActionListener(e -> showPreviousOpponent());
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(prevOpponentButton, gbc);

    opponentLabel = new JLabel(resizeIcon(characterIcons.get(currentOpponentIndex), 100, 100));
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(opponentLabel, gbc);

    JButton nextOpponentButton = new JButton(">");
    nextOpponentButton.addActionListener(e -> showNextOpponent());
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    gbc.insets = new Insets(10, 10, 10, 10);
    add(nextOpponentButton, gbc);

    JButton startGameButton = new JButton("Start Game");
    startGameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (currentCharacterIndex != currentOpponentIndex) {
          selectedCharacter = characterIcons.get(currentCharacterIndex).toString();
          selectedOpponent = characterIcons.get(currentOpponentIndex).toString();
          Seed.CROSS.setImage(selectedCharacter);
          Seed.NOUGHT.setImage(selectedOpponent);
          parentFrame.setContentPane(new GameMain(parentFrame, playerName));
          parentFrame.revalidate();
          parentFrame.repaint();
        } else {
          JOptionPane.showMessageDialog(parentFrame, "Please select different characters for player and opponent.");
        }
      }
    });

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(20, 0, 20, 0);
    add(startGameButton, gbc);
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
    characterLabel.setIcon(resizeIcon(characterIcons.get(currentCharacterIndex), 100, 100));
  }

  private void showNextCharacter() {
    currentCharacterIndex = (currentCharacterIndex + 1) % characterIcons.size();
    characterLabel.setIcon(resizeIcon(characterIcons.get(currentCharacterIndex), 100, 100));
  }

  private void showPreviousOpponent() {
    currentOpponentIndex = (currentOpponentIndex - 1 + characterIcons.size()) % characterIcons.size();
    opponentLabel.setIcon(resizeIcon(characterIcons.get(currentOpponentIndex), 100, 100));
  }

  private void showNextOpponent() {
    currentOpponentIndex = (currentOpponentIndex + 1) % characterIcons.size();
    opponentLabel.setIcon(resizeIcon(characterIcons.get(currentOpponentIndex), 100, 100));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (backgroundImg != null) {
      g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
    }
  }
}