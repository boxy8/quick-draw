package nz.ac.auckland.se206.badges;

import java.util.Map;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.games.Game.Difficulty;
import nz.ac.auckland.se206.games.Game.Setting;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class GrandmasterBadge extends Badge {
  /**
   * Constructs a Grand master instance with name and image location.
   *
   * <p>Badge condition: win a game with difficulty settings Hard, Master, Master, Master.
   */
  public GrandmasterBadge() {
    super("Grandmaster", "GRANDMASTER.png");
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
      Map<Game.Setting, Game.Difficulty> map = lastGame.getSettings2Difficulty();
      // if difficulty matches grand master badge requirement, badge is achieved
      if (map.get(Setting.ACCURACY) == Difficulty.HARD
          && map.get(Setting.WORDS) == Difficulty.MASTER
          && map.get(Setting.TIME) == Difficulty.MASTER
          && map.get(Setting.CONFIDENCE) == Difficulty.MASTER) {
        setIsAchieved(true);
      }
    }
  }
}
