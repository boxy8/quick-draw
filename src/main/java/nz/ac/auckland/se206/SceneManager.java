package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import nz.ac.auckland.se206.controllers.SwitchInListener;
import nz.ac.auckland.se206.controllers.SwitchOutListener;

public class SceneManager {
  public enum AppUi {
    NORMAL_CANVAS,
    ZEN_CANVAS,
    HIDDEN_CANVAS,
    SCRAMBLE_CANVAS,
    MAIN_MENU,
    CATEGORY_DISPLAY,
    PROFILE_PAGE,
    PROFILE_LIST,
    MAIN,
    DIFFICULTY_SELECTOR
  }

  private static HashMap<AppUi, Scene> sceneMap = new HashMap<>();

  /**
   * Generate and store all scenes required so they don't have to be reloaded every time
   *
   * @param appUi the ui being used
   * @param loader the fxml loader
   * @throws IOException
   */
  public static void addUi(AppUi appUi, FXMLLoader loader) throws IOException {
    Scene newScene = new Scene(loader);
    newScene.getRoot().setUserData(appUi); // node stores what AppUi type it is
    sceneMap.put(appUi, newScene);
  }

  /**
   * Get the parent UI of the scene
   *
   * @param appUi the ui of the scene
   * @return root of the UI
   */
  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi).getRoot();
  }

  /**
   * Get the controller of the scene
   *
   * @param appUi the ui of the scene
   * @return controller of scene
   */
  public static Object getController(AppUi appUi) {
    return sceneMap.get(appUi).getController();
  }

  /**
   * Change scene from currently available scenes
   *
   * @param event the triggering event
   * @param appUi the ui being switched to
   */
  public static void changeScene(ActionEvent event, AppUi appUi) {
    // call switch in method if applicable
    Object newController = SceneManager.getController(appUi);
    if (newController instanceof SwitchInListener switchInListener) {
      switchInListener.onSwitchIn();
    }
    Button button = (Button) event.getSource();
    javafx.scene.Scene scene = button.getScene();
    // get the node of main
    BorderPane mainBorder = ((BorderPane) scene.getRoot());
    // call switch out method if applicable
    Object currentController =
        SceneManager.getController((AppUi) mainBorder.getCenter().getUserData());
    if (currentController instanceof SwitchOutListener switchOutListener) {
      switchOutListener.onSwitchOut();
    }
    // switch views (i.e. center content)
    mainBorder.setCenter(SceneManager.getUiRoot(appUi));
  }
}
