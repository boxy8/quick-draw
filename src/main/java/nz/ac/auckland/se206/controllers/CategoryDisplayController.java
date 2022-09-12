package nz.ac.auckland.se206.controllers;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.CategorySelector.Difficulty;
import nz.ac.auckland.se206.words.WordHolder;

public class CategoryDisplayController implements SwitchListener {

  @FXML private Label categoryLabel;

  private CategorySelector categorySelector;

  public void initialize() throws IOException, CsvException, URISyntaxException {
    categorySelector = new CategorySelector();
  }

  @FXML
  private void onStart(ActionEvent event) {

    Object controller = SceneManager.getController(AppUi.CANVAS);
    if (controller instanceof SwitchListener switchListener) {
      switchListener.onSwitch();
    }

    // switch view
    Button button = (Button) event.getSource();
    Scene scene = button.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.CANVAS));
  }

  @Override
  public void onSwitch() {
    WordHolder.getInstance().setCurrentWord(categorySelector.getRandomCategory(Difficulty.E));
    categoryLabel.setText(
        "Draw " + WordHolder.getInstance().getCurrentWord() + " in under a minute");
  }
}
