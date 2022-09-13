package nz.ac.auckland.se206.profiles;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Profile {

  private String username;
  private int wins;
  private int losses;
  private List<String> wordHistory;
  private int fastestWonGame;

  private File csvFile;
  private List<String[]> csvList;

  public Profile(String username) throws URISyntaxException, IOException, CsvException {
    this.username = username.toLowerCase();
    this.csvFile =
        new File(Profile.class.getResource("/profiles/" + this.username + ".csv").toURI());

    if (this.csvFile.isFile()) {
      this.csvList = this.getCsvLines();
      this.wins = Integer.valueOf(this.csvList.get(1)[1]);
      this.losses = Integer.valueOf(this.csvList.get(2)[1]);
      this.wordHistory = Arrays.asList(this.csvList.get(3)).subList(1, this.csvList.get(3).length);
      this.fastestWonGame = Integer.valueOf(this.csvList.get(2)[1]);

    } else {
      // TODO: create {username}.csv file with default values

      // this.csvList = this.getCsvLines();
      // this.wins = 0;
      // this.losses = 0;
      // this.wordHistory = new ArrayList<String>();
      // this.fastestWonGame = 60;
      // };
    }
  }

  private List<String[]> getCsvLines() throws IOException, CsvException {
    try (FileReader fr = new FileReader(this.csvFile, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(fr)) {
      return reader.readAll();
    }
  }

  public void incrementWins() {
    // TODO: Write to CSV file
    this.wins += 1;
  }

  public void incrementLosses() {
    // TODO: Write to CSV file
    this.losses += 1;
  }

  public void addToHistory(String word) {
    // TODO: Write to CSV file
    this.wordHistory.add(word);
  }

  public void setFastestWonGame(int time) {
    // TODO: Write to CSV file
    this.fastestWonGame = time;
  }

  public List<String[]> getCsvList() {
    return csvList;
  }

  public String getUsername() {
    return this.username;
  }

  public int getWins() {
    return this.wins;
  }

  public int getLosses() {
    return this.losses;
  }

  public List<String> getWordHistory() {
    return this.wordHistory;
  }

  public int getFastestWonGame() {
    return this.fastestWonGame;
  }
}
