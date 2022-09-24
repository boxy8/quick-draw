package nz.ac.auckland.se206.profiles;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se206.games.Game;

public class Profile {
  private String username;
  private int wins;
  private int losses;
  private int fastestWinTime;
  private List<String> wordHistory = new ArrayList<>();
  private List<Game> gameHistory = new ArrayList<>();

  // Calling getters and setters
  public String getUsername() {
    return username;
  }

  public void setName(String usernameEntered) {
    this.username = usernameEntered;
  }

  public int getWins() {
    return wins;
  }

  public void setWins() {
    this.wins++;
  }

  public int getLosses() {
    return this.losses;
  }

  public void setLosses() {
    this.losses++;
  }

  public void setDefault(String name) {
    this.username = name;
    this.wins = 0;
    this.losses = 0;
    this.fastestWinTime = 60;
  }

  public int getFastestWinTime() {
    return this.fastestWinTime;
  }

  public void setFastestWinTime(int time) {
    this.fastestWinTime = time;
  }

  public List<String> getWordHistory() {
    return wordHistory;
  }

  public boolean containsWord(String word) {
    if (wordHistory.contains(word)) {
      return true;
    } else {
      return false;
    }
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
