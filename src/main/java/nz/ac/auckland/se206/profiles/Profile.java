package nz.ac.auckland.se206.profiles;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
  private int fastestWinTime;

  private File csvFile;
  private List<String[]> csvList;

  public Profile(String username) throws URISyntaxException, IOException, CsvException {
    this.username = username.toLowerCase();

    // location of file
    String filePath = "./" + this.username + ".csv";
    File csvNewFile = new File(filePath);

    this.csvFile =
        new File(Profile.class.getResource("/profiles/" + this.username + ".csv").toURI());

    if (this.csvFile.isFile()) {
      this.csvList = this.getCsvLines();
      this.wins = Integer.valueOf(this.csvList.get(1)[1]);
      this.losses = Integer.valueOf(this.csvList.get(2)[1]);
      this.wordHistory = Arrays.asList(this.csvList.get(3)).subList(1, this.csvList.get(3).length);
      this.fastestWinTime = Integer.valueOf(this.csvList.get(2)[1]);

    } else {
      // create {username}.csv file with default values
      try {
        // create a new csv file writer
        FileWriter outputfile = new FileWriter(csvNewFile);
        CSVWriter writer = new CSVWriter(outputfile);
        // Creating the default beginning file
        String[] name = {"Name", username};
        writer.writeNext(name);
        String[] win = {"Win", "0"};
        writer.writeNext(win);
        String[] loss = {"loss", "0"};
        writer.writeNext(loss);
        String[] words = {"words"};
        writer.writeNext(words);
        String[] fastestWinTime = {"fastestWinTime", "50"};
        writer.writeNext(fastestWinTime);
        // closing writer after it is complete
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
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

  public void setfastestWinTime(int time) {
    // TODO: Write to CSV file
    this.fastestWinTime = time;
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

  public int getfastestWinTime() {
    return this.fastestWinTime;
  }
}
