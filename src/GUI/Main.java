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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame(GameMain.TITLE);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      setCustomCursor(frame);

      frame.setContentPane(new HomePage(frame));

      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      SoundEffect.BACKSONG.loop();
    });
  }

  private static void setCustomCursor(JFrame frame) {
    try {
      URL cursorUrl = Main.class.getClassLoader().getResource("GUI/images/cursor.png");
      if (cursorUrl != null) {
        BufferedImage cursorImg = ImageIO.read(cursorUrl);
        // Resize the cursor image
        Image resizedCursorImg = cursorImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(resizedCursorImg, new Point(0, 0),
            "custom cursor");
        frame.setCursor(customCursor);
      } else {
        System.err.println("Couldn't find file: GUI/images/image.png");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}