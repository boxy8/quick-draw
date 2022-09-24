package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class ProfilePageController implements SwitchListener {

  @FXML private Button quickDrawButton;

  @FXML
  public void onGoHome(ActionEvent event) {
    SceneManager.changeScene(event, AppUi.MAIN_MENU);
  }

  @Override
  public void onSwitch() {
    // populate statistics section

  }
}
