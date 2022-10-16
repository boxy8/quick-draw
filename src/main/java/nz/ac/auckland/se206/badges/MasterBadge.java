package nz.ac.auckland.se206.badges;

import java.util.Map;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.games.Game.Setting;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class MasterBadge extends Badge {
  /**
   * Constructs a Master badge instance with name and image location.
   *
   * <p>Badge condition: win a game with any difficulty setting on Master.
   */
  public MasterBadge() {
    super("Master", "MASTER.png", "Win a game with any difficulty setting set to MASTER");
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
      // if difficulty matches master badge requirement, badge is achieved
      for (Setting setting : Setting.values()) {
        if (map.get(setting) == Game.Difficulty.MASTER) {
          setIsAchieved(true);
        }
      }
    }
  }
}
