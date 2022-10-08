package nz.ac.auckland.se206.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> accuracyDifficulties = FXCollections.observableArrayList("Easy", "Medium", "Hard");
        initializeSpinner(accuracySpinner, accuracyDifficulties);

        ObservableList<String> difficulties = FXCollections.observableArrayList("Easy", "Medium", "Hard", "Master");
        initializeSpinner(wordsSpinner, difficulties);
        initializeSpinner(timeSpinner, difficulties);
        initializeSpinner(confidenceSpinner, difficulties);

    }

    private void initializeSpinner(Spinner<String> spinner, ObservableList<String> difficulties) {
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                difficulties);

        valueFactory.setValue("Easy");
        spinner.setValueFactory(valueFactory);
    }

    @FXML
    private void onChooseDifficulty(ActionEvent event) {
        // TODO
        return;
    }

}
