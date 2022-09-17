package nz.ac.auckland.se206.profiles;

public class Profile {
  private String username;
  private int wins;
  private int losses;
  private String[] wordHistory;
  private int fastestWinTime;


  // Calling getters and setters
  public String getUsername() {
    return username;
  }

  public void setName(String usernameEntered) {
    this.username = usernameEntered;
  }

  public int getWins() {
    return wins;
  }

  public void setWins() {
    this.wins++;
  }

  public int getLosses() {
    return this.losses;
  }

  public void setLosses() {
    this.losses++;
  }

  public void setDefault(String name) {
    this.username = name;
    this.wins = 0;
    this.losses = 0;
    this.fastestWinTime = 60;
  }

  public int getFastestWinTime() {
    return this.fastestWinTime;
  }

  public void setFastestWinTime(int time) {
    this.fastestWinTime = time;
  }


  // Creating toString
  @Override
  public String toString() {
    return "Organisation [username=" + username + ", wins=" + wins + ", losses=" + losses
        + ", fastestWinTime=" + fastestWinTime + "]";
  }
}
