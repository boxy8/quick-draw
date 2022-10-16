package nz.ac.auckland.se206.controllers;

import java.io.FileNotFoundException;
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
    // loading the profile button
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profile_button.fxml"));
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
   * @return the selected buttons user name
   */
  public String getToggleText() {
    return selectButton.getText();
  }

  /**
   * set the current toggle button's name
   *
   * @param username the username of the button you wish to change
   */
  public void setToggleText(String username) {
    selectButton.setText(username);
  }

  /**
   * set the current selected toggle button to on or off
   *
   * @param bool turn the buttons toggle on or off
   */
  public void setToggleSelected(boolean bool) {
    selectButton.setSelected(bool);
  }

  /**
   * set the current selected button to the toggle group
   *
   * @param group the group of toggle buttons
   */
  public void setToggleGroup(ToggleGroup group) {
    selectButton.setToggleGroup(group);
  }

  /**
   * Updates the badges container in profile list view based on selected (not yet confirmed) profile
   *
   * @throws FileNotFoundException
   */
  @FXML
  private void onSelectProfile() throws FileNotFoundException {
    Profile selectedProfile = ProfileLoader.read(selectButton.getText());
    ((ProfileListController) SceneManager.getController(AppUi.PROFILE_LIST))
        .updateBadgesContainer(selectedProfile);
  }

  /**
   * Deletes the currently selected profile and removes it from the GUI. Warns the user and requires
   * confirmation
   *
   * @throws IOException where it was unable to read the user profile
   */
  @FXML
  private void onDeleteProfile() throws IOException {
    // make sure that there is a button selected
    if (selectButton != null) {
      Profile selectedProfile = ProfileLoader.read(selectButton.getText());
      selectedProfile.delete();
      deleteProfileLabel(selectButton.getText());
    }
  }

  /**
   * Deletes the profile label in the select profile screen
   *
   * @param username the user name of the user you wish to delete
   * @throws IOException when it is unable to read/write to the profile
   */
  private void deleteProfileLabel(String username) throws IOException {
    if (selectButton.getText().equals(username)) {
      // find out of the button is same as selected and set to guest if it is
      if (selectButton
          .getText()
          .equals(ProfileHolder.getInstance().getCurrentProfile().getUsername())) {
        ProfileHolder.getInstance().setCurrentProfile(new Profile("Guest"));
        selectButton.getToggleGroup().selectToggle(null);
        ((MainController) SceneManager.getController(AppUi.MAIN)).setProfileButton();
      }
    }
    // delete the profile from the screen
    ((ProfileListController) SceneManager.getController(AppUi.PROFILE_LIST))
        .onDeleteProfileButton(this);
  }
}
