package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.games.Game.Difficulty;
import nz.ac.auckland.se206.games.Game.GameMode;
import nz.ac.auckland.se206.games.Game.Setting;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;
import nz.ac.auckland.se206.words.WordHolder;

public class CategoryDisplayController implements SwitchInListener {

  @FXML private Label categoryLabel;
  @FXML private Label accuracyDisplay;
  @FXML private Label timeDisplay;
  @FXML private VBox userInstructions;

  /**
   * Changes from category display to the canvas when user wants to start
   *
   * @param event event that has triggered this method
   */
  @FXML
  private void onStart(ActionEvent event) {
    SoundEffects.stopBackgroundMusic();
    // go to canvas view
    GameMode gameMode = ProfileHolder.getInstance().getCurrentProfile().getGameMode();
    // switch to the game mode user has selected
    switch (gameMode) {
        // go to correct game mode
      case HIDDEN:
        // hidden word game mode
        SceneManager.changeScene(event, AppUi.HIDDEN_CANVAS);
        break;
      case NORMAL:
        // normal game mode
        SceneManager.changeScene(event, AppUi.NORMAL_CANVAS);
        break;
      case SCRAMBLE:
        // normal game mode
        SceneManager.changeScene(event, AppUi.SCRAMBLE_CANVAS);
        break;
      case ZEN:
        // zen game mode
        SceneManager.changeScene(event, AppUi.ZEN_CANVAS);
        break;
      default:
        break;
    }
  }

  /** Resets the screen when it is switched to so that words can be updated and gotten */
  @Override
  public void onSwitchIn() {
    // update the text label for the game
    // make sure the text is visible
    userInstructions.setVisible(true);
    // get the current word
    if (ProfileHolder.getInstance().getCurrentProfile().getGameMode() != GameMode.SCRAMBLE) {
      categoryLabel.setText(WordHolder.getInstance().getCurrentWord());
    } else {
      categoryLabel.setText(WordHolder.getInstance().getScrambledWord());
      // get current game mode time and accuracy
    }
    GameMode gameMode = ProfileHolder.getInstance().getCurrentProfile().getGameMode();
    Difficulty time =
        ProfileHolder.getInstance().getCurrentProfile().getSetting2Difficulty().get(Setting.TIME);
    Difficulty accuracy =
        ProfileHolder.getInstance()
            .getCurrentProfile()
            .getSetting2Difficulty()
            .get(Setting.ACCURACY);
    // check for zen mode
    switch (gameMode) {
      case ZEN:
        // don't show instructions
        userInstructions.setVisible(false);
        break;
      default:
        // load correct value for time
        String timeText = null;
        switch (time) {
          case EASY:
            // 60 seconds
            timeText = "60";
            break;
          case MEDIUM:
            // 45 seconds
            timeText = "45";
            break;
          case HARD:
            // 30 seconds
            timeText = "30";
            break;
          case MASTER:
            // 15 seconds
            timeText = "15";
            break;
        }
        // load correct value for accuracy
        String accuracyText = null;
        switch (accuracy) {
          case EASY:
            // top 3
            accuracyText = "3";
            break;
          case MEDIUM:
            // top 2
            accuracyText = "2";
            break;
          case HARD:
            // top 1
            accuracyText = "1";
            break;
          default:
            break;
        }
        // update labels
        timeDisplay.setText(timeText);
        accuracyDisplay.setText(accuracyText);
    }
  }
}
