package nz.ac.auckland.se206.badges;

public class SniperBadge extends ProgressiveBadge {
  /** Constructs an instance of a Sniper badge with the values needed to unlock each rank. */
  public SniperBadge() {
    super("Sniper", "SNIPER_BRONZE.png", -50, -75, -90, -100, -200);
  }
}
