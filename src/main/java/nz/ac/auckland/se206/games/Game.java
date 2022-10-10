package nz.ac.auckland.se206.games;

import java.util.Map;

import nz.ac.auckland.se206.profiles.ProfileHolder;

public class Game {
  public enum GameMode {
    NORMAL,
    ZEN
  }

  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD,
    MASTER
  }

  public enum Setting {
    ACCURACY,
    WORDS,
    TIME,
    CONFIDENCE,
  }

  // set on game start
  private String word;
  private GameMode mode;
  private Map<Setting, Difficulty> settings;

  // update on game end
  private int duration = 60;
  private boolean isWin = false;

  /**
   * Constructor for a Game. Takes in the word and gamemode.
   *
   * @param word the word to be guessed
   * @param mode the game mode
   */
  public Game(String word, GameMode mode) {
    this.word = word;
    this.mode = mode;
    this.settings = ProfileHolder.getInstance().getCurrentProfile().getSetting2Difficulty();
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int time) {
    this.duration = time;
  }

  public boolean getIsWin() {
    return isWin;
  }

  public void setIsWin(boolean isWin) {
    this.isWin = isWin;
  }

  public GameMode getMode() {
    return mode;
  }

  public Map<Setting, Difficulty> getSettings() {
    return this.settings;
  }

  public String getWord() {
    return word;
  }
}
