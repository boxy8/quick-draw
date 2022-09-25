package nz.ac.auckland.se206.profiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Profile {
  private String username;
  private int wins;
  private int losses;
  private List<String> wordHistory;
  private int fastestWinTime;

  public Profile(String username) throws IOException {
    this(username, false);
  }

  public Profile(String username, boolean isDefault) throws IOException {
    this.username = username;
    if (isDefault) {
      this.wins = 0;
      this.losses = 0;
      this.wordHistory = new ArrayList<String>();
      this.fastestWinTime = 60;
      ProfileLoader.updateJSON(this);
    } else {
      this.wins = ProfileLoader.read(username).getWins();
      this.losses = ProfileLoader.read(username).getLosses();
      this.wordHistory = ProfileLoader.read(username).getHistory();
      this.fastestWinTime = ProfileLoader.read(username).getFastestWinTime();
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

  public List<String> getHistory() {
    return wordHistory;
  }

  public void addToList(String something) throws IOException {
    wordHistory.add(something);
    ProfileLoader.updateJSON(this);
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
    return "{\"username\":\"" + username + "\",\"wins\":" + wins + ",\"losses\":" + losses
        + ",\"wordHistory\":" + wordHistory + ",\"fastestWinTime\":" + fastestWinTime + "}";
  }
}
