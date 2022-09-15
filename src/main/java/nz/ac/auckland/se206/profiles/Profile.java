package nz.ac.auckland.se206.profiles;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

  public Profile(String username) throws IOException, CsvException {
    this.username = username.toLowerCase();

    // location of file
    String filePath = "src/main/resources/profiles/" + this.username + ".csv";
    this.csvFile = new File(filePath);

    if (!this.csvFile.isFile()) {
      // create {username}.csv file with default values
      try {
        // create a new csv file writer
        FileWriter outputfile = new FileWriter(this.csvFile);
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
        String[] fastestWinTime = {"fastestWinTime", "60"};
        writer.writeNext(fastestWinTime);
        // closing writer after it is complete
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    readCsv();
  }

  /**
   * reads the .csv file and converts relevant data into the correct data types. Updates the wins,
   * losses, word history, and fastest win time as per the values in the .csv file
   *
   * @throws IOException
   * @throws CsvException
   */
  private void readCsv() throws IOException, CsvException {
    this.csvList = this.getCsvLines();
    this.wins = Integer.valueOf(this.csvList.get(1)[1]);
    this.losses = Integer.valueOf(this.csvList.get(2)[1]);
    this.wordHistory = Arrays.asList(this.csvList.get(3)).subList(1, this.csvList.get(3).length);
    this.fastestWinTime = Integer.valueOf(this.csvList.get(4)[1]);
  }

  /**
   * Reads all lines of a .csv file and returns all the lines as a list of strings
   *
   * @return all lines of the csv file as strings
   * @throws IOException
   * @throws CsvException
   */
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
