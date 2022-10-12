package nz.ac.auckland.se206.controllers;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.games.Game.Difficulty;
import nz.ac.auckland.se206.games.Game.Setting;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.WordHolder;

public class DifficultySelectorController implements Initializable {
  @FXML private Spinner<String> accuracySpinner;
  @FXML private Spinner<String> wordsSpinner;
  @FXML private Spinner<String> timeSpinner;
  @FXML private Spinner<String> confidenceSpinner;
  @FXML private Button chooseDifficultyButton;
  private CategorySelector categorySelector;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    try {
      categorySelector = new CategorySelector();
    } catch (IOException | CsvException | URISyntaxException e) {
      e.printStackTrace();
    }

    // initialise spinners with "Easy", "Medium", and "Hard" difficulties
    ObservableList<String> accuracyDifficulties =
        FXCollections.observableArrayList("EASY", "MEDIUM", "HARD");
    initializeSpinner(accuracySpinner, Setting.ACCURACY, accuracyDifficulties);

    // initialise spinners with "Easy", "Medium", "Hard", and "Master" difficulties
    ObservableList<String> difficulties =
        FXCollections.observableArrayList("EASY", "MEDIUM", "HARD", "MASTER");
    initializeSpinner(wordsSpinner, Setting.WORDS, difficulties);
    initializeSpinner(timeSpinner, Setting.TIME, difficulties);
    initializeSpinner(confidenceSpinner, Setting.CONFIDENCE, difficulties);
  }

  /**
   * Initializes a spinner with relevant difficulties for user selection
   *
   * @param spinner a spinner on the GUI that the user uses to select the relevant difficulty
   * @param difficulties all available difficulty strings for the setting
   */
  private void initializeSpinner(
      Spinner<String> spinner, Setting setting, ObservableList<String> difficulties) {
    SpinnerValueFactory<String> valueFactory =
        new SpinnerValueFactory.ListSpinnerValueFactory<String>(difficulties);
    valueFactory.setValue("EASY");
    spinner.setValueFactory(valueFactory);
  }

  /**
   * Sets the users chosen difficulty settings Triggers when ChooseDifficultyButton is pressed
   *
   * @param event the event of triggering this method
   */
  @FXML
  private void onChooseDifficulty(ActionEvent event) {
    setAccuracyDifficulty();
    setWordsDifficulty();
    setTimeDifficulty();
    setConfidenceDifficulty();

    SceneManager.changeScene(event, AppUi.CATEGORY_DISPLAY);
  }

  /** Sets the next game's accuracy cut off as per the difficulty selected by accuracy spinner */
  private void setAccuracyDifficulty() {
    // Easy difficulty selected
    if (accuracySpinner.getValue().equals("EASY")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.ACCURACY, Difficulty.EASY);
    }
    // Medium difficulty selected
    else if (accuracySpinner.getValue().equals("MEDIUM")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.ACCURACY, Difficulty.MEDIUM);
    }
    // Hard difficulty selected
    else {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.ACCURACY, Difficulty.HARD);
    }
  }

  /** Sets the next game's word as per the difficulty selected by words spinner */
  private void setWordsDifficulty() {
    // Easy difficulty selected
    if (wordsSpinner.getValue().equals("EASY")) {
      WordHolder.getInstance().setCurrentWord(categorySelector.getEasyCategory());
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.WORDS, Difficulty.EASY);
    }
    // Medium difficulty selected
    else if (wordsSpinner.getValue().equals("MEDIUM")) {
      WordHolder.getInstance().setCurrentWord(categorySelector.getMediumCategory());
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.WORDS, Difficulty.MEDIUM);
    }
    // Hard difficulty selected
    else if (wordsSpinner.getValue().equals("HARD")) {
      WordHolder.getInstance().setCurrentWord(categorySelector.getHardCategory());
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.WORDS, Difficulty.HARD);
    }
    // Master difficulty selected
    else {
      WordHolder.getInstance().setCurrentWord(categorySelector.getMasterCategory());
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.WORDS, Difficulty.MASTER);
    }
  }

  /** Sets the time as per the difficulty selected by time spinner */
  private void setTimeDifficulty() {
    // Easy difficulty selected
    if (timeSpinner.getValue().equals("EASY")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.TIME, Difficulty.EASY);
    }
    // Medium difficulty selected
    else if (timeSpinner.getValue().equals("MEDIUM")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.TIME, Difficulty.MEDIUM);
    }
    // Hard difficulty selected
    else if (timeSpinner.getValue().equals("HARD")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.TIME, Difficulty.HARD);
    } else {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.TIME, Difficulty.MASTER);
    }
  }

  /**
   * Sets the next game's confidence cut off as per the difficulty selected by confidence spinner
   */
  private void setConfidenceDifficulty() {
    // Easy difficulty selected
    if (confidenceSpinner.getValue().equals("EASY")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.CONFIDENCE, Difficulty.EASY);
    }
    // Medium difficulty selected
    else if (confidenceSpinner.getValue().equals("MEDIUM")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.CONFIDENCE, Difficulty.MEDIUM);
    }
    // Hard difficulty selected
    else if (confidenceSpinner.getValue().equals("HARD")) {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.CONFIDENCE, Difficulty.HARD);
    } else {
      ProfileHolder.getInstance()
          .getCurrentProfile()
          .getSetting2Difficulty()
          .put(Setting.CONFIDENCE, Difficulty.MASTER);
    }
  }

  /** Updates the spinner value being displayed as per the profiles previous selection */
  public void setSpinners() {

    Map<Setting, Difficulty> map =
        ProfileHolder.getInstance().getCurrentProfile().getSetting2Difficulty();
    if (map.get(Setting.ACCURACY) != null) {
      accuracySpinner.getValueFactory().setValue(map.get(Setting.ACCURACY).toString());
      wordsSpinner.getValueFactory().setValue(map.get(Setting.WORDS).toString());
      timeSpinner.getValueFactory().setValue(map.get(Setting.TIME).toString());
      confidenceSpinner.getValueFactory().setValue(map.get(Setting.CONFIDENCE).toString());
    }
  }
}
