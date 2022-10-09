package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

  private ArrayList<ToggleButton> profileButtons = new ArrayList<>();

  //  private EventHandler<ActionEvent> selectProfile =
  //      event -> {
  //        ToggleButton profileButton = (ToggleButton) event.getSource();
  //        selectedUsername = profileButton.getText();
  //      };

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
   * @param username a string of the profile's username
   */
  private void addProfileButton(String username) {
    ToggleButton profileButton = new ToggleButton(username);
    // add to profile buttons toggle group
    profileButton.setToggleGroup(profilesGroup);
    // make sure current clicked label is selected
    //    profileButton.setOnAction(selectProfile);
    profileButton.getStyleClass().add("profile-button");
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
    if (button != null) {
      String username = button.getText();
      Profile selectedProfile = ProfileLoader.read(username);
      ProfileHolder.getInstance().setCurrentProfile(selectedProfile);
      // set program wide profile for the user
      ((MainController) SceneManager.getController(AppUi.MAIN)).setProfileButton();
      SceneManager.changeScene(event, AppUi.MAIN_MENU);
      ((ProfilePageController) SceneManager.getController(AppUi.PROFILE_PAGE)).setProfileLabel();
    }
  }

  /** Adds a profile to the game and shows it on the GUI */
  @FXML
  private void onAddProfile() {
    String username = usernameField.getText();
    // making sure that a valid username is entered
    if (username.length() > 0) {
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
   * Deletes the currently selected profile and removes it from the GUI. Warns the user and requires
   * confirmation
   */
  @FXML
  private void onDeleteProfile() {}

  @Override
  public void onSwitchIn() {
    // pre-select the currently selected profile
    if (ProfileHolder.getInstance().getCurrentProfile().isGuest()) {
      profilesGroup.selectToggle(null);
    } else {
      String currentUser = ProfileHolder.getInstance().getCurrentProfile().getUsername();
      // select the button that matches current username
      for (ToggleButton button : profileButtons) {
        if (button.getText().equals(currentUser)) {
          button.setSelected(true);
        }
      }
    }
  }
}
