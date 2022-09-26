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

  // sets the main content (content below the nav bar)
  public void setContent(AppUi appUi) {
    Object controller = SceneManager.getController(appUi);
    if (controller instanceof SwitchInListener switchListener) {
      switchListener.onSwitchIn();
    }

    // switch main content
    border.setCenter(SceneManager.getUiRoot(appUi));
  }

  public void setProfileButton() {
    profileButton.setText(ProfileHolder.getInstance().getCurrentProfile().getUsername());
  }

  @FXML
  private void onGoHome(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.MAIN_MENU);
  }

  @FXML
  private void onShowProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_PAGE);
  }
}
