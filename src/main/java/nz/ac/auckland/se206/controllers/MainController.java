package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class MainController {

  @FXML private BorderPane border;

  public void setContent(AppUi appUi) {
    Object controller = SceneManager.getController(appUi);
    if (controller instanceof SwitchListener switchListener) {
      switchListener.onSwitch();
    }

    // switch main content
    border.setCenter(SceneManager.getUiRoot(appUi));
  }
}
