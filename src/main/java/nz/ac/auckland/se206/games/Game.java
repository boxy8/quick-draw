package nz.ac.auckland.se206.games;

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

  // set on game start
  private String word;
  private GameMode mode;

  // update on game end
  private int time = 60;
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
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
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

  public String getWord() {
    return word;
  }
}
