package nz.ac.auckland.se206.controllers.badges;

public class BadgeFactory {

  public BadgeController createBadge(BadgeController.BadgeType badgeType) {
    switch (badgeType) {
      case WIN_STREAK:
        return new WinStreakBadgeController();
      case COLLECTOR:
        return new CollectorBadgeController();
      case SNIPER:
        return new SniperBadgeController();
      case SPRINTER:
        return new SprinterBadgeController();
      case GRANDMASTER:
        return new GrandmasterBadgeController();
      case MASTER:
        return new MasterBadgeController();
      case FIRST_TRY:
        return new FirstTryBadgeController();
      default:
        return null;
    }
  }
}
