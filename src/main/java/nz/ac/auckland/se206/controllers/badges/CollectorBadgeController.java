package nz.ac.auckland.se206.controllers.badges;

import java.io.IOException;

public class CollectorBadgeController extends BadgeController {

  public CollectorBadgeController() {
    badgeName = "Collector";
    imageLocation = "progressive/COLLECTOR_BRONZE.png";

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
