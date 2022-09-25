package nz.ac.auckland.se206.controllers;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    // go to canvas view
    SceneManager.changeScene(event, AppUi.CANVAS);
  }

  @Override
  public void onSwitch() {
    WordHolder.getInstance().setCurrentWord(categorySelector.getRandomCategory(Difficulty.E));
    categoryLabel.setText(
        "Draw " + WordHolder.getInstance().getCurrentWord() + " in under a minute");
  }
}
