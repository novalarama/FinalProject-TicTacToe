/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #3
 * 1 - 5026231006 - Noval Akbar Ramadhany
 * 2 - 5026231007 - Nadia Lovely
 * 3 - 5026231090 - Nadia Ayula Assyaputri
 */
package TicTacToeAI.connectfour;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed { // to save as "Seed.java"
  // get the image hardcoded in the enum
  SNOWY("Snowy", "TicTacToeAI/images/cat1.gif"),
  STORMY("Stormy", "TicTacToeAI/images/cat2.gif"),
  NO_SEED(" ", null);

  // Private variables
  private final String displayName;
  private Image img = null;

  // Constructor (must be private)
  private Seed(String name, String imageFilename) {
    this.displayName = name;

    if (imageFilename != null) {
      URL imgURL = getClass().getClassLoader().getResource(imageFilename);
      ImageIcon icon = null;
      if (imgURL != null) {
        icon = new ImageIcon(imgURL);
        // System.out.println(icon); // debugging
      } else {
        System.err.println("Couldn't find file " + imageFilename);
      }
      img = icon.getImage();
    }
  }

  // Public getters
  public String getDisplayName() {
    return displayName;
  }

  public Image getImage() {
    return img;
  }
}
