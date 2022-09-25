package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.CanvasController;
import nz.ac.auckland.se206.controllers.SwitchListener;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  public static void main(final String[] args) {
    launch();
  }

  /**
   * Returns an FXMLLoader instance for the input file. The method expects that the file is located
   * in "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return An FXMLLoader for the input file.
   */
  private static FXMLLoader getFxmlLoader(final String fxml) {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
  }

  private Scene scene;

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    stage.setOnCloseRequest(
        e -> {
          Platform.exit();
          // Terminate TTS so app closes fully on close
          CanvasController controller = (CanvasController) SceneManager.getController(AppUi.CANVAS);
          if (controller.getTTS() != null) {
            controller.getTTS().terminate();
          }
        });

    // add scenes to scene manager
    SceneManager.addUi(AppUi.MAIN_MENU, getFxmlLoader("main_menu"));

    SceneManager.addUi(AppUi.MAIN, getFxmlLoader("main"));
    // these views all use the main border pane
    SceneManager.addUi(AppUi.CANVAS, getFxmlLoader("canvas"));
    SceneManager.addUi(AppUi.CATEGORY_DISPLAY, getFxmlLoader("category_display"));
    SceneManager.addUi(AppUi.PROFILE_PAGE, getFxmlLoader("profile_page"));
    SceneManager.addUi(AppUi.PROFILE_LIST, getFxmlLoader("profile_list"));

    scene = new Scene(SceneManager.getUiRoot(AppUi.MAIN), 840, 680);
    // scene will always be main scene
    // change views by setting content of main border pane
    ((BorderPane) scene.getRoot()).setCenter(SceneManager.getUiRoot(AppUi.MAIN_MENU));
    Object controller = SceneManager.getController(AppUi.MAIN_MENU);
    if (controller instanceof SwitchListener switchListener) {
      switchListener.onSwitch();
    }
    stage.setScene(scene);
    stage.show();
  }
}
