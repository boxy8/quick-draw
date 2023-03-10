package nz.ac.auckland.se206.badges;

import static nz.ac.auckland.se206.badges.ProgressiveBadge.Rank.BRONZE;
import static nz.ac.auckland.se206.badges.ProgressiveBadge.Rank.DIAMOND;
import static nz.ac.auckland.se206.badges.ProgressiveBadge.Rank.GOLD;
import static nz.ac.auckland.se206.badges.ProgressiveBadge.Rank.PLATINUM;
import static nz.ac.auckland.se206.badges.ProgressiveBadge.Rank.SILVER;

import java.util.HashMap;
import java.util.Map;

public abstract class ProgressiveBadge extends Badge {
  public enum Rank {
    DIAMOND,
    PLATINUM,
    GOLD,
    SILVER,
    BRONZE,
  }

  private Rank rank;
  private int value;
  protected Map<Rank, Integer> rankValues = new HashMap<>();

  /**
   * Constructs an instance of a progressive badge with the values needed to unlock each rank.
   *
   * @param name the name of the badge
   * @param imageLocation the location to the image (relative to badges folder)
   * @param diamondValue the value needed to unlock diamond rank
   * @param platinumValue the value needed to unlock platinum rank
   * @param goldValue the value needed to unlock gold rank
   * @param silverValue the value needed to unlock silver rank
   * @param bronzeValue the value needed to unlock bronze rank
   */
  public ProgressiveBadge(
      String name,
      String imageLocation,
      String tooltip,
      int diamondValue,
      int platinumValue,
      int goldValue,
      int silverValue,
      int bronzeValue) {
    super(name, "/progressive/" + imageLocation, tooltip);
    rankValues.put(DIAMOND, diamondValue);
    rankValues.put(PLATINUM, platinumValue);
    rankValues.put(GOLD, goldValue);
    rankValues.put(SILVER, silverValue);
    rankValues.put(BRONZE, bronzeValue);
  }

  /** Checks and updates rank based on current profile statistics */
  @Override
  public void updateBadge() {
    // highest possible rank
    if (this.rank == DIAMOND) {
      return;
    }
    updateValue();
    for (Rank rank : Rank.values()) {
      // return if we reach existing rank
      if (this.rank == rank) {
        return;
      }
      // if we meet the rank condition, update the rank
      if (value >= rankValues.get(rank)) {
        this.rank = rank;
        setIsAchieved(true);
        // update the suffix of the image location
        int n = getImageLocation().lastIndexOf("_");
        setImageLocation(getImageLocation().substring(0, n + 1) + rank.name() + ".png");
        return;
      }
    }
  }

  /** Updates the value the badge is measuring, based on current profile statistics */
  public abstract void updateValue();

  /**
   * Gets the value of the current badge
   *
   * @return the value of the current badge
   */
  protected int getValue() {
    return value;
  }

  /**
   * set the value of the current badge
   *
   * @param value set the value of the current badge
   */
  protected void setValue(int value) {
    this.value = value;
  }
}
