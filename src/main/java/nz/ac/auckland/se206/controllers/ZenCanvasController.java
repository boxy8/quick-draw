package nz.ac.auckland.se206.controllers;

import ai.djl.modality.Classifications;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class ZenCanvasController extends CanvasController {
  private Color currentColor; // American spelling : (

  /** Always returns false as we don't want ZEN mode to end */
  @Override
  protected boolean isWin(List<Classifications.Classification> classifications) {
    return false;
  }

  /** sets the timer to blank value rather than a time as such */
  @Override
  protected void resetTimer() {
    timerLabel.setText("--:--");
  }

  /** This overrides the function as there is no reason to count down */
  @Override
  protected void countDown() {}

  // COLORS
  @FXML
  private void onChooseBlue() {
    currentColor = Color.LIGHTBLUE;
  }

  @FXML
  private void onChooseDarkBlue() {
    currentColor = Color.BLUE;
  }

  @FXML
  private void onChooseRed() {
    currentColor = Color.RED;
  }

  @FXML
  private void onChooseGreen() {
    currentColor = Color.GREEN;
  }

  @FXML
  private void onChooseBlack() {
    currentColor = Color.BLACK;
  }

  @FXML
  private void onChoosePink() {
    currentColor = Color.PINK;
  }

  @FXML
  private void onChooseOrange() {
    currentColor = Color.ORANGE;
  }

  @FXML
  private void onChooseYellow() {
    currentColor = Color.YELLOW;
  }

  @FXML
  private void onChoosePurple() {
    currentColor = Color.PURPLE;
  }

  @FXML
  private void onChooseExtraLarge() {}

  @FXML
  private void onChooseLarge() {}

  @FXML
  private void onChooseMedium() {}

  @FXML
  private void onChooseSmall() {}
}
