package nz.ac.auckland.se206.controllers;

import ai.djl.modality.Classifications;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;

public class ZenCanvasController extends CanvasController {
  private Color currentColor; // American spelling : (
  private boolean isEraser;

  /** Always returns false as we don't want ZEN mode to end */
  @Override
  protected boolean isWin(List<Classifications.Classification> classifications) {
    return false;
  }

  @Override
  protected void setEndgameVisibility(boolean visibility) {}

  @Override
  protected void playGamemodeSoundEffect() {
    try {
      setTimerSoundEffect(new SoundEffects("zen"));
      getTimerSoundEffect().playRepeateSound();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  /** sets the timer to blank value rather than a time as such */
  @Override
  protected void resetTimer() {
    timerLabel.setText("--:--");
  }

  /** Called when the game is left mid way through stops game from running */
  @Override
  public void onSwitchOut() {
    // terminate any unfinished game
    game.setDuration(0);
    Profile userProfile = ProfileHolder.getInstance().getCurrentProfile();
    userProfile.updateAllStats(game);
    if (!userProfile.isGuest()) {
      try {
        userProfile.saveToFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    getTimerSoundEffect().stopSound();
    SoundEffects.playBackgroundMusic();

    if (!(timeline.getStatus() == Animation.Status.STOPPED)) {
      timeline.stop();
    }
  }

  /** This overrides the function as there is no reason to count down */
  @Override
  protected void countDown() {}

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

  private void updateColor() {
    if (isEraser != true) {
      graphic.setStroke(currentColor);
    }
  }

  // COLORS
  @FXML
  private void onChooseBlue() {
    currentColor = Color.rgb(56, 182, 255);
    updateColor();
  }

  @FXML
  private void onChooseDarkBlue() {
    currentColor = Color.rgb(0, 74, 173);
    updateColor();
  }

  @FXML
  private void onChooseRed() {
    currentColor = Color.rgb(255, 22, 22);
    updateColor();
  }

  @FXML
  private void onChooseGreen() {
    currentColor = Color.rgb(126, 217, 87);
    updateColor();
  }

  @FXML
  private void onChooseBlack() {
    currentColor = Color.rgb(0, 0, 0);
    updateColor();
  }

  @FXML
  private void onChoosePink() {
    currentColor = Color.rgb(255, 102, 196);
    updateColor();
  }

  @FXML
  private void onChooseOrange() {
    currentColor = Color.rgb(255, 145, 77);
    updateColor();
  }

  @FXML
  private void onChooseYellow() {
    currentColor = Color.rgb(255, 222, 89);
    updateColor();
  }

  @FXML
  private void onChoosePurple() {
    currentColor = Color.rgb(140, 82, 255);
    updateColor();
  }

  @FXML
  private void onChooseExtraLarge() {
    graphic.setLineWidth(25);
  }

  @FXML
  private void onChooseLarge() {
    graphic.setLineWidth(15);
  }

  @FXML
  private void onChooseMedium() {
    graphic.setLineWidth(7);
  }

  @FXML
  private void onChooseSmall() {
    graphic.setLineWidth(4);
  }
}
