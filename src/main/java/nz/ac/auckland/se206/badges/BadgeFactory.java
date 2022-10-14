package nz.ac.auckland.se206.badges;

import nz.ac.auckland.se206.badges.Badge.BadgeType;
import nz.ac.auckland.se206.badges.children.CollectorBadge;
import nz.ac.auckland.se206.badges.children.FirstTryBadge;
import nz.ac.auckland.se206.badges.children.GrandMasterBadge;
import nz.ac.auckland.se206.badges.children.MasterBadge;
import nz.ac.auckland.se206.badges.children.SniperBadge;
import nz.ac.auckland.se206.badges.children.SprinterBadge;
import nz.ac.auckland.se206.badges.children.WinStreakBadge;

public class BadgeFactory {

    public Badge createBadge(BadgeType badgeType) {
        switch (badgeType) {
            case WINSTREAK:
                return new WinStreakBadge();
            case COLLECTOR:
                return new CollectorBadge();
            case SNIPER:
                return new SniperBadge();
            case SPRINTER:
                return new SprinterBadge();
            case GRANDMASTER:
                return new GrandMasterBadge();
            case MASTER:
                return new MasterBadge();
            case FIRSTTRY:
                return new FirstTryBadge();
            default:
                return null;
        }
    }

}
