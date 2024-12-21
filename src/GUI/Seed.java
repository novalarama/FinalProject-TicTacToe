package GUI;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
  CROSS("X", "GUI/images/cat-cl.png"),
  NOUGHT("O", "GUI/images/cat-op.png"),
  NO_SEED(" ", null);

  private String displayName;
  private Image img = null;

  private Seed(String name, String imageFilename) {
    this.displayName = name;
    setImage(imageFilename);
  }

  public void setImage(String imageFilename) {
    if (imageFilename != null) {
      URL imgURL = getClass().getClassLoader().getResource(imageFilename);
      if (imgURL != null) {
        ImageIcon icon = new ImageIcon(imgURL);
        img = icon.getImage();
      } else {
        System.err.println("Couldn't find file: " + imageFilename);
      }
    }
  }

  public String getDisplayName() {
    return displayName;
  }

  public Image getImage() {
    return img;
  }
}