package nz.ac.auckland.se206.sounds;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.words.CategorySelector;

public class SoundEffects {

  private static MediaPlayer BackgroundPlayer;
  private MediaPlayer player;

  public static void backgroundSoundLoad() throws URISyntaxException {
    Media backgroundSound =
        new Media(
            CategorySelector.class.getResource("/sounds").toURI()
                + "/"
                + "backgroundMusic"
                + ".wav");
    BackgroundPlayer = new MediaPlayer(backgroundSound);
  }

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
    BackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    BackgroundPlayer.play();
  }

  public static void stopBackgroundMusic() {
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
