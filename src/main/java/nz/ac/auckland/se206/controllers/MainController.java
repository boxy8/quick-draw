package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class MainController {

  @FXML private BorderPane border;

  @FXML private Button home;

  @FXML private Button profileButton;

  /**
   * sets the main content (content below the navigation bar)
   *
   * @param appUi
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
   * @param event
   */
  @FXML
  private void onGoHome(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.MAIN_MENU);
  }

  /**
   * When show profile is clicked it takes user to their profile page
   *
   * @param event
   */
  @FXML
  private void onShowProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_PAGE);
  }
}
