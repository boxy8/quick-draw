package nz.ac.auckland.se206.badges;

import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class SniperBadge extends ProgressiveBadge {

  /**
   * Constructs an instance of a Sniper badge with the values needed to unlock each rank.
   *
   * <p>Badge condition: win with a prediction that is
   *
   * <p>Bronze: top 2
   *
   * <p>Silver: top 1
   *
   * <p>Gold: top 1 + >10% prediction confidence
   *
   * <p>Platinum: top 1 + >25% prediction confidence
   *
   * <p>Diamond: top 1 + >50% prediction confidence
   */
  public SniperBadge() {
    super("Sniper", "SNIPER_BRONZE.png", -50, -75, -90, -100, -200);
  }

  @Override
  public void updateValue() {
    Profile profile = ProfileHolder.getInstance().getCurrentProfile();
    Game lastGame = profile.getLatestGame();
    // only perform update if the latest game was won
    if (lastGame.getIsWin()) {
      setValue(
          (int) -(lastGame.getWordPosition() * 100 + lastGame.getPredictionProbability() * 100));
    }
  }
}
