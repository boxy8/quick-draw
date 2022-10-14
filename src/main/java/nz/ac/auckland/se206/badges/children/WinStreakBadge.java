package nz.ac.auckland.se206.badges.children;

import java.util.HashMap;

import nz.ac.auckland.se206.badges.ProgressiveBadge;

public class WinStreakBadge extends ProgressiveBadge {

    private final int NONE_STREAK = 0;
    private final int BRONZE_STREAK = 3;
    private final int SILVER_STREAK = 10;
    private final int GOLD_STREAK = 25;
    private final int PLATINUM_STREAK = 50;
    private final int DIAMOND_STREAK = 100;

    public WinStreakBadge() {
        super();
        this.imageLocation = "src/main/resources/images/badges/progressive/WINSTREAK_NONE.png";
        this.badgeType = BadgeType.WINSTREAK;
        this.map = new HashMap<>();
        int[] streakConditions = { DIAMOND_STREAK, PLATINUM_STREAK, GOLD_STREAK, SILVER_STREAK, BRONZE_STREAK,
                NONE_STREAK };
        for (int i = 0; i < 6; i++) {
            this.map.put(Rank.values()[i], streakConditions[i]);
        }
    }

}
