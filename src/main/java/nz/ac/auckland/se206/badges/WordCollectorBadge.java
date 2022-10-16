package nz.ac.auckland.se206.badges;

import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class WordCollectorBadge extends ProgressiveBadge {

  /**
   * Constructs an instance of a Word Collector badge with the values needed to unlock each rank.
   *
   * <p>Badge condition:
   *
   * <p>Bronze: 10 unique words won
   *
   * <p>Silver: 25 unique words won
   *
   * <p>Gold: 50 unique words won
   *
   * <p>Platinum: 100 unique words won
   *
   * <p>Diamond: All unique words won
   */
  public WordCollectorBadge() {
    super(
        "Word Collector",
        "WORD_COLLECTOR_BRONZE.png",
        "Win many different words",
        345,
        100,
        50,
        25,
        10);
  }

  @Override
  public void updateValue() {
    Profile profile = ProfileHolder.getInstance().getCurrentProfile();
    Game lastGame = profile.getLatestGame();
    // only perform update if the latest game was won
    if (lastGame.getIsWin()) {
      setValue(profile.getWordHistory().size());
    }
  }
}
