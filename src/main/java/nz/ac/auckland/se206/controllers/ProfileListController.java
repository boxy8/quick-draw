package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.ProfileLoader;

public class ProfileListController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button chooseButton;

    public void initialize() {
        String filePath = "src/main/resources/profiles/";
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String username = "File " + listOfFiles[i].getName();

            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    @FXML
    public void onChooseProfile(ActionEvent event) {
        SceneManager.changeScene(event, AppUi.MAIN_MENU);
    }

    @FXML
    public void onAddProfile() {
        String username = usernameField.getText();
        if (username.length() > 0) {
            ProfileLoader profileLoader = new ProfileLoader(username);
            try {
                profileLoader.create();
                usernameField.clear();
            } catch (IOException e) {
                usernameField.setText("Invalid Input. Try Again");
            }
        }
    }

    @FXML
    public void onDeleteProfile() {
        // TODO
        return;
    }
}
