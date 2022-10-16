package nz.ac.auckland.se206.badges;

import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class WinStreakBadge extends ProgressiveBadge {

  /**
   * Constructs an instance of a Win Streak badge with the values needed to unlock each rank.
   *
   * <p>Badge condition: attain a win streak of length
   *
   * <p>Bronze: 3
   *
   * <p>Silver: 10
   *
   * <p>Gold: 25
   *
   * <p>Platinum: 50
   *
   * <p>Diamond: 100
   */
  public WinStreakBadge() {
    super("Win Streak", "WIN_STREAK_BRONZE.png", "Hold a lengthy win streak", 100, 50, 25, 10, 3);
  }

  @Override
  public void updateValue() {
    Profile profile = ProfileHolder.getInstance().getCurrentProfile();
    Game lastGame = profile.getLatestGame();
    // only perform update if the latest game was won
    if (lastGame.getIsWin()) {
      setValue(profile.getWinStreak());
    }
  }
}
