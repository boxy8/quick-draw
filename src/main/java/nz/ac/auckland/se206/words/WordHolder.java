package nz.ac.auckland.se206.words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordHolder {

  private static WordHolder instance;

  /**
   * Either create a new instance or return current instance of the word
   *
   * @return the current word the user has gotten
   */
  public static WordHolder getInstance() {
    if (instance == null) {
      instance = new WordHolder();
    }
    return instance;
  }

  private String currentWord;
  private String scrambledWord;

  private WordHolder() {}

  /**
   * Set the current word to one that is input
   *
   * @param word the word you wish to set and give to user
   */
  public void setCurrentWord(String word) {
    this.currentWord = word;
    this.scrambledWord = scrambleWord(word);
  }

  /**
   * scrambles the word for use in the scramble game mode
   *
   * @param word word to be scrambled
   * @return scrambled word that can be displayed
   */
  private String scrambleWord(String word) {
    // convert to char array
    char[] characterArray = word.toCharArray();
    List<Character> randomisedCharacters = new ArrayList<Character>(characterArray.length);
    // loop through list and add to array list
    for (int i = 0; i < characterArray.length; i++) {
      randomisedCharacters.add(characterArray[i]);
    }
    // randomise
    Collections.shuffle(randomisedCharacters);
    StringBuilder stringBuilder = new StringBuilder();
    // put them back together
    for (int i = 0; i < randomisedCharacters.size(); i++) {
      stringBuilder.append(randomisedCharacters.get(i));
    }
    // return the scrambled word
    return stringBuilder.toString();
  }

  /**
   * Get the current word that is input
   *
   * @return current word that is loaded
   */
  public String getCurrentWord() {
    return this.currentWord;
  }

  /**
   * Get the scrambled word that is input
   *
   * @return current word that is scrambled loaded
   */
  public String getScrambledWord() {
    return this.scrambledWord;
  }
}
