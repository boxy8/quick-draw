package nz.ac.auckland.se206.badges;

public abstract class Badge {

    protected boolean isAchieved;
    protected String imageLocation;
    protected BadgeType badgeType;

    public enum BadgeType {
        WINSTREAK,
        COLLECTOR,
        SNIPER,
        SPRINTER,
        MASTER,
        GRANDMASTER,
        FIRSTTRY
    }

    public Badge() {
        this.isAchieved = false;
    }

    public abstract void updateBadge();

    public String getImageLocation() {
        return imageLocation;
    }

    public boolean getIsAchieved() {
        return isAchieved;
    }

    public void setIsAchieved(boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

}
