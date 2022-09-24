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
  private int time = 60 * 1000; // ms
  private boolean isWin;

  public Game(String word, GameMode mode) {
    this.word = word;
    this.mode = mode;
  }

  public void setGameResult(boolean isWin, int time) {
    this.isWin = isWin;
    if (isWin) {
      this.time = time;
    }
  }
}
