package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;

public class MainMenuController implements SwitchInListener {

  @FXML private Button playButton;

  @FXML private Label userNameLabel;

  @FXML private Button profilesButton;

  /**
   * When play is pushed, takes user to the difficulty selector
   *
   * @param event
   */
  @FXML
  private void onPlay(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.DIFFICULTY_SELECTOR);
  }

  /**
   * When chose profile is selected, it takes user to profile list page
   *
   * @param event
   */
  @FXML
  private void onChooseProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_LIST);
  }

  /**
   * When show profile is selected, it takes user to the profile page
   *
   * @param event
   */
  @FXML
  private void onShowProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_PAGE);
  }

  /** When this controller is switched to makes everything default guest or selected profile */
  @Override
  public void onSwitchIn() {
    SoundEffects.playBackgroundMusic();
    // if no profile chosen, create a guest user
    if (ProfileHolder.getInstance().getCurrentProfile() == null) {
      try {
        // create a guest profile
        ProfileHolder.getInstance().setCurrentProfile(new Profile("Guest"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      // if logged in as a user, make button say Play as [username] instead of Play as guest
      if (!ProfileHolder.getInstance().getCurrentProfile().isGuest()) {
        playButton.setText(
            "Play as " + ProfileHolder.getInstance().getCurrentProfile().getUsername());
      }
    }
  }
}
