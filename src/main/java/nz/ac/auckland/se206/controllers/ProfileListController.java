package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.profiles.ProfileHolder;
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

    @FXML
    private VBox profileCards;

    private String selectedUsername;

    private EventHandler<MouseEvent> selectProfileLabel = event -> {

        Label profileLabel = (Label) event.getSource();
        selectedUsername = profileLabel.getText();
        // Set all profile labels to black
        for (Node child : profileCards.getChildren()) {
            Label childLabel = (Label) child;
            childLabel.setTextFill(Color.BLACK);
        }
        // Set selected profile label to blue
        profileLabel.setTextFill(Color.BLUE);
    };

    public void initialize() {
        // Find all profile files
        String folderPath = "src/main/resources/profiles/";
        File folder = new File(folderPath);
        File[] fileArray = folder.listFiles();

        // Create profile label for each profile file on show it on the GUI
        for (int i = 0; i < fileArray.length; i++) {
            if (fileArray[i].isFile()) {
                String fileName = fileArray[i].getName();
                String username = fileName.substring(0, fileName.length() - 5);
                createProfileLabel(username);
            }
        }
    }

    /**
     * Creates profile label with configured mouse click events and adds it to the
     * GUI
     * 
     * @param username a string of the profile's username
     */
    private void createProfileLabel(String username) {
        Label profileLabel = new Label(username);
        profileLabel.setOnMouseClicked(selectProfileLabel);
        profileCards.getChildren().add(profileLabel);
    }

    /**
     * Sets the current profile based on the current selected profile, and returns
     * to the main menu
     * 
     * @param event the event of activating the Choose Profile Button
     */
    @FXML
    public void onChooseProfile(ActionEvent event) {
        if (selectedUsername != null) {
            ProfileHolder.getInstance().setCurrentProfile(selectedUsername);
            SceneManager.changeScene(event, AppUi.MAIN_MENU);
        }
    }

    /**
     * Adds a profile to the game and shows it on the GUI
     */
    @FXML
    public void onAddProfile() {
        String username = usernameField.getText();
        if (username.length() > 0) {
            ProfileLoader profileLoader = new ProfileLoader(username);
            try {
                // Create valid user profile
                profileLoader.create();
                createProfileLabel(username);
                usernameField.clear();
            } catch (IOException e) {
                usernameField.setText("Invalid Input. Try Again");
            }
        }
    }

    /**
     * Deletes the currently selected profile and removes it from the GUI. Warns the
     * user and requires confirmation
     */
    @FXML
    public void onDeleteProfile() {
        // TODO
        return;
    }
}