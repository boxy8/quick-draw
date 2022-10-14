package nz.ac.auckland.se206.badges.children;

import java.util.HashMap;
import java.util.Map;

import nz.ac.auckland.se206.badges.ProgressiveBadge;

public class CollectorBadge extends ProgressiveBadge {

    private final int NONE_COLLECTED = 0;
    private final int BRONZE_COLLECTED = 10;
    private final int SILVER_COLLECTED = 25;
    private final int GOLD_COLLECTED = 50;
    private final int PLATINUM_COLLECTED = 100;
    private final int DIAMOND_COLLECTED = 345;

    private Map<Rank, Integer> hiMap;

    public CollectorBadge() {
        super();
        this.hiMap = new HashMap<>();
        this.imageLocation = "src/main/resources/images/badges/progressive/COLLECTOR_NONE.png";
        this.badgeType = BadgeType.COLLECTOR;

        // construct map of Ranks and their minimum condition to achieve
        int[] collectedValues = { DIAMOND_COLLECTED, PLATINUM_COLLECTED, GOLD_COLLECTED, SILVER_COLLECTED,
                BRONZE_COLLECTED, NONE_COLLECTED };
        for (int i = 0; i < 6; i++) {
            this.hiMap.put(Rank.values()[i], collectedValues[i]);
        }
    }

    /**
     * Calculates the number of words a profile has 'collected'
     * 
     * @return the number of unique words the profile has won
     */
    // private int calculateCollected() {
    // Set<String> wonWords = new HashSet<String>();
    // List<Game> gameHistory =
    // ProfileHolder.getInstance().getCurrentProfile().getGameHistory();
    // for (Game game : gameHistory) {
    // if (game.getIsWin()) {
    // wonWords.add(game.getWord());
    // }
    // }
    // return wonWords.size();
    // }

}
