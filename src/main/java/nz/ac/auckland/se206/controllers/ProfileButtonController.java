package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.profiles.ProfileLoader;

public class ProfileButtonController extends HBox {

  @FXML private ToggleButton selectButton;

  /** Constructs a profile button instance which has text and a delete button */
  public ProfileButtonController() {
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/fxml/" + "profile_button" + ".fxml"));
    loader.setRoot(this);
    loader.setController(this);
    // loading the profile button instance
    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the current button selected's name
   *
   * @return
   */
  public String getToggleText() {
    return selectButton.getText();
  }

  /**
   * set the current toggle button's name
   *
   * @param username
   */
  public void setToggleText(String username) {
    selectButton.setText(username);
  }

  /**
   * set the current selected toggle button to on or off
   *
   * @param bool
   */
  public void setToggleSelected(boolean bool) {
    selectButton.setSelected(bool);
  }

  /**
   * set the current selected button to the toggle group
   *
   * @param group
   */
  public void setToggleGroup(ToggleGroup group) {
    selectButton.setToggleGroup(group);
  }

  /**
   * Deletes the currently selected profile and removes it from the GUI. Warns the user and requires
   * confirmation
   *
   * @throws IOException
   */
  @FXML
  private void onDeleteProfile() throws IOException {
    ToggleButton button = selectButton;
    // make sure that there is a button selected
    if (button != null) {
      Profile selectedProfile = ProfileLoader.read(button.getText());
      selectedProfile.delete();
      deleteProfileLabel(button.getText());
    }
  }

  /**
   * Deletes the profile label in the select profile screen
   *
   * @param username
   * @throws IOException
   */
  private void deleteProfileLabel(String username) throws IOException {
    if (selectButton.getText().equals(username)) {
      System.out.println("SAME");
      // find out of the button is same as selected and set to guest if it is
      if (selectButton
          .getText()
          .equals(ProfileHolder.getInstance().getCurrentProfile().getUsername())) {
        System.out.println("yesmaet");
        ProfileHolder.getInstance().setCurrentProfile(new Profile("Guest"));
        ((MainController) SceneManager.getController(AppUi.MAIN)).setProfileButton();
        // SceneManager.changeScene(null, AppUi.MAIN_MENU);
        ((ProfilePageController) SceneManager.getController(AppUi.PROFILE_PAGE)).setProfileLabel();
      }
    }
    // delete the profile from the screen
    ((ProfileListController) SceneManager.getController(AppUi.PROFILE_LIST))
        .onDeleteProfileButton(this);
  }
}
