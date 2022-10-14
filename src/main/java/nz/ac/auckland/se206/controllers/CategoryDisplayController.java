package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.games.Game.GameMode;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;
import nz.ac.auckland.se206.words.WordHolder;

public class CategoryDisplayController implements SwitchInListener {

  @FXML private Label categoryLabel;

  /**
   * Changes from category display to the canvas when user wants to start
   *
   * @param event
   */
  @FXML
  private void onStart(ActionEvent event) {
    SoundEffects.stopBackgroundMusic();
    // go to canvas view
    GameMode gameMode = ProfileHolder.getInstance().getCurrentProfile().getGameMode();
    switch (gameMode) {
      case HIDDEN:
        SceneManager.changeScene(event, AppUi.HIDDEN_CANVAS);
        break;
      case NORMAL:
        SceneManager.changeScene(event, AppUi.NORMAL_CANVAS);
        break;
      case ZEN:
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
    categoryLabel.setText(WordHolder.getInstance().getCurrentWord());
  }
}
