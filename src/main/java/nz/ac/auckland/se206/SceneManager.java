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
    MAIN_MENU,
    CATEGORY_DISPLAY,
    PROFILE_PAGE,
    PROFILE_LIST,
    MAIN
  }

  private static HashMap<AppUi, Scene> sceneMap = new HashMap<>();

  public static void addUi(AppUi appUi, FXMLLoader loader) throws IOException {
    Scene newScene = new Scene(loader);
    newScene.getRoot().setUserData(appUi); // node stores what AppUi type it is
    sceneMap.put(appUi, newScene);
  }

  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi).getRoot();
  }

  public static Object getController(AppUi appUi) {
    return sceneMap.get(appUi).getController();
  }

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
