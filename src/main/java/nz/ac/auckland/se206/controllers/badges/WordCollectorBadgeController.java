package nz.ac.auckland.se206.controllers.badges;

import java.io.IOException;

public class WordCollectorBadgeController extends ProgressiveBadgeController {

  public WordCollectorBadgeController() {
    super(10, 25, 50, 100, 345);
    badgeType = BadgeType.WORD_COLLECTOR;
    imageLocation = "progressive/WORD_COLLECTOR_BRONZE.png";

    // set the controller of the badge component
    loader.setController(this);
    // load the badge object from FXML
    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
