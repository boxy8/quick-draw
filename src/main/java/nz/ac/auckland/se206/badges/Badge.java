package nz.ac.auckland.se206.badges;

public abstract class Badge {

  private final String name;
  private boolean isAchieved;
  private String imageLocation;

  /**
   * Constructs a Badge instance with name and image location
   *
   * @param name the name of the badge
   * @param imageLocation the location to the image (relative to badges folder)
   */
  public Badge(String name, String imageLocation) {
    this.name = name;
    this.imageLocation = imageLocation;
  }

  /** Updates whether the badge is achieved or not based on current profile statistics */
  public abstract void updateBadge();

  public String getName() {
    return name;
  }

  public boolean getIsAchieved() {
    return isAchieved;
  }

  public void setIsAchieved(boolean isAchieved) {
    this.isAchieved = isAchieved;
  }

  public String getImageLocation() {
    return imageLocation;
  }

  public void setImageLocation(String imageLocation) {
    this.imageLocation = imageLocation;
  }
}
