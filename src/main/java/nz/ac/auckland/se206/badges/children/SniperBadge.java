package nz.ac.auckland.se206.badges.children;

import java.util.HashMap;

import nz.ac.auckland.se206.badges.ProgressiveBadge;

public class SniperBadge extends ProgressiveBadge {

    private final int NONE_PRECISION = -34501;
    private final int BRONZE_PRECISION = -200;
    private final int SILVER_PRECISION = -100;
    private final int GOLD_PRECISION = -90;
    private final int PLATINUM_PRECISION = -75;
    private final int DIAMOND_PRECISION = -50;

    public SniperBadge() {
        super();
        this.imageLocation = "src/main/resources/images/badges/progressive/SNIPER_NONE.png";
        this.badgeType = BadgeType.SNIPER;
        // Game lastGame =
        // ProfileHolder.getInstance().getCurrentProfile().getReversedGameHistory().get(0);
        // this.value = (int) -(lastGame.getWordPosition() * 100 +
        // lastGame.getPredictionProbability() * 100);
        this.map = new HashMap<>();
        int[] precisionConditions = { DIAMOND_PRECISION, PLATINUM_PRECISION, GOLD_PRECISION, SILVER_PRECISION,
                BRONZE_PRECISION, NONE_PRECISION };
        for (int i = 0; i < 6; i++) {
            this.map.put(Rank.values()[i], precisionConditions[i]);
        }

    }

}
