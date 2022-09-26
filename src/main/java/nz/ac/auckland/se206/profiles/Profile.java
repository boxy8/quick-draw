package nz.ac.auckland.se206.profiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.auckland.se206.games.Game;

public class Profile {
  private String username;
  private int wins;
  private int losses;
  private int winStreak;
  private int fastestWinTime = 60;
  private List<String> wordHistory = new ArrayList<String>();
  private List<Game> gameHistory = new ArrayList<Game>();

  public Profile(String username) throws IOException {
    this.username = username;
    // setting file path for profile under the profile directory in src folder
    String filePath = "profiles/" + username + ".json";
    File f = new File(filePath);
    if (f.exists()) {
      // get all required information
      this.wins = ProfileLoader.read(username).getWins();
      this.losses = ProfileLoader.read(username).getLosses();
      this.winStreak = ProfileLoader.read(username).getWinStreak();
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

  public int getLosses() {
    return losses;
  }

  public int getWinStreak() {
    return winStreak;
  }

  public int getFastestWinTime() {
    return this.fastestWinTime;
  }

  public int getAverageTime() {
    if (gameHistory.size() == 0) {
      return 60; // default value
    }

    int sum = 0;
    // sum up game times
    for (Game game : gameHistory) {
      sum += game.getDuration();
    }
    // calculate average
    return Math.round(sum / gameHistory.size());
  }

  public List<String> getWordHistory() {
    return wordHistory;
  }

  public List<Game> getGameHistory() {
    return gameHistory;
  }

  public List<Game> getReversedGameHistory() {
    List<Game> reversed = new ArrayList<>(gameHistory);
    Collections.reverse(reversed);
    return reversed;
  }

  public void saveToFile() throws IOException {
    ProfileLoader.updateJson(this);
  }

  public boolean isGuest() {
    return username.equals("Guest");
  }

  /**
   * Updates all profile statistics based on a new game result.
   *
   * @param game a finished game
   */
  public void updateAllStats(Game game) {
    // update wins/losses and win streak
    if (game.getIsWin()) {
      wins++;
      winStreak++;
    } else {
      losses++;
      winStreak = 0;
    }
    // update fastest wintime
    if (game.getDuration() < fastestWinTime) {
      fastestWinTime = game.getDuration();
    }
    // update word history
    wordHistory.add(game.getWord());
    // update game history
    gameHistory.add(game);
  }
}
