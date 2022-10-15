package nz.ac.auckland.se206.badges;

public class WinStreakBadge extends ProgressiveBadge {
  /** Constructs an instance of a Win Streak badge with the values needed to unlock each rank. */
  public WinStreakBadge() {
    super("Win Streak", "WIN_STREAK_BRONZE.png", 100, 50, 25, 10, 3);
  }
}
