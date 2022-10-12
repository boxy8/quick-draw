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
    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getToggleText() {
    return selectButton.getText();
  }

  public void setToggleText(String username) {
    selectButton.setText(username);
  }

  public void setToggleSelected(boolean bool) {
    selectButton.setSelected(bool);
  }

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
    if (button != null) {
      Profile selectedProfile = ProfileLoader.read(button.getText());
      selectedProfile.delete();
      deleteProfileLabel(button.getText());
    }
  }

  private void deleteProfileLabel(String username) throws IOException {
    if (selectButton.getText().equals(username)) {
      if (selectButton
          .getText()
          .equals(ProfileHolder.getInstance().getCurrentProfile().getUsername())) {
        ProfileHolder.getInstance().setInstance();
        ProfileHolder.getInstance();
        System.out.println(ProfileHolder.getInstance());
        ((MainController) SceneManager.getController(AppUi.MAIN)).setProfileButton();
        SceneManager.changeScene(null, AppUi.MAIN_MENU);
        ((ProfilePageController) SceneManager.getController(AppUi.PROFILE_PAGE)).setProfileLabel();
      }
    }
    ((ProfileListController) SceneManager.getController(AppUi.PROFILE_LIST)).onDeleteProf(this);
  }
}
