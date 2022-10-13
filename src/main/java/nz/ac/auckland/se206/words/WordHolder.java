package nz.ac.auckland.se206.words;

public class WordHolder {

  private static WordHolder instance;

  /**
   * Either create a new instance or return current instance of the word
   *
   * @return
   */
  public static WordHolder getInstance() {
    if (instance == null) {
      instance = new WordHolder();
    }
    return instance;
  }

  private String currentWord;

  private WordHolder() {}

  /**
   * Set the current word to one that is input
   *
   * @param word
   */
  public void setCurrentWord(String word) {
    this.currentWord = word;
  }

  /**
   * Get the current word that is input
   *
   * @return current word
   */
  public String getCurrentWord() {
    return this.currentWord;
  }
}
