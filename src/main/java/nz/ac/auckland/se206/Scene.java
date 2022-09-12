package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Scene {
  private Parent root;
  private Object controller;

  public Scene(FXMLLoader loader) throws IOException {
    root = loader.load();
    controller = loader.getController();
  }

  public Parent getRoot() {
    return root;
  }

  public Object getController() {
    return controller;
  }
}
