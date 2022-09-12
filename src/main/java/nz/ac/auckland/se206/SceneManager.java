package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SceneManager {
  public enum AppUi {
    CANVAS,
    MAIN_MENU,
    CATEGORY_DISPLAY
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
}
