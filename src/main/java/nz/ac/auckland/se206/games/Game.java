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

  // set on game end
  private int time;
  private boolean isWin;

  public Game(String word, GameMode mode) {
    this.word = word;
    this.mode = mode;
  }
}
