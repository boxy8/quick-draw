package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.HiddenWordCanvasController;
import nz.ac.auckland.se206.controllers.NormalCanvasController;
import nz.ac.auckland.se206.controllers.ScrambleController;
import nz.ac.auckland.se206.controllers.SwitchInListener;
import nz.ac.auckland.se206.controllers.ZenCanvasController;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

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
   * @throws IOException If "src/main/resources/fxml/[scene].fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    stage.setOnCloseRequest(
        e -> {
          Platform.exit();
          // Terminate TTS so app closes fully on close
          // loading all controllers of pages
          NormalCanvasController normalController =
              (NormalCanvasController) SceneManager.getController(AppUi.NORMAL_CANVAS);
          ZenCanvasController zenController =
              (ZenCanvasController) SceneManager.getController(AppUi.ZEN_CANVAS);
          ScrambleController scrambleController =
              (ScrambleController) SceneManager.getController(AppUi.SCRAMBLE_CANVAS);
          HiddenWordCanvasController hiddenController =
              (HiddenWordCanvasController) SceneManager.getController(AppUi.HIDDEN_CANVAS);
          // check to see if a text to speech is active
          if (normalController.getTextToSpeech() != null) {
            // stop it from running if it is
            normalController.getTextToSpeech().terminate();
          }
          // do the same for zen controller
          if (zenController.getTextToSpeech() != null) {
            zenController.getTextToSpeech().terminate();
          }
          // do the same for scramble controller
          if (scrambleController.getTextToSpeech() != null) {
            scrambleController.getTextToSpeech().terminate();
          }
          // do the same for hidden word controller
          if (hiddenController.getTextToSpeech() != null) {
            hiddenController.getTextToSpeech().terminate();
          }
        });

    // set profile to Guest
    try {
      // create a guest profile
      ProfileHolder.getInstance().setCurrentProfile(new Profile("Guest"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // add scenes to scene manager
    SceneManager.addUi(AppUi.MAIN_MENU, getFxmlLoader("main_menu"));
    SceneManager.addUi(AppUi.MAIN, getFxmlLoader("main"));
    // these views all use the main border pane
    // These are for the different game modes
    SceneManager.addUi(AppUi.NORMAL_CANVAS, getFxmlLoader("normal_canvas"));
    SceneManager.addUi(AppUi.ZEN_CANVAS, getFxmlLoader("zen_canvas"));
    SceneManager.addUi(AppUi.HIDDEN_CANVAS, getFxmlLoader("hidden_word_canvas"));
    SceneManager.addUi(AppUi.SCRAMBLE_CANVAS, getFxmlLoader("scramble_canvas"));
    // rest of the pages
    SceneManager.addUi(AppUi.CATEGORY_DISPLAY, getFxmlLoader("category_display"));
    SceneManager.addUi(AppUi.CATEGORY_DISPLAY_HIDDEN, getFxmlLoader("category_display_hidden"));
    SceneManager.addUi(AppUi.PROFILE_PAGE, getFxmlLoader("profile_page"));
    SceneManager.addUi(AppUi.PROFILE_LIST, getFxmlLoader("profile_list"));
    SceneManager.addUi(AppUi.DIFFICULTY_SELECTOR, getFxmlLoader("difficulty_selector"));
    scene = new Scene(SceneManager.getUiRoot(AppUi.MAIN), 840, 680);
    // scene will always be main scene
    // change views by setting content of main border pane
    ((BorderPane) scene.getRoot()).setCenter(SceneManager.getUiRoot(AppUi.MAIN_MENU));
    Object controller = SceneManager.getController(AppUi.MAIN_MENU);
    if (controller instanceof SwitchInListener switchListener) {
      switchListener.onSwitchIn();
    }
    stage.setScene(scene);
    stage.show();
  }
}
