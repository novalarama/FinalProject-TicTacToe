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
import javax.swing.plaf.basic.BasicTextFieldUI;

public class RoundedTextFieldUI extends BasicTextFieldUI {
  @Override
  protected void paintSafely(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(new Color(30, 30, 30));
    g2d.fillRoundRect(0, 0, getComponent().getWidth(), getComponent().getHeight(), 40, 40);
    g2d.dispose();
    super.paintSafely(g);
  }
}