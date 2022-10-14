package nz.ac.auckland.se206.controllers;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.games.Game.GameMode;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class ProfilePageController implements SwitchInListener {

  @FXML private Label finishedGamesLabel;

  @FXML private Label gamesWonLabel;

  @FXML private Label gamesLostLabel;

  @FXML private Label fastestGameLabel;

  @FXML private Label averageGameLabel;

  @FXML private PieChart gamesPie;

  @FXML private TableView<Game> table;

  @FXML private Label usernameLabel;

  @FXML private Label winStreak;

  @FXML private TableColumn<Game, String> wordCol;
  @FXML private TableColumn<Game, GameMode> modeCol;
  @FXML private TableColumn<Game, Integer> lengthCol;
  @FXML private TableColumn<Game, Boolean> wonCol;

  /** Runs when GUI is finished loading from JavaFX, it gets the table colums ready */
  public void initialize() {
    // Initialize table columns
    wordCol.setCellValueFactory(new PropertyValueFactory<Game, String>("word"));
    modeCol.setCellValueFactory(new PropertyValueFactory<Game, GameMode>("mode"));
    lengthCol.setCellValueFactory(new PropertyValueFactory<Game, Integer>("duration"));
    wonCol.setCellValueFactory(new PropertyValueFactory<Game, Boolean>("isWin"));
  }

  /** sets the label of the profile to the current username of the profile */
  public void setProfileLabel() {
    usernameLabel.setText(ProfileHolder.getInstance().getCurrentProfile().getUsername());
  }

  /** Runs when this page is switched to, It get populates the screen to show user statistics */
  @Override
  public void onSwitchIn() {

    setProfileLabel();

    Profile profile = ProfileHolder.getInstance().getCurrentProfile();
    // populate statistics section with profile values

    int wins = profile.getWins();
    int losses = profile.getLosses();
    int totalGames = wins + losses + profile.getZenGamesPlayed();

    // statistics section
    winStreak.setText(String.valueOf(profile.getWinStreak()));
    finishedGamesLabel.setText("Finished games: " + totalGames);
    gamesWonLabel.setText("Games won: " + wins);
    gamesLostLabel.setText("Games lost: " + losses);
    fastestGameLabel.setText("Fastest time: " + profile.getFastestWinTime() + " secs");
    averageGameLabel.setText("Average game length: " + profile.getAverageTime() + " secs");

    // pie chart
    ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
            new PieChart.Data("Wins", wins), new PieChart.Data("Losses", losses));
    gamesPie.setData(pieChartData);

    // game history table
    List<Game> gameHistory = profile.getReversedGameHistory();
    table.setItems(FXCollections.observableArrayList(gameHistory));
  }
}
