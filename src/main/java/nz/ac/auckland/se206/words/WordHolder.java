package nz.ac.auckland.se206.words;

public class WordHolder {

  private static WordHolder instance;

  public static WordHolder getInstance() {
    if (instance == null) {
      instance = new WordHolder();
    }
    return instance;
  }

  private String currentWord;

  private WordHolder() {}

  public void setCurrentWord(String word) {
    this.currentWord = word;
  }

  public String getCurrentWord() {
    return this.currentWord;
  }
}
