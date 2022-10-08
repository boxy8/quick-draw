package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.opencsv.exceptions.CsvException;

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
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.WordHolder;

public class DifficultySelectorController implements Initializable {
    @FXML
    private Spinner<String> accuracySpinner;
    @FXML
    private Spinner<String> wordsSpinner;
    @FXML
    private Spinner<String> timeSpinner;
    @FXML
    private Spinner<String> confidenceSpinner;
    @FXML
    private Button chooseDifficultyButton;
    private CategorySelector categorySelector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            categorySelector = new CategorySelector();
        } catch (IOException | CsvException | URISyntaxException e) {
            e.printStackTrace();
        }

        // initialise spinners with "Easy", "Medium", and "Hard" difficulties
        ObservableList<String> accuracyDifficulties = FXCollections.observableArrayList("Easy", "Medium", "Hard");
        initializeSpinner(accuracySpinner, accuracyDifficulties);

        // initialise spinners with "Easy", "Medium", "Hard", and "Master" difficulties
        ObservableList<String> difficulties = FXCollections.observableArrayList("Easy", "Medium", "Hard", "Master");
        initializeSpinner(wordsSpinner, difficulties);
        initializeSpinner(timeSpinner, difficulties);
        initializeSpinner(confidenceSpinner, difficulties);

    }

    /**
     * Initializes a spinner with relevant difficulties for user selection
     * 
     * @param spinner      a spinner on the GUI that the user uses to select the
     *                     relevant difficulty
     * @param difficulties all available difficulty strings for the setting
     */
    private void initializeSpinner(Spinner<String> spinner, ObservableList<String> difficulties) {
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                difficulties);
        valueFactory.setValue("Easy");
        spinner.setValueFactory(valueFactory);
    }

    /**
     * Sets the users chosen difficulty settings
     * Triggers when ChooseDifficultyButton is pressed
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

    private void setAccuracyDifficulty() {
        // TODO
        return;
    }

    /**
     * Sets the next game's word as per the difficulty selected by words spinner
     */
    private void setWordsDifficulty() {
        // Easy difficulty selected
        if (wordsSpinner.getValue().equals("Easy")) {
            WordHolder.getInstance().setCurrentWord(categorySelector.getEasyCategory());

        }
        // Medium difficulty selected
        else if (wordsSpinner.getValue().equals("Medium")) {
            WordHolder.getInstance().setCurrentWord(categorySelector.getMediumCategory());

        }
        // Hard difficulty selected
        else if (wordsSpinner.getValue().equals("Hard")) {
            WordHolder.getInstance().setCurrentWord(categorySelector.getHardCategory());

        }
        // Master difficulty selected
        else {
            WordHolder.getInstance().setCurrentWord(categorySelector.getMasterCategory());
        }
    }

    private void setTimeDifficulty() {
        // TODO
        return;
    }

    private void setConfidenceDifficulty() {
        // TODO
        return;
    }

}
