package nz.ac.auckland.se206.controllers.badges;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.profiles.ProfileHolder;

public abstract class BadgeController extends VBox {
  protected FXMLLoader loader;
  protected boolean isAchieved;

  protected BadgeType badgeType;

  protected String imageLocation;
  @FXML protected VBox badgeBox;

  @FXML protected ImageView badgeIcon;

  @FXML protected Label badgeLabel;

  public enum BadgeType {
    WIN_STREAK("Win Streak"),
    COLLECTOR("Collector"),
    SNIPER("Sniper"),
    SPRINTER("Sprinter"),
    MASTER("Master"),
    GRANDMASTER("Grandmaster"),
    FIRST_TRY("First Try");

    private final String badgeName;

    private BadgeType(String badgeName) {
      this.badgeName = badgeName;
    }

    @Override
    public String toString() {
      return badgeName;
    }
  }

  public BadgeController() {
    // check if badge is stored as achieved on current user profile
    if (ProfileHolder.getInstance().getCurrentProfile().getBadges().contains(badgeType)) {
      isAchieved = true;
    }
    loader = new FXMLLoader(getClass().getResource("/fxml/badge.fxml"));
    loader.setRoot(this);
  }

  /** JavaFX calls this method after the constructor. */
  public void initialize() {
    // update gui elements once fields have been initialised
    badgeLabel.setText(badgeType.toString());
    badgeBox.setVisible(false);
    badgeBox.setManaged(false);
    updateBadge();
  }

  /** Update the badge icon image and visibility based on current status. */
  protected void updateBadge() {
    updateBadgeIcon();
    if (isAchieved) {
      badgeBox.setVisible(true);
      badgeBox.setManaged(true);
    }
  }

  /** Update badge icon image. */
  protected void updateBadgeIcon() {
    String filePath = null;
    try {
      filePath = getClass().getResource("/images/badges/" + this.imageLocation).toURI().toString();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    badgeIcon.setImage(new Image(filePath));
  }
}
