package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

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
   */
  @FXML
  private void onDeleteProfile() {
    // To Do
    System.out.println("delete");
  }
}
