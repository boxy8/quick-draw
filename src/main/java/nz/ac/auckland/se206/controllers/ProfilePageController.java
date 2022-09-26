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

public class ProfilePageController implements SwitchListener {

  @FXML
  private Label finishedGamesLabel;

  @FXML
  private Label gamesWonLabel;

  @FXML
  private Label gamesLostLabel;

  @FXML
  private Label fastestGameLabel;

  @FXML
  private Label averageGameLabel;

  @FXML
  private PieChart gamesPie;

  @FXML
  private TableView<Game> table;

  @FXML
  private TableColumn<Game, String> wordCol;
  @FXML
  private TableColumn<Game, GameMode> modeCol;
  @FXML
  private TableColumn<Game, Integer> lengthCol;
  @FXML
  private TableColumn<Game, Boolean> wonCol;

  public void initialize() {
    // initialise table columns
    wordCol.setCellValueFactory(new PropertyValueFactory<Game, String>("word"));
    modeCol.setCellValueFactory(new PropertyValueFactory<Game, GameMode>("mode"));
    lengthCol.setCellValueFactory(new PropertyValueFactory<Game, Integer>("time"));
    wonCol.setCellValueFactory(new PropertyValueFactory<Game, Boolean>("isWin"));
  }

  @Override
  public void onSwitch() {
    Profile profile = ProfileHolder.getInstance().getCurrentProfile();
    // TODO User section (name, winstreak, badges)

    // populate statistics section with profile values

    int wins = profile.getWins();
    int losses = profile.getLosses();
    int totalGames = wins + losses;

    // statistics section
    finishedGamesLabel.setText("Finished games: " + totalGames);
    gamesWonLabel.setText("Games won: " + wins);
    gamesLostLabel.setText("Games lost: " + losses);
    fastestGameLabel.setText("Fastest time: " + profile.getFastestWinTime() + " secs");
    averageGameLabel.setText("Average game length: " + profile.getAverageTime() + " secs");

    // pie chart
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("Wins", wins), new PieChart.Data("Losses", losses));
    gamesPie.setData(pieChartData);

    // game history table
    List<Game> gameHistory = profile.getReversedGameHistory();
    table.setItems(FXCollections.observableArrayList(gameHistory));
  }
}
