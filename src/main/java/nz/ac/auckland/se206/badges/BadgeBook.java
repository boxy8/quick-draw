package nz.ac.auckland.se206.badges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nz.ac.auckland.se206.badges.Badge.BadgeType;

public class BadgeBook {

    private Map<Badge, Boolean> badgeMap;
    private ArrayList<Badge> badges;
    private ArrayList<Badge> unlockedBadges;
    private ArrayList<Badge> lockedBadges;

    public BadgeBook() {
        this.badgeMap = new HashMap<>();
        this.badges = new ArrayList<>();
        this.unlockedBadges = new ArrayList<>();
        this.lockedBadges = new ArrayList<>();
        BadgeFactory badgeFactory = new BadgeFactory();
        for (BadgeType badgeType : BadgeType.values()) {
            Badge badge = badgeFactory.createBadge(badgeType);
            this.badges.add(badge);
            this.lockedBadges.add(badge);
            badgeMap.put(badge, false);
        }
    }

    public ArrayList<Badge> getBadges() {
        return badges;
    }

    public Map<Badge, Boolean> getBadgeMap() {
        return badgeMap;
    }

    public ArrayList<Badge> getUnlockedBadges() {
        return unlockedBadges;
    }

    public ArrayList<Badge> getLockedBadges() {
        return lockedBadges;
    }
}
