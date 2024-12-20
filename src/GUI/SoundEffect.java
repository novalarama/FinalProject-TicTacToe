package GUI;

import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    EAT_FOOD("GUI/audio/bruh.wav"),
    EXPLODE("GUI/audio/punch.wav"),
    // DIE("GUI/audio/happy.wav"),
    WIN("GUI/audio/happy.wav"),
    LOSE("GUI/audio/sad_trumpet.wav"); 

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
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    static void initGame() {
        values(); // Initialize all sound effects
    }
}
