package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.profiles.ProfileLoader;

public class ProfileListController {

  @FXML
  private TextField usernameField;

  @FXML
  private Button addButton;

  @FXML
  private Button deleteButton;

  @FXML
  private Button chooseButton;

  @FXML
  private VBox profileCards;

  private String selectedUsername;

  private ArrayList<Profile> profiles;

  private EventHandler<MouseEvent> selectProfileLabel = event -> {
    Label profileLabel = (Label) event.getSource();
    selectedUsername = profileLabel.getText();
    // Set all profile labels to black
    for (Node child : profileCards.getChildren()) {
      Label childLabel = (Label) child;
      if (childLabel.getStyleClass().contains("selectedProfile")) {
        childLabel.getStyleClass().remove("selectedProfile");
      }
    }
    // Set selected profile label to blue
    for (Profile profile : profiles) {
      if (profile.getUsername().equals(selectedUsername)) {
        profileLabel.getStyleClass().add("selectedProfile");
      }
    }
  };

  public void initialize() throws IOException {
    profiles = new ArrayList<Profile>();

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
        profiles.add(new Profile(username));
        createProfileLabel(username);
      }
    }
  }

  /**
   * Creates profile label with configured mouse click events and adds it to the
   * GUI
   *
   * @param username a string of the profile's username
   */
  private void createProfileLabel(String username) {
    Label profileLabel = new Label(username);
    // make sure current clicked label is selected
    profileLabel.setOnMouseClicked(selectProfileLabel);
    profileLabel.getStyleClass().add("profileLabel");
    profileCards.getChildren().add(profileLabel);
  }

  /**
   * Sets the current profile based on the current selected profile, and returns
   * to the main menu
   *
   * @param event the event of activating the Choose Profile Button
   * @throws FileNotFoundException
   */
  @FXML
  private void onChooseProfile(ActionEvent event) throws FileNotFoundException {
    // check for empty user name
    if (selectedUsername != null) {
      // get user entered user name
      Profile selectedProfile = ProfileLoader.read(selectedUsername);
      ProfileHolder.getInstance().setCurrentProfile(selectedProfile);

      // set program wide profile for the user
      ((MainController) SceneManager.getController(AppUi.MAIN)).setProfileButton();
      SceneManager.changeScene(event, AppUi.MAIN_MENU);
      ((ProfilePageController) SceneManager.getController(AppUi.PROFILE_PAGE)).setProfileLabel();

      // set difficulty settings on Difficulty Selector GUI
      ((DifficultySelectorController) SceneManager.getController(AppUi.DIFFICULTY_SELECTOR)).setSpinners();
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
        // add to list of profiles
        profiles.add(newProfile);
        createProfileLabel(username);
        // empty box ready for another profile
        usernameField.clear();
      } catch (Exception e) {
        usernameField.setText("Try Again");
      }
    }
  }

  /**
   * Deletes the currently selected profile and removes it from the GUI. Warns the
   * user and requires
   * confirmation
   */
  @FXML
  private void onDeleteProfile() {
  }
}
