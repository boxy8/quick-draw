package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.controllers.SwitchListener;

public class SceneManager {
  public enum AppUi {
    CANVAS,
    MAIN_MENU,
    CATEGORY_DISPLAY,
    PROFILE_PAGE
  }

  private static HashMap<AppUi, Scene> sceneMap = new HashMap<>();

  public static void addUi(AppUi appUi, FXMLLoader loader) throws IOException {
    sceneMap.put(appUi, new Scene(loader));
  }

  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi).getRoot();
  }

  public static Object getController(AppUi appUi) {
    return sceneMap.get(appUi).getController();
  }

  public static void changeScene(ActionEvent event, AppUi appUi) {
    Object controller = SceneManager.getController(appUi);
    if (controller instanceof SwitchListener switchListener) {
      switchListener.onSwitch();
    }

    // switch views
    Button button = (Button) event.getSource();
    javafx.scene.Scene scene = button.getScene();
    scene.setRoot(SceneManager.getUiRoot(appUi));
  }
}
