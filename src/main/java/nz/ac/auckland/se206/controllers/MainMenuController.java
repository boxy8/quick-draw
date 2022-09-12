package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class MainMenuController {

  @FXML private Button playButton;

  @FXML private Label titleLabel;

  @FXML
  private void onPlay(ActionEvent event) {

    Object controller = SceneManager.getController(AppUi.CATEGORY_DISPLAY);
    if (controller instanceof SwitchListener switchListener) {
      switchListener.onSwitch();
    }

    // switch views
    Button button = (Button) event.getSource();
    Scene scene = button.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.CATEGORY_DISPLAY));
  }
}
