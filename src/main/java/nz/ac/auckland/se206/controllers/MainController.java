package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;

public class MainController {

  @FXML private BorderPane border;

  @FXML private Button home;

  @FXML private Button profileButton;

  @FXML private Button muteButton;

  /**
   * sets the main content (content below the navigation bar)
   *
   * @param appUi the app ui they want to switch to
   */
  public void setContent(AppUi appUi) {
    Object controller = SceneManager.getController(appUi);
    if (controller instanceof SwitchInListener switchListener) {
      switchListener.onSwitchIn();
    }
    // switch main content
    border.setCenter(SceneManager.getUiRoot(appUi));
  }

  /** Sets the profile Button the the current user that is selected */
  public void setProfileButton() {
    profileButton.setText(ProfileHolder.getInstance().getCurrentProfile().getUsername());
  }

  /**
   * when the home button is pushed, it takes the user to home screen
   *
   * @param event event that triggered this method
   */
  @FXML
  private void onGoHome(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.MAIN_MENU);
  }

  /** Toggle the mute switch for the game */
  @FXML
  private void onMute() {
    SoundEffects.muteToggle();
  }

  /**
   * When show profile is clicked it takes user to their profile page
   *
   * @param event event that triggered this method
   */
  @FXML
  private void onShowProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_PAGE);
  }
}
