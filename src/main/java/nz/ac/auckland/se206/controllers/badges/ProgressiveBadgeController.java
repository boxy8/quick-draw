package nz.ac.auckland.se206.controllers.badges;

import static nz.ac.auckland.se206.controllers.badges.ProgressiveBadgeController.Rank.*;

import java.util.Map;

public abstract class ProgressiveBadgeController extends BadgeController {
  public enum Rank {
    DIAMOND,
    PLATINUM,
    GOLD,
    SILVER,
    BRONZE,
  }

  protected Rank rank;
  protected int value;
  protected Map<Rank, Integer> rankValues;

  public ProgressiveBadgeController(
      int diamondValue, int platinumValue, int goldValue, int silverValue, int bronzeValue) {
    rankValues.put(DIAMOND, diamondValue);
    rankValues.put(PLATINUM, platinumValue);
    rankValues.put(GOLD, goldValue);
    rankValues.put(SILVER, silverValue);
    rankValues.put(BRONZE, bronzeValue);
  }

  /** Checks and updates rank based on current profile statistics */
  public void updateRank() {
    // highest possible rank
    if (this.rank == DIAMOND) {
      return;
    }
    for (Rank rank : Rank.values()) {
      // if we meet the rank condition, update the rank
      if (value >= rankValues.get(rank)) {
        setRank(rank);
        updateBadgeGui();
      }
    }
  }

  /**
   * Sets the rank of the badge and image location according to that rank
   *
   * @param rank the rank of the badge
   */
  public void setRank(Rank rank) {
    this.rank = rank;
    // update the suffix of the image location
    int n = imageLocation.lastIndexOf("_");
    imageLocation = imageLocation.substring(0, n) + rank.name();
  }
}
