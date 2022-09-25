package nz.ac.auckland.se206.profiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.auckland.se206.games.Game;

public class Profile {
  private String username;
  private int wins;
  private int losses;
  private int fastestWinTime = 60;
  private List<String> wordHistory = new ArrayList<String>();
  private List<Game> gameHistory = new ArrayList<Game>();

  public Profile(String username) throws IOException {
    this(username, false);
  }

  public Profile(String username, boolean isDefault) throws IOException {
    this.username = username;
    if (isDefault) {
      // create a new JSON with default values
      ProfileLoader.updateJSON(this);
    } else {
      // read in from existing JSON
      this.wins = ProfileLoader.read(username).getWins();
      this.losses = ProfileLoader.read(username).getLosses();
      this.fastestWinTime = ProfileLoader.read(username).getFastestWinTime();
      this.wordHistory = ProfileLoader.read(username).getWordHistory();
      this.gameHistory = ProfileLoader.read(username).getGameHistory();
    }
  }

  // Calling getters and setters
  public String getUsername() {
    return username;
  }

  public int getWins() {
    return wins;
  }

  public void incrementWins() throws IOException {
    this.wins++;
    ProfileLoader.updateJSON(this);
  }

  public int getLosses() {
    return this.losses;
  }

  public void incrementLosses() throws IOException {
    this.losses++;
    ProfileLoader.updateJSON(this);
  }

  public int getFastestWinTime() {
    return this.fastestWinTime;
  }

  public void setFastestWinTime(int time) throws IOException {
    this.fastestWinTime = time;
    ProfileLoader.updateJSON(this);
  }

  public int getAverageTime() {
    if (gameHistory.size() == 0) {
      return 60; // default value
    }

    int sum = 0;
    // sum up game times
    for (Game game : gameHistory) {
      sum += game.getTime();
    }
    // calculate average
    return Math.round(sum / gameHistory.size());
  }

  public List<String> getWordHistory() {
    return wordHistory;
  }

  public void addToWordHistory(String word) throws IOException {
    wordHistory.add(word);
    ProfileLoader.updateJSON(this);
  }

  // Creating toString
  @Override
  public String toString() {
    return "{\"username\":\""
        + username
        + "\",\"wins\":"
        + wins
        + ",\"losses\":"
        + losses
        + ",\"wordHistory\":"
        + wordHistory
        + ",\"fastestWinTime\":"
        + fastestWinTime
        + "}";
  }

  public List<Game> getGameHistory() {
    return gameHistory;
  }

  public List<Game> getReversedGameHistory() {
    List<Game> reversed = new ArrayList<>(gameHistory);
    Collections.reverse(reversed);
    return reversed;
  }

  public void addToGameHistory(Game game) throws IOException {
    gameHistory.add(game);
    ProfileLoader.updateJSON(this);
  }
}
