package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;

public class MainController {

  @FXML private BorderPane border;

  @FXML private Button home;

  @FXML private Button profileButton;

  @FXML private ImageView soundIcon;

  private Image mutedIcon = new Image("/images/music_muted.png");
  private Image unmutedIcon = new Image("/images/music.png");

  /** Sets the profile Button the current user that is selected */
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

  @FXML
  private void onMute() {
    SoundEffects.muteToggle();
    // if muted after toggling set muted icon, otherwise set unmuted icon
    if (SoundEffects.getIsMute()) {
      soundIcon.setImage(mutedIcon);
    } else {
      soundIcon.setImage(unmutedIcon);
    }
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
