package nz.ac.auckland.se206.profiles;

import java.util.ArrayList;

public class Profile {

    private String username;
    private int wins;
    private int losses;
    private ArrayList<String> wordHistory;
    private int fastestWonGame;

    public Profile(String username) {
        this.username = username;
        // TODO: if {username}.csv exists
        this.wins = readCsvInt(1);
        this.losses = readCsvInt(2);
        this.wordHistory = readCsvArrayList(3);
        this.fastestWonGame = readCsvInt(4);

        // else
        // TODO: create {username}.csv file with default values
        this.wins = 0;
        this.losses = 0;
        this.fastestWonGame = 60; // IDK what to do for this
    }

    private int readCsvInt(int line) {
        // TODO: read CSV file
        return -1;
    }

    private ArrayList<String> readCsvArrayList(int line) {
        // TODO: read CSV file
        return null;
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

    public String getUsername() {
        return this.username;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses;
    }

    public ArrayList<String> getWordHistory() {
        return this.wordHistory;
    }

    public int getFastestWonGame() {
        return this.fastestWonGame;
    }

}
