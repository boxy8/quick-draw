package nz.ac.auckland.se206.controllers;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.CategorySelector.Difficulty;
import nz.ac.auckland.se206.words.WordHolder;

public class CategoryDisplayController implements SwitchListener {

  @FXML
  private Label categoryLabel;

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
    // get a new word that hasn't been used
    WordHolder.getInstance()
        .setCurrentWord(
            categorySelector.getRandomCategory(
                Difficulty.E, ProfileHolder.getInstance().getCurrentProfile().getWordHistory()));
    // update the text label for the game
    categoryLabel.setText(WordHolder.getInstance().getCurrentWord());
  }
}
