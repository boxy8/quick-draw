package nz.ac.auckland.se206.badges;

import java.util.Map;

public abstract class ProgressiveBadge extends Badge {
    public enum Rank {
        DIAMOND,
        PLATINUM,
        GOLD,
        SILVER,
        BRONZE,
        NONE
    }

    protected Rank rank;
    protected Map<Rank, Integer> map;
    protected int value;

    /**
     * Checks if a new rank of progressive badge has been achieved, and updates the
     * badge's rank and its image
     */
    public void updateBadge() {
        if (this.rank == Rank.DIAMOND) {
            return;
        }

        // Start building badge image location string
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("src/main/resources/images/badges/progressive/")
                .append(this.badgeType.toString())
                .append("_");

        // If new rank achieved, update rank and image location
        for (Rank rank : Rank.values()) {
            if (value > map.get(rank)) {
                if (this.rank == rank) {
                    return;
                } else {
                    stringBuilder.append(rank.toString()).append(".png");
                    this.rank = rank;
                    break;
                }
            }
        }
        // set image to image at new image location
        this.imageLocation = stringBuilder.toString();
    }

    @Override
    public boolean getIsAchieved() {
        return this.rank == Rank.DIAMOND;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

}
