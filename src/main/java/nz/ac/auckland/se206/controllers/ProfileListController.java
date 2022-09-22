package nz.ac.auckland.se206.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.text.TextStringBuilder;

import javafx.event.ActionEvent;
import javafx.event.Event;
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

    private EventHandler<MouseEvent> selectProfileLabel = event -> {
        Label profileLabel = (Label) event.getSource();
        for (Node child : profileCards.getChildren()) {
            Label childLabel = (Label) child;
            childLabel.setTextFill(Color.BLACK);
        }

        profileLabel.setTextFill(Color.BLUE);
    };

    public void initialize() {
        String folderPath = "src/main/resources/profiles/";
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String username = listOfFiles[i].getName();
                createProfileLabel(username);
            }
        }
    }

    public void createProfileLabel(String username) {
        Label profileLabel = new Label(username);
        profileLabel.setOnMouseClicked(selectProfileLabel);
        profileCards.getChildren().add(profileLabel);
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
                createProfileLabel(username);
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
