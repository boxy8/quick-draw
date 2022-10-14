package nz.ac.auckland.se206.sounds;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.words.CategorySelector;

public class SoundEffects {

  private static MediaPlayer BackgroundPlayer;
  private static boolean musicPlaying;
  private MediaPlayer player;

  // SOUND EFFECT CREDIT
  // background https://pixabay.com/music/beautiful-plays-ambient-piano-ampamp-strings-10711/
  // timer https://mixkit.co/free-sound-effects/clock/

  public SoundEffects(String fileName) throws URISyntaxException {
    Media sound =
        new Media(CategorySelector.class.getResource("/sounds").toURI() + "/" + fileName + ".wav");
    player = new MediaPlayer(sound);

    Media backgroundSound =
        new Media(
            CategorySelector.class.getResource("/sounds").toURI()
                + "/"
                + "backgroundMusic"
                + ".wav");
    BackgroundPlayer = new MediaPlayer(backgroundSound);
  }

  public static void playBackgroundMusic() {
    if (BackgroundPlayer == null) {
      Media backgroundSound;
      try {
        backgroundSound =
            new Media(
                CategorySelector.class.getResource("/sounds").toURI()
                    + "/"
                    + "backgroundMusic"
                    + ".wav");
        BackgroundPlayer = new MediaPlayer(backgroundSound);
        BackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }

    if (!musicPlaying) {
      BackgroundPlayer.play();
      musicPlaying = true;
    }
  }

  public static void stopBackgroundMusic() {
    musicPlaying = false;
    BackgroundPlayer.stop();
  }

  public void playSound() {
    player.play();
  }

  public void playRepeateSound() throws URISyntaxException {
    player.setCycleCount(MediaPlayer.INDEFINITE);
    player.play();
  }

  public void stopSound() {
    player.stop();
  }
}
