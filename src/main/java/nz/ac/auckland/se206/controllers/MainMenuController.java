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

public class MainMenuController implements SwitchInListener {

  @FXML private Button playButton;

  @FXML private Label userNameLabel;

  @FXML private Button profilesButton;

  @FXML
  private void onPlay(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.CATEGORY_DISPLAY);
  }

  @FXML
  private void onChooseProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_LIST);
  }

  @FXML
  private void onShowProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_PAGE);
  }

  @Override
  public void onSwitchIn() {
    // if no profile chosen, create a guest user
    if (ProfileHolder.getInstance().getCurrentProfile() == null) {
      try {
        ProfileHolder.getInstance().setCurrentProfile(new Profile("Guest"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      // if logged in as a user, make button say Play instead of Play as guest
      if (!ProfileHolder.getInstance().getCurrentProfile().isGuest()) {
        playButton.setText("Play");
      }
    }
  }
}
