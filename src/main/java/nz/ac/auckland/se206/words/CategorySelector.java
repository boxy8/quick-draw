package nz.ac.auckland.se206.words;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class CategorySelector {

  public enum WordDifficulty {
    E,
    M,
    H
  }

  private Map<WordDifficulty, List<String>> difficulty2categories;

  /**
   * Creates a new category selector so that you can get random words from categories that you want
   *
   * @throws IOException if there is an error reading or writing files
   * @throws CsvException if there is an error in the csv file
   * @throws URISyntaxException if there is an error in the file path
   */
  public CategorySelector() throws IOException, CsvException, URISyntaxException {
    difficulty2categories = new HashMap<>();
    // saving in hash map for better performance
    for (WordDifficulty difficulty : WordDifficulty.values()) {
      difficulty2categories.put(difficulty, new ArrayList<>());
    }
    for (String[] line : getLines()) {
      difficulty2categories.get(WordDifficulty.valueOf(line[1])).add(line[0]);
    }
  }

  /**
   * Find a random word for the player to draw that they haven't encountered already
   *
   * @param availableWords list of available words given the difficulty selection (Easy, Medium,
   *     Hard, Master)
   * @return the random category for next game
   */
  private String getRandomCategory(List<String> availableWords) {
    // grab all used words
    Set<String> usedWords = ProfileHolder.getInstance().getCurrentProfile().getWordHistory();
    // get random number
    int randIndex = new Random().nextInt(availableWords.size());
    while (availableWords.size() > 0) {
      // check if it has been used or not
      if (usedWords.contains(availableWords.get(randIndex))) {
        availableWords.remove(randIndex);
        randIndex = new Random().nextInt(availableWords.size());
      } else {
        // get a word that has been found suitable
        return availableWords.get(randIndex);
      }
    }
    // get a word that has been found suitable
    return availableWords.get(randIndex);
  }

  /**
   * Gets a random word for when the Words Difficulty is Easy (E)
   *
   * @return random Easy word to draw
   */
  public String getEasyCategory() {
    return getRandomCategory(difficulty2categories.get(WordDifficulty.E));
  }

  /**
   * Gets a random word for when the Words Difficulty is Medium (E, M)
   *
   * @return random Medium word to draw
   */
  public String getMediumCategory() {
    WordDifficulty[] difficulties = {WordDifficulty.E, WordDifficulty.M};
    return getRandomCategory(getAvailableWords(difficulties));
  }

  /**
   * Gets a random word for when the Words Difficulty is Hard (E, M, H)
   *
   * @return random Hard word to draw
   */
  public String getHardCategory() {
    WordDifficulty[] difficulties = {WordDifficulty.E, WordDifficulty.M, WordDifficulty.H};
    return getRandomCategory(getAvailableWords(difficulties));
  }

  /**
   * Gets a random word for when the Words Difficulty is Master (H)
   *
   * @return random Master word to draw
   */
  public String getMasterCategory() {
    return getRandomCategory(difficulty2categories.get(WordDifficulty.H));
  }

  /**
   * Gets a random word for when the Words Difficulty is Easy (E) without spaces
   *
   * @return random Easy word to draw without spaces
   */
  public String getEasyCategorySingleWord() {
    String wordWithoutSpace = getRandomCategory(difficulty2categories.get(WordDifficulty.E));
    while (wordWithoutSpace.contains(" ")) {
      wordWithoutSpace = getRandomCategory(difficulty2categories.get(WordDifficulty.E));
    }
    return wordWithoutSpace;
  }

  /**
   * Gets a random word for when the Words Difficulty is Medium (E, M) without spaces
   *
   * @return random Medium word to draw without spaces
   */
  public String getMediumCategorySingleWord() {
    WordDifficulty[] difficulties = {WordDifficulty.E, WordDifficulty.M};
    String wordWithoutSpace = getRandomCategory(getAvailableWords(difficulties));
    while (wordWithoutSpace.contains(" ")) {
      wordWithoutSpace = getRandomCategory(getAvailableWords(difficulties));
    }
    return wordWithoutSpace;
  }

  /**
   * Gets a random word for when the Words Difficulty is Hard (E, M, H) without spaces
   *
   * @return random Hard word to draw without spaces
   */
  public String getHardCategorySingleWord() {
    WordDifficulty[] difficulties = {WordDifficulty.E, WordDifficulty.M, WordDifficulty.H};
    String wordWithoutSpace = getRandomCategory(getAvailableWords(difficulties));
    while (wordWithoutSpace.contains(" ")) {
      wordWithoutSpace = getRandomCategory(getAvailableWords(difficulties));
    }
    return wordWithoutSpace;
  }

  /**
   * Gets a random word for when the Words Difficulty is Master (H) without spaces
   *
   * @return random Master word to draw without spaces
   */
  public String getMasterCategorySingleWord() {
    String wordWithoutSpace = getRandomCategory(difficulty2categories.get(WordDifficulty.H));
    while (wordWithoutSpace.contains(" ")) {
      wordWithoutSpace = getRandomCategory(difficulty2categories.get(WordDifficulty.H));
    }
    return wordWithoutSpace;
  }

  /**
   * Gets all words of the difficulties (E, M, H) specified without spaces
   *
   * @param difficulties the difficulties (E, M, H) which the list of words is wanted for
   * @return all words tagged as the difficulties specified
   */
  private List<String> getAvailableWords(WordDifficulty[] difficulties) {
    ArrayList<String> availableWords = new ArrayList<>();
    for (WordDifficulty difficulty : difficulties) {
      availableWords.addAll(difficulty2categories.get(difficulty));
    }
    return availableWords;
  }

  /**
   * gets all line from the csv file so that the categories can be separated
   *
   * @return list of a list of strings containing all differnt words form csv
   * @throws IOException if file cannot be read
   * @throws CsvException if the file is not working with opencsv
   * @throws URISyntaxException if given the wrong file path
   */
  protected List<String[]> getLines() throws IOException, CsvException, URISyntaxException {
    // get the csv file
    File file = new File(CategorySelector.class.getResource("/category_difficulty.csv").toURI());
    // read all lines
    try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(fr)) {
      return reader.readAll();
    }
  }
}
