package nz.ac.auckland.se206.controllers;

import ai.djl.modality.Classifications;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;
import nz.ac.auckland.se206.words.WordHolder;

public class ZenCanvasController extends CanvasController {
  private Color currentColor; // American spelling
  private boolean isEraser;

  private String[] encouragement = {
    "Good work",
    "love it",
    "love that drawing",
    "Amazing work",
    "Cool drawing",
    "absolute masterpiece"
  };

  /** Always returns false as we don't want ZEN mode to end */
  @Override
  protected boolean isWin(List<Classifications.Classification> classifications) {
    return false;
  }

  /**
   * Keeps everything visible at all times, for zen mode
   *
   * @param visibility This is ignored for zen mode as we always will have access to save button
   */
  @Override
  protected void setEndgameVisibility(boolean visibility) {}

  /** For zen mode we play some calm nature sounds comapred to ticking clock */
  @Override
  protected void playGamemodeSoundEffect() {
    try {
      // set correct music to play and repeat the sound
      setTimerSoundEffect(new SoundEffects("zen"));
      getTimerSoundEffect().playRepeatSound();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  /** Chooses a random encouraging comment and speaks it every 20 seconds */
  @Override
  protected void gamemodeSpeak(String prediction) {
    if (ttsCounter % 20 == 1) {
      speak(encouragement[(int) Math.floor(Math.random() * encouragement.length)]);
    }
  }

  /** sets the timer to blank value rather than a time as such */
  @Override
  protected void resetTimer() {
    timerLabel.setText("--:--");
  }

  /** Resets game when switching to this screen by clearing everything */
  @Override
  public void onSwitchIn() {
    String currentWord = WordHolder.getInstance().getCurrentWord();
    wordLabel.setText(currentWord); // display new category

    // stop predictions from taking place
    drawingStarted = false;

    // empty label when starting game
    resetPredictionLabel();

    // enable canvas and drawing buttons
    canvas.setDisable(false);
    toolsContainer.setDisable(false);

    // reset to pen function
    paintButton.fire();
    game = new Game(currentWord, Game.GameMode.ZEN);
    clearCanvas();
    startTimer();
  }

  /** Called when the game is left mid way through stops game from running */
  @Override
  public void onSwitchOut() {
    // terminate any unfinished game
    // zen mode has no duration
    // textToSpeech.terminate();
    game.setDuration(0);
    // update profile stats
    Profile userProfile = ProfileHolder.getInstance().getCurrentProfile();
    userProfile.updateAllStats(game);
    if (!userProfile.isGuest()) {
      try {
        userProfile.saveToFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // stop the zen mode music and start main menu one
    getTimerSoundEffect().stopSound();
    SoundEffects.playBackgroundMusic();
    // stop ml model predictions
    if (!(timeline.getStatus() == Animation.Status.STOPPED)) {
      timeline.stop();
    }
  }

  /** This overrides the function as there is no reason to count down */
  @Override
  protected void countDown() {}

  /** When the paint tool is clicked it either defaults to black or current colour selected */
  @FXML
  protected void onPaintTool() {
    isEraser = false;
    if (currentColor == null) {
      graphic.setStroke(Color.BLACK);
    }
    graphic.setStroke(currentColor);
  }

  /** Enables the eraser for the user */
  @FXML
  protected void onEraseTool() {
    // change to eraser
    isEraser = true;
    graphic.setStroke(Color.WHITE);
  }

  /** updates the pen colour so when user draws it is correct colour */
  private void updateColor() {
    if (isEraser != true) {
      graphic.setStroke(currentColor);
    }
  }

  // COLORS

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChooseBlue() {
    currentColor = Color.rgb(56, 182, 255);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChooseDarkBlue() {
    currentColor = Color.rgb(0, 74, 173);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChooseRed() {
    currentColor = Color.rgb(255, 22, 22);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChooseGreen() {
    currentColor = Color.rgb(126, 217, 87);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChooseBlack() {
    currentColor = Color.rgb(0, 0, 0);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChoosePink() {
    currentColor = Color.rgb(255, 102, 196);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChooseOrange() {
    currentColor = Color.rgb(255, 145, 77);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChooseYellow() {
    currentColor = Color.rgb(255, 222, 89);
    updateColor();
  }

  /** changes the colour of the pen to the correct RGB value */
  @FXML
  private void onChoosePurple() {
    currentColor = Color.rgb(140, 82, 255);
    updateColor();
  }

  /** Changes the pen size the correct size value, in this case extra large */
  @FXML
  private void onChooseExtraLarge() {
    graphic.setLineWidth(25);
  }

  /** Changes the pen size the correct size value, in this case large */
  @FXML
  private void onChooseLarge() {
    graphic.setLineWidth(15);
  }

  /** Changes the pen size the correct size value, in this case medium (default) */
  @FXML
  private void onChooseMedium() {
    graphic.setLineWidth(7);
  }

  /** Changes the pen size the correct size value, in this case small */
  @FXML
  private void onChooseSmall() {
    graphic.setLineWidth(4);
  }
}
