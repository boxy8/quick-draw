package nz.ac.auckland.se206.profiles;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

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
			this.wins = 0;
			this.losses = 0;
			this.fastestWinTime = 60;
			try {
				rewriteCSV();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		readCsv();
	}

	/**
	 * reads the .csv file and converts relevant data into the correct data types.
	 * Updates the wins, losses, word history, and fastest win time as per the
	 * values in the .csv file
	 *
	 * @throws IOException
	 * @throws CsvException
	 */
	void readCsv() throws IOException, CsvException {
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

	public void rewriteCSV() throws IOException {
		FileWriter outputfile = new FileWriter(this.csvFile);
		CSVWriter writer = new CSVWriter(outputfile);
		// Creating the default beginning file
		String[] name = { "Name", username };
		writer.writeNext(name);
		String[] win = { "Win", this.wins + "" };
		writer.writeNext(win);
		String[] loss = { "loss", this.losses + "" };
		writer.writeNext(loss);
		// THIS NEEDS TO BE CHANGED FOR THE WORDS LIST
		String[] words = { "word" };
		writer.writeNext(words);
		String[] fastestWinTime = { "fastestWinTime", this.fastestWinTime + "" };
		writer.writeNext(fastestWinTime);
		// closing writer after it is complete
		writer.close();
	}

	public void incrementWins() throws IOException {
		// TODO: Write to CSV file
		this.wins += 1;
		rewriteCSV();
	}

	public void incrementLosses() throws IOException {
		// TODO: Write to CSV file
		this.losses += 1;
		rewriteCSV();
	}

	public void addToHistory(String word) throws IOException {
		// TODO: Write to CSV file
		this.wordHistory.add(word);
	}

	public void setfastestWinTime(int time) throws IOException {
		// TODO: Write to CSV file
		this.fastestWinTime = time;
		rewriteCSV();
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
