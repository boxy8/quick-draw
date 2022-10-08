package nz.ac.auckland.se206.words;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import nz.ac.auckland.se206.profiles.ProfileHolder;

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

public class CategorySelector {

  public enum Difficulty {
    E,
    M,
    H
  }

  private Map<Difficulty, List<String>> difficulty2categories;

  public CategorySelector() throws IOException, CsvException, URISyntaxException {
    difficulty2categories = new HashMap<>();
    // saving in hash map for better performance
    for (Difficulty difficulty : Difficulty.values()) {
      difficulty2categories.put(difficulty, new ArrayList<>());
    }

    for (String[] line : getLines()) {
      difficulty2categories.get(Difficulty.valueOf(line[1])).add(line[0]);
    }
  }

  /**
   * Find a random word for the player to draw that they haven't encountered
   * already
   * 
   * @param availableWords list of available words given the difficulty selection
   *                       (Easy, Medium, Hard, Master)
   * @return the random category for next game
   */
  private String getRandomCategory(List<String> availableWords) {
    List<String> usedWords = ProfileHolder.getInstance().getCurrentProfile().getWordHistory();
    int randIndex = new Random().nextInt(availableWords.size());
    while (availableWords.size() > 0) {
      if (usedWords.contains(availableWords.get(randIndex))) {
        availableWords.remove(randIndex);
        randIndex = new Random().nextInt(availableWords.size());
      } else {
        return availableWords.get(randIndex);
      }
    }
    return availableWords.get(randIndex);

  }

  /**
   * Gets a random word for when the Words Difficulty is Easy (E)
   * 
   * @return random Easy word to draw
   */
  public String getEasyCategory() {
    return getRandomCategory(difficulty2categories.get(Difficulty.E));
  }

  /**
   * Gets a random word for when the Words Difficulty is Medium (E, M)
   * 
   * @return random Medium word to draw
   */
  public String getMediumCategory() {
    Difficulty[] difficulties = { Difficulty.E, Difficulty.M };
    return getRandomCategory(getAvailableWords(difficulties));
  }

  /**
   * Gets a random word for when the Words Difficulty is Hard (E, M, H)
   * 
   * @return random Hard word to draw
   */
  public String getHardCategory() {
    Difficulty[] difficulties = { Difficulty.E, Difficulty.M, Difficulty.H };
    return getRandomCategory(getAvailableWords(difficulties));
  }

  /**
   * Gets a random word for when the Words Difficulty is Master (H)
   * 
   * @return random Master word to draw
   */
  public String getMasterCategory() {
    return getRandomCategory(difficulty2categories.get(Difficulty.H));
  }

  /**
   * Gets all words of the difficulties (E, M, H) specified
   * 
   * @param difficulties the difficulties (E, M, H) which the list of words is
   *                     wanted for
   * @return all words tagged as the difficulties specified
   */
  private List<String> getAvailableWords(Difficulty[] difficulties) {
    ArrayList<String> availableWords = new ArrayList<>();
    for (Difficulty difficulty : difficulties) {
      availableWords.addAll(difficulty2categories.get(difficulty));
    }
    return availableWords;
  }

  /**
   * gets all line from the csv file so that the categories can be separated
   *
   * @return
   * @throws IOException
   * @throws CsvException
   * @throws URISyntaxException
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
