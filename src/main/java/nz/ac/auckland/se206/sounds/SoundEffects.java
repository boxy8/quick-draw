package nz.ac.auckland.se206.sounds;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.words.CategorySelector;

public class SoundEffects {

  public static void playSound(String fileName) throws URISyntaxException {
    Media sound =
        new Media(CategorySelector.class.getResource("/sounds").toURI() + "/" + fileName + ".wav");
    MediaPlayer player = new MediaPlayer(sound);
    player.play();
  }
}
