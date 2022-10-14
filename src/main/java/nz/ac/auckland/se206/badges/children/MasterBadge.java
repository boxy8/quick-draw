package nz.ac.auckland.se206.badges.children;

import nz.ac.auckland.se206.badges.Badge;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.games.Game.Difficulty;
import nz.ac.auckland.se206.games.Game.Setting;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class MasterBadge extends Badge {

    public MasterBadge() {
        super();
        this.imageLocation = "src/main/resources/images/badges/MASTER1.png";
        this.badgeType = BadgeType.MASTER;
    }

    public void updateBadge() {
        Game lastGame = ProfileHolder.getInstance().getCurrentProfile().getReversedGameHistory().get(0);
        for (Setting setting : Setting.values()) {
            if (lastGame.getSettings2Difficulty().get(setting) == Difficulty.MASTER) {
                this.isAchieved = true;
                this.imageLocation = "src/main/resources/images/badges/MASTER2.png";
            }
        }

    }

}
