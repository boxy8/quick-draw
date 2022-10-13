package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.profiles.ProfileLoader;

public class ProfileListController implements SwitchInListener {

  @FXML private TextField usernameField;

  @FXML private Button addButton;

  @FXML private Button deleteButton;

  @FXML private Button chooseButton;

  @FXML private VBox profileContainer;

  @FXML private ToggleGroup profilesGroup = new ToggleGroup();

  private ArrayList<ProfileButtonController> profileButtons = new ArrayList<>();

  /**
   * Runs after GUI is ready from JavaFx and sets the path of the profiles to correct location
   *
   * @throws IOException
   */
  public void initialize() throws IOException {
    // Find all profile files
    String folderPath = "profiles/";
    File folder = new File(folderPath);
    if (!folder.exists()) {
      folder.mkdir();
    }
    File[] fileArray = folder.listFiles(f -> !f.isHidden());

    // Create profile label for each profile file on show it on the GUI
    for (int i = 0; i < fileArray.length; i++) {
      if (fileArray[i].isFile()) {
        String fileName = fileArray[i].getName();
        String username = fileName.substring(0, fileName.length() - 5);
        addProfileButton(username);
      }
    }
  }

  /**
   * Creates profile label with configured mouse click events and adds it to the GUI
   *
   * @param username a string of the profile's user name
   */
  private void addProfileButton(String username) {
    ProfileButtonController profileButton = new ProfileButtonController();
    profileButton.setToggleText(username);
    // add to profile buttons toggle group
    profileButton.setToggleGroup(profilesGroup);
    profileContainer.getChildren().add(profileButton);
    // store button instance in list
    profileButtons.add(profileButton);
  }

  /**
   * Sets the current profile based on the current selected profile, and returns to the main menu
   *
   * @param event the event of activating the Choose Profile Button
   * @throws FileNotFoundException
   */
  @FXML
  private void onConfirmProfile(ActionEvent event) throws FileNotFoundException {
    ToggleButton button = (ToggleButton) profilesGroup.getSelectedToggle();
    // if a profile was selected
    if ((button != null) && (profileContainer.getChildren().size() != 0)) {
      // set to selected profile
      String username = button.getText();
      Profile selectedProfile = ProfileLoader.read(username);
      ProfileHolder.getInstance().setCurrentProfile(selectedProfile);
    } else {
      // set to Guest if nothing is selected
      try {
        ProfileHolder.getInstance().setCurrentProfile(new Profile("Guest"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // update all GUI that requires the username
    ((MainController) SceneManager.getController(AppUi.MAIN)).setProfileButton();
    ((ProfilePageController) SceneManager.getController(AppUi.PROFILE_PAGE)).setProfileLabel();
    ((MainMenuController) SceneManager.getController(AppUi.MAIN_MENU)).setPlayButton();
    // swap scenes
    SceneManager.changeScene(event, AppUi.MAIN_MENU);
    // set difficulty settings on Difficulty Selector GUI
    ((DifficultySelectorController) SceneManager.getController(AppUi.DIFFICULTY_SELECTOR))
        .setSpinners();
  }

  /** Adds a profile to the game and shows it on the GUI */
  @FXML
  private void onAddProfile() {
    String username = usernameField.getText();
    // making sure that a valid username is entered
    if ((username.length() > 0) && !(isDuplicateUsername(username))) {
      try {
        // create a new profile
        Profile newProfile = new Profile(username);
        newProfile.saveToFile();
        addProfileButton(username);
        // empty box ready for another profile
        usernameField.clear();
      } catch (Exception e) {
        usernameField.setText("Try Again");
      }
    }
  }

  /**
   * finds out if there is already a profile with the same name
   *
   * @param username
   * @return boolean that is true if there is or no if there isn't
   */
  private boolean isDuplicateUsername(String username) {
    // check all buttons
    for (ProfileButtonController prof : profileButtons) {
      if (prof.getToggleText().equals(username)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes the box from the profile container so the user can no longer see it
   *
   * @param profileButtonController
   */
  public void onDeleteProfileButton(ProfileButtonController profileButtonController) {
    profileContainer.getChildren().remove(profileButtonController);
    profileButtons.remove(profileButtonController);
  }

  /** Runs when this is switched to, it shows the current profile that is selected */
  @Override
  public void onSwitchIn() {
    // pre-select the currently selected profile
    if (ProfileHolder.getInstance().getCurrentProfile().isGuest()) {
      profilesGroup.selectToggle(null);
    } else {
      String currentUser = ProfileHolder.getInstance().getCurrentProfile().getUsername();
      // select the button that matches current username
      for (ProfileButtonController button : profileButtons) {
        if (button.getToggleText().equals(currentUser)) {
          button.setToggleSelected(true);
        }
      }
    }
  }
}
