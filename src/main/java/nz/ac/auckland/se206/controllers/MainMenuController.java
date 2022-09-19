package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class MainMenuController {

  @FXML
  private Button playButton;

  @FXML
  private Label titleLabel;

  @FXML
  private Button profileButton;

  @FXML
  private void onPlay(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.CATEGORY_DISPLAY);
  }

  @FXML
  void onShowProfile(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.PROFILE_PAGE);
  }
}
