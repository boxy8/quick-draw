package nz.ac.auckland.se206.badges.children;

import nz.ac.auckland.se206.badges.Badge;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public class FirstTryBadge extends Badge {

    public FirstTryBadge() {
        super();
        this.imageLocation = "src/main/resources/images/badges/FIRSTTRY1.png";
        // this.badgeType = BadgeType.FIRSTTRY;
    }

    public void updateBadge() {
        Game lastGame = ProfileHolder.getInstance().getCurrentProfile().getReversedGameHistory().get(0);
        if (!lastGame.getWasEraserPressed() && !lastGame.getWasClearPressed()) {
            this.isAchieved = true;
            this.imageLocation = "src/main/resources/images/badges/FIRSTTRY2.png";
        }
    }
}
