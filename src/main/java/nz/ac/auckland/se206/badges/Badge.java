package nz.ac.auckland.se206.badges;

public abstract class Badge {

  private final String name;
  private boolean isAchieved;
  private String imageLocation;
  private String tooltip;

  /**
   * Constructs a Badge instance with name and image location
   *
   * @param name the name of the badge
   * @param imageLocation the location to the image (relative to badges folder)
   * @param tooltip the tooltip description for the image
   */
  public Badge(String name, String imageLocation, String tooltip) {
    this.name = name;
    this.imageLocation = imageLocation;
    this.tooltip = tooltip;
  }

  /** Updates whether the badge is achieved or not based on current profile statistics */
  public abstract void updateBadge();

  /**
   * Gets the name of the badge required
   *
   * @return the name of the badge
   */
  public String getName() {
    return name;
  }

  /**
   * Gets if the badge has been achieved or not
   *
   * @return if the badge has been achieved or not
   */
  public boolean getIsAchieved() {
    return isAchieved;
  }

  /**
   * Sets the badge to achieved or not depending on if user has earned it
   *
   * @param isAchieved what you wish to set the badge achieved to
   */
  public void setIsAchieved(boolean isAchieved) {
    this.isAchieved = isAchieved;
  }

  /**
   * Gets the location of the image of the badge
   *
   * @return the location of the image of the badge
   */
  public String getImageLocation() {
    return imageLocation;
  }

  /**
   * sets the location of the image of the badge
   *
   * @param imageLocation sets the location of the image of the badge
   */
  public void setImageLocation(String imageLocation) {
    this.imageLocation = imageLocation;
  }

  public String getTooltip() {
    return tooltip;
  }
}
