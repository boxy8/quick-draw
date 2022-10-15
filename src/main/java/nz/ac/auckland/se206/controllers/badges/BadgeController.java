package nz.ac.auckland.se206.controllers.badges;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public abstract class BadgeController extends VBox {
  protected FXMLLoader loader;
  protected boolean isAchieved;

  protected String badgeName;

  protected String imageLocation;
  @FXML protected VBox badgeBox;

  @FXML protected ImageView badgeIcon;

  @FXML protected Label badgeLabel;

  public BadgeController() {
    loader = new FXMLLoader(getClass().getResource("/fxml/badge.fxml"));
    loader.setRoot(this);
  }

  public void initialize() {
    // update gui elements once fields have been initialised
    badgeLabel.setText(badgeName);
    badgeBox.setVisible(false);
    badgeBox.setManaged(false);
    updateBadge();
  }

  protected void updateBadge() {
    updateBadgeIcon();
    if (isAchieved) {
      badgeBox.setVisible(true);
      badgeBox.setManaged(true);
    }
  }

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
