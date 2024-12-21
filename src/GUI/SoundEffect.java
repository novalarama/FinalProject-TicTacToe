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

import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
  CLICK_CHAR("GUI/audio/click-char.wav"),
  EAT_FOOD("GUI/audio/jumpclick.wav"),
  EXPLODE("GUI/audio/selectclick.wav"),
  WIN("GUI/audio/win.wav"),
  LOSE("GUI/audio/lose.wav"),
  START("GUI/audio/start.wav"),
  BACKSONG("GUI/audio/backsong.wav");

  public static enum Volume {
    MUTE, LOW, MEDIUM, HIGH
  }

  public static Volume volume = Volume.MEDIUM;

  private Clip clip;

  private SoundEffect(String soundFileName) {
    try {
      URL url = this.getClass().getClassLoader().getResource(soundFileName);
      if (url == null) {
        System.err.println("Couldn't find file: " + soundFileName);
        return;
      }
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void play() {
    if (volume != Volume.MUTE) {
      if (clip.isRunning())
        clip.stop();
      clip.setFramePosition(0);
      clip.start();
    }
  }

  public void loop() {
    if (volume != Volume.MUTE) {
      if (clip.isRunning())
        clip.stop();
      clip.setFramePosition(0);
      clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop indefinitely
    }
  }

  public void stop() {
    if (clip.isRunning()) {
      clip.stop();
    }
  }

  static void initGame() {
    values(); // Initialize all sound effects
  }
}
