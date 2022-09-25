package nz.ac.auckland.se206.profiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se206.games.Game;

public class Profile {
  private String username;
  private int wins;
  private int losses;
  private int fastestWinTime;
  private List<String> wordHistory;
  private List<Game> gameHistory;

  public Profile(String username) throws IOException {
    this.username = username;
    this.wins = 0;
    this.losses = 0;
    this.wordHistory = new ArrayList<String>();
    this.gameHistory = new ArrayList<>();
    this.fastestWinTime = 60;
    ProfileLoader.updateJSON(this);
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
}
