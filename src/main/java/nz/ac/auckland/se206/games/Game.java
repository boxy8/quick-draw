package nz.ac.auckland.se206.games;

import ai.djl.modality.Classifications.Classification;
import java.util.List;
import java.util.Map;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.words.WordHolder;

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
    MASTER,
    SUPER_EASY
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
  private Map<Setting, Difficulty> settings2Difficulty;

  // update during game
  private boolean wasEraserPressed;
  private boolean wasClearPressed;

  // update on game end
  private int duration = 60;
  private boolean isWin = false;
  private int wordPosition;
  private double predictionProbability;

  /**
   * Constructor for a Game. Takes in the word and gamemode.
   *
   * @param word the word to be guessed
   * @param mode the game mode
   */
  public Game(String word, GameMode mode) {
    this.word = word;
    this.mode = mode;
    this.settings2Difficulty =
        ProfileHolder.getInstance().getCurrentProfile().getSetting2Difficulty();
    this.wasEraserPressed = false;
    this.wasClearPressed = false;
  }

  public void setPredictionAttributes(List<Classification> predictions) {
    int count = 0;
    for (Classification classification : predictions) {
      count += 1;
      // get prediction without underscores
      String prediction = classification.getClassName().replace("_", " ");
      // once the game's word prediction is found, update prediction attributes
      if (prediction.equals(WordHolder.getInstance().getCurrentWord())) {
        this.predictionProbability = classification.getProbability();
        this.wordPosition = count;
        return;
      }
    }
  }

  public boolean getWasEraserPressed() {
    return wasEraserPressed;
  }

  public void setWasEraserPressed(boolean wasEraserPressed) {
    this.wasEraserPressed = wasEraserPressed;
  }

  public boolean getWasClearPressed() {
    return wasClearPressed;
  }

  public void setWasClearPressed(boolean wasClearPressed) {
    this.wasClearPressed = wasClearPressed;
  }

  public double getPredictionProbability() {
    return predictionProbability;
  }

  public void setPredictionProbability(double predictionProbability) {
    this.predictionProbability = predictionProbability;
  }

  public int getWordPosition() {
    return wordPosition;
  }

  public void setWordPosition(int wordPosition) {
    this.wordPosition = wordPosition;
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
  public Map<Setting, Difficulty> getSettings2Difficulty() {
    return this.settings2Difficulty;
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
