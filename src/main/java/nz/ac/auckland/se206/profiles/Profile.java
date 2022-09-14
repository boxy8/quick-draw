package nz.ac.auckland.se206.profiles;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class Profile {

  private String username;
  private File csvFile;

  public Profile(String username) throws URISyntaxException, IOException, CsvException {
    this.username = username.toLowerCase();

    // location of file
    String filePath = "./" + this.username + ".csv";
    this.csvFile = new File(filePath);

    // checking to see if this file exists already indicating that there is already
    // a profile of this name
    if (this.csvFile.isFile()) {
      // TODO: read file
    } else {
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
        String[] gameWinTime = {"gameWinTime", "50"};
        writer.writeNext(gameWinTime);
        // closing writer after it is complete
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
