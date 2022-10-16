package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.badges.Badge;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.profiles.ProfileLoader;

public class ProfileListController implements SwitchInListener {

  @FXML private TextField usernameField;

  @FXML private Button addButton;

  @FXML private Button deleteButton;

  @FXML private Button chooseButton;

  @FXML private VBox profileContainer;

  @FXML private FlowPane badgesContainer;

  @FXML private ToggleGroup profilesGroup = new ToggleGroup();

  private ArrayList<ProfileButtonController> profileButtons = new ArrayList<>();

  /**
   * Runs after GUI is ready from JavaFx and sets the path of the profiles to correct location
   *
   * @throws IOException if it is unable to read or write to file
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

    // Initialize badges
    Profile initialProfile = ProfileHolder.getInstance().getCurrentProfile();
    for (Badge badge : initialProfile.getBadges()) {
      badgesContainer.getChildren().add(new BadgeController(badge));
    }
  }

  /**
   * Updates the badges container GUI based on a profile
   *
   * @param profile the profile that the badges will be shown from
   */
  protected void updateBadgesContainer(Profile profile) {
    // Using badge from profile, update each badge controller
    for (Badge badge : profile.getBadges()) {
      for (Node controller : badgesContainer.getChildren()) {
        // if the badge type matches, update the controller
        if (badge.getClass() == ((BadgeController) controller).getBadge().getClass()) {
          ((BadgeController) controller).setBadge(badge);
        }
      }
    }
    ;
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
   * @throws FileNotFoundException was unable to find the file specified
   */
  @FXML
  private void onConfirmProfile(ActionEvent event) throws FileNotFoundException {
    ToggleButton button = (ToggleButton) profilesGroup.getSelectedToggle();
    // if a profile was selected
    if (button != null) {
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
    // update GUI for main that requires the user name
    ((MainController) SceneManager.getController(AppUi.MAIN)).setProfileButton();
    // swap scenes
    SceneManager.changeScene(event, AppUi.MAIN_MENU);
  }

  /** Adds a profile to the game and shows it on the GUI */
  @FXML
  private void onAddProfile() {
    String username = usernameField.getText();
    // making sure that a valid user name is entered
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
        e.printStackTrace();
      }
    }
  }

  /**
   * finds out if there is already a profile with the same name
   *
   * @param username the user name you wish to check if it is duplicate or not
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
   * @param profileButtonController the controller that storing all the buttons
   */
  public void onDeleteProfileButton(ProfileButtonController profileButtonController) {
    profileContainer.getChildren().remove(profileButtonController);
    profileButtons.remove(profileButtonController);
  }

  /** Runs when this is switched to, it shows the current profile that is selected */
  @Override
  public void onSwitchIn() {
    Profile currentProfile = ProfileHolder.getInstance().getCurrentProfile();
    // pre-select the currently selected profile
    if (currentProfile.isGuest()) {
      profilesGroup.selectToggle(null);
    } else {
      String currentUser = currentProfile.getUsername();
      // select the button that matches current username
      for (ProfileButtonController button : profileButtons) {
        if (button.getToggleText().equals(currentUser)) {
          button.setToggleSelected(true);
        }
      }
    }
    // update the badges for the player
    updateBadgesContainer(currentProfile);
  }
}
