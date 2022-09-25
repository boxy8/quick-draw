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

public class CategorySelector {

  public enum Difficulty {
    E,
    M,
    H
  }

  private Map<Difficulty, List<String>> difficulty2categories;

  public CategorySelector() throws IOException, CsvException, URISyntaxException {
    difficulty2categories = new HashMap<>();
    for (Difficulty difficulty : Difficulty.values()) {
      difficulty2categories.put(difficulty, new ArrayList<>());
    }

    for (String[] line : getLines()) {
      difficulty2categories.get(Difficulty.valueOf(line[1])).add(line[0]);
    }
  }

  /**
   * Find a random word for the player to draw that they haven't encountered
   *
   * @param difficulty
   * @param usedWords
   * @return
   */
  public String getRandomCategory(Difficulty difficulty, List<String> usedWords) {
    // gets a random word
    String temp =
        difficulty2categories
            .get(difficulty)
            .get(new Random().nextInt(difficulty2categories.get(difficulty).size()));
    // stores if the random word has not been encountered
    boolean foundWord = true;
    while (foundWord && (usedWords.size() < 144)) {
      // check if the word has been encountered
      if (!usedWords.contains(temp)) {
        foundWord = false;
        return temp;
      } else {
        // return the word that is found
        temp =
            difficulty2categories
                .get(difficulty)
                .get(new Random().nextInt(difficulty2categories.get(difficulty).size()));
      }
    }
    // if all words used. then return any random word
    return temp;
  }

  protected List<String[]> getLines() throws IOException, CsvException, URISyntaxException {
    File file = new File(CategorySelector.class.getResource("/category_difficulty.csv").toURI());
    try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(fr)) {
      return reader.readAll();
    }
  }
}
