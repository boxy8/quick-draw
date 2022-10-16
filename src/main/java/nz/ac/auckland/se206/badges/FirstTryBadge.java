package nz.ac.auckland.se206.badges;

import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class FirstTryBadge extends Badge {
  /**
   * Constructs a First Try badge instance with name and image location.
   *
   * <p>Badge condition: win a game with no eraser or clear canvas used.
   */
  public FirstTryBadge() {
    super("First Try", "MASTER.png");
  }

  @Override
  public void updateBadge() {
    // no need to update if already achieved
    if (getIsAchieved()) {
      return;
    }
    Profile profile = ProfileHolder.getInstance().getCurrentProfile();
    Game lastGame = profile.getLatestGame();
    // only perform check if the latest game was won
    if (lastGame.getIsWin()) {
      // badge achieved if no eraser or clear used
      if (!lastGame.getWasEraserPressed() && !lastGame.getWasClearPressed()) {
        setIsAchieved(true);
      }
    }
  }
}
