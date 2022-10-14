package nz.ac.auckland.se206.badges.children;

import java.util.Map;

import nz.ac.auckland.se206.badges.Badge;
import nz.ac.auckland.se206.games.Game.Difficulty;
import nz.ac.auckland.se206.games.Game.Setting;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class GrandMasterBadge extends Badge {
    public GrandMasterBadge() {
        super();
        this.imageLocation = "src/main/resources/images/badges/GRANDMASTER1.png";
        this.badgeType = BadgeType.GRANDMASTER;
    }

    public void updateBadge() {
        Profile profile = ProfileHolder.getInstance().getCurrentProfile();
        Map<Setting, Difficulty> map = profile.getReversedGameHistory().get(0).getSettings2Difficulty();
        if (map.get(Setting.ACCURACY) == Difficulty.HARD
                && map.get(Setting.WORDS) == Difficulty.MASTER
                && map.get(Setting.TIME) == Difficulty.MASTER
                && map.get(Setting.CONFIDENCE) == Difficulty.MASTER) {
            this.isAchieved = true;
            this.imageLocation = "src/main/resources/images/badges/GRANDMASTER2.png";
        }
    }

}
