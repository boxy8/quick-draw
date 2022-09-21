package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class ProfileListController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button chooseButton;

    @FXML
    public void onChooseProfile(ActionEvent event) {
        SceneManager.changeScene(event, AppUi.MAIN_MENU);
    }

    @FXML
    public void onAddProfile() {
        // TODO
        return;
    }

    @FXML
    public void onDeleteProfile() {
        // TODO
        return;
    }
}
