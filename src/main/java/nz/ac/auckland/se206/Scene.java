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
   * @param loader
   * @throws IOException
   */
  public Scene(FXMLLoader loader) throws IOException {
    root = loader.load();
    controller = loader.getController();
  }

  /**
   * Get root of the parent
   *
   * @return root
   */
  public Parent getRoot() {
    return root;
  }

  /**
   * Get the current controller
   *
   * @return controller
   */
  public Object getController() {
    return controller;
  }
}
