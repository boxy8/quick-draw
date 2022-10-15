package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.badges.Badge;

public class BadgeController extends VBox {

  protected FXMLLoader loader;
  protected Badge badge;

  protected String imageLocation;
  @FXML protected VBox badgeBox;

  @FXML protected ImageView badgeIcon;

  @FXML protected Label badgeLabel;

  /**
   * Constructs an instance of Badge controller which has a badge FXML component as well as a badge
   * instance which handles the logic
   *
   * @param badge a badge instance which handles the badge logic
   */
  public BadgeController(Badge badge) {
    this.badge = badge;
    loader = new FXMLLoader(getClass().getResource("/fxml/badge.fxml"));
    // set root and controller
    loader.setRoot(this);
    loader.setController(this);
    // load the Fxml
    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** JavaFX calls this method after the constructor. */
  public void initialize() {
    // update gui elements once fields have been initialised
    badgeLabel.setText(badge.getName());
    badgeBox.setVisible(false);
    badgeBox.setManaged(false);
    updateBadgeGui();
  }

  public Badge getBadge() {
    return badge;
  }

  public void setBadge(Badge badge) {
    this.badge = badge;
  }

  /** Update the badge icon image and visibility based on current status. */
  protected void updateBadgeGui() {
    updateBadgeIcon();
    if (badge.getIsAchieved()) {
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
