package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Scene {
  private Parent root;
  private Object controller;

  /**
   * Constructor for creating a new scene
   *
   * @param loader the fxml loader is called
   * @throws IOException when the file is unable to be loaded
   */
  public Scene(FXMLLoader loader) throws IOException {
    root = loader.load();
    controller = loader.getController();
  }

  /**
   * Get root of the parent required
   *
   * @return root the root of the scene
   */
  public Parent getRoot() {
    return root;
  }

  /**
   * Get the current controller that is being used
   *
   * @return controller the controller of the scene
   */
  public Object getController() {
    return controller;
  }
}
