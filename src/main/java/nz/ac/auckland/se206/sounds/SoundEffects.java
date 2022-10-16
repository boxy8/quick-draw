package nz.ac.auckland.se206.sounds;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.words.CategorySelector;

public class SoundEffects {

  private static MediaPlayer BackgroundPlayer;
  private static boolean musicPlaying;
  private static boolean isMute = true;

  // SOUND EFFECT CREDIT
  // background sound: https://pixabay.com/music/beautiful-plays-ambient-piano-ampamp-strings-10711/
  // timer, win, lose, zen mode sound: https://mixkit.co/free-sound-effects/

  /** Play the background music */
  public static void playBackgroundMusic() {
    // check to see if the player is loaded already
    if (BackgroundPlayer == null) {
      Media backgroundSound;
      // load media
      try {
        backgroundSound =
            new Media(
                CategorySelector.class.getResource("/sounds").toURI()
                    + "/"
                    + "backgroundMusic"
                    + ".wav");
        BackgroundPlayer = new MediaPlayer(backgroundSound);
        // put on repeat
        BackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
    // check if music is already playing
    if (!musicPlaying) {
      // play music
      BackgroundPlayer.play();
      musicPlaying = true;
    }
  }

  /** Stop the background music playing */
  public static void stopBackgroundMusic() {
    musicPlaying = false;
    BackgroundPlayer.stop();
  }

  /** toggles sound on and off */
  public static void muteToggle() {
    BackgroundPlayer.setMute(!BackgroundPlayer.isMute());
    isMute = !isMute;
  }

  private MediaPlayer player;

  /**
   * Constructor for Sound Effects
   *
   * @param fileName the name of the music you want to play
   * @throws URISyntaxException if the file name is wrong/does not exist
   */
  public SoundEffects(String fileName) throws URISyntaxException {
    Media sound =
        new Media(CategorySelector.class.getResource("/sounds").toURI() + "/" + fileName + ".wav");
    player = new MediaPlayer(sound);
  }

  /** play the sound that you have loaded */
  public void playSound() {
    player.play();
  }

  /**
   * play the sound you have chosen, on repeat
   *
   * @throws URISyntaxException if the player does not find file
   */
  public void playRepeatSound() throws URISyntaxException {
    player.setCycleCount(MediaPlayer.INDEFINITE);
    player.play();
  }

  /** checks to see if the sound needs to be muted, and mutes it if so */
  public void toggleSound() {
    player.setMute(!isMute);
  }

  /** Stop playing the sound that you have chosen */
  public void stopSound() {
    player.stop();
  }
}
