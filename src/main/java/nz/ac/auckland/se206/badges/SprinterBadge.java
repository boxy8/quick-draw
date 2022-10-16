package nz.ac.auckland.se206.badges;

import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class SprinterBadge extends ProgressiveBadge {

  /**
   * Constructs an instance of a Sprinter badge with the values needed to unlock each rank.
   *
   * <p>Badge condition: win with a time that is under
   *
   * <p>Bronze: 45 seconds
   *
   * <p>Silver: 30 seconds
   *
   * <p>Gold: 15 seconds
   *
   * <p>Platinum: 10 seconds
   *
   * <p>Diamond: 5 seconds
   */
  public SprinterBadge() {
    super(
        "Sprinter",
        "SPRINTER_BRONZE.png",
        "Win a game in a short timeframe",
        -5,
        -10,
        -15,
        -30,
        -45);
  }

  @Override
  public void updateValue() {
    Profile profile = ProfileHolder.getInstance().getCurrentProfile();
    Game lastGame = profile.getLatestGame();
    // only perform update if the latest game was won
    if (lastGame.getIsWin()) {
      setValue(-lastGame.getDuration());
    }
  }
}
