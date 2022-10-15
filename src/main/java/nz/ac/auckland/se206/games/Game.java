package nz.ac.auckland.se206.games;

import java.util.Map;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class Game {

  public enum GameMode {
    NORMAL,
    ZEN,
    HIDDEN,
    SCRAMBLE
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

  /**
   * Get the duration that the game was played
   *
   * @return duration game is played
   */
  public int getDuration() {
    return duration;
  }

  /**
   * set the duration the game has been played for
   *
   * @param time the time the game has been played for
   */
  public void setDuration(int time) {
    this.duration = time;
  }

  /**
   * get if the game was a winning one or a loosing one
   *
   * @return if user won or not
   */
  public boolean getIsWin() {
    return isWin;
  }

  /**
   * set the outcome of the game to won or lost
   *
   * @param isWin if the user has won or not
   */
  public void setIsWin(boolean isWin) {
    this.isWin = isWin;
  }

  /**
   * Gets the game mode that the game was played in
   *
   * @return game mode the game was played in
   */
  public GameMode getMode() {
    return mode;
  }

  /**
   * gets the settings that were used for the game
   *
   * @return the settings the game was played in
   */
  public Map<Setting, Difficulty> getSettings() {
    return this.settings;
  }

  /**
   * gets the word that was played in the game
   *
   * @return word that was played
   */
  public String getWord() {
    return word;
  }
}
