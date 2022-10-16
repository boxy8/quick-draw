package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.badges.Badge;

public class BadgeController extends VBox {

  protected FXMLLoader loader;
  protected Badge badge;
  @FXML protected VBox badgeBox;

  @FXML protected ImageView badgeIcon;

  @FXML protected Label badgeLabel;

  /**
   * Constructs an instance of Badge controller which handles the badge FXML component, and holds a
   * badge instance which handles the logic
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
    // install tooltip based on tooltip description in badge
    Tooltip tooltip = new Tooltip(badge.getTooltip());
    tooltip.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
    Tooltip.install(badgeIcon, tooltip);
    badgeBox.setVisible(false);
    badgeBox.setManaged(false);
    updateBadgeGui();
  }

  /**
   * Gets the current badge for the user
   *
   * @return the current badge requested
   */
  public Badge getBadge() {
    return badge;
  }

  /**
   * Sets the current badge to one input
   *
   * @param badge set the badge that is input
   */
  public void setBadge(Badge badge) {
    this.badge = badge;
    updateBadgeGui(); // update GUI after setting a new badge
  }

  /** Update the badge icon image and visibility based on current status. */
  protected void updateBadgeGui() {
    updateBadgeIcon();
    badgeBox.setVisible(badge.getIsAchieved());
    badgeBox.setManaged(badge.getIsAchieved());
  }

  /** Update badge icon image to be displayed */
  protected void updateBadgeIcon() {
    String filePath = null;
    try {
      // create correct file path
      filePath =
          getClass().getResource("/images/badges/" + badge.getImageLocation()).toURI().toString();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    badgeIcon.setImage(new Image(filePath));
  }
}
