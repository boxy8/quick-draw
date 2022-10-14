package nz.ac.auckland.se206.badges.children;

import java.util.HashMap;
import nz.ac.auckland.se206.badges.ProgressiveBadge;

public class SprinterBadge extends ProgressiveBadge {

    private final int NONE_DURATION = 60;
    private final int BRONZE_DURATION = 45;
    private final int SILVER_DURATION = 30;
    private final int GOLD_DURATION = 15;
    private final int PLATINUM_DURATION = 10;
    private final int DIAMOND_DURATION = 5;

    public SprinterBadge() {
        super();
        this.imageLocation = "src/main/resources/images/badges/progressive/SPRINTER_NONE.png";
        this.badgeType = BadgeType.SPRINTER;
        // this.value =
        // ProfileHolder.getInstance().getCurrentProfile().getReversedGameHistory().get(0).getDuration();
        this.map = new HashMap<>();
        int[] durationConditions = { DIAMOND_DURATION, PLATINUM_DURATION, GOLD_DURATION, SILVER_DURATION,
                BRONZE_DURATION, NONE_DURATION };
        for (int i = 0; i < 6; i++) {
            this.map.put(Rank.values()[i], durationConditions[i]);
        }
    }

}
