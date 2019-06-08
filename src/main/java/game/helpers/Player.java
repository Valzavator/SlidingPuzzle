package game.helpers;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player {
    private static boolean isPlaySound = true;

    public static void setPlaySound(boolean playSound) {
        isPlaySound = playSound;
    }

    public static void play(String soundFile) {
        try {
            if (!isPlaySound)
                return;
            URL soundPath = Player.class.getResource(soundFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}