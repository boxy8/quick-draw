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

public class CategoryDisplayController implements SwitchInListener {

  @FXML private Label categoryLabel;

  private CategorySelector categorySelector;

  /**
   * Creates a new category selector for user later in the code
   *
   * @throws IOException
   * @throws CsvException
   * @throws URISyntaxException
   */
  public void initialize() throws IOException, CsvException, URISyntaxException {
    categorySelector = new CategorySelector();
  }

  /**
   * Changes from category display to the canvas when user wants to start
   *
   * @param event
   */
  @FXML
  private void onStart(ActionEvent event) {
    // go to canvas view
    SceneManager.changeScene(event, AppUi.NORMAL_CANVAS);
  }

  /** Resets the screen when it is switched to so that words can be updated and gotten */
  @Override
  public void onSwitchIn() {
    // get a new word that hasn't been used
    WordHolder.getInstance()
        .setCurrentWord(
            categorySelector.getRandomCategory(
                Difficulty.E, ProfileHolder.getInstance().getCurrentProfile().getWordHistory()));
    // update the text label for the game
    categoryLabel.setText(WordHolder.getInstance().getCurrentWord());
  }
}
