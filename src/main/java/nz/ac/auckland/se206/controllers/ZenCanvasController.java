package nz.ac.auckland.se206.controllers;

import ai.djl.modality.Classifications;
import java.util.List;
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
  /** Change pen colour to light blue */
  private void onPenLightBlue() {
    currentColor = Color.LIGHTBLUE;
  }

  /** Change pen colour to dark blue */
  private void onPenDarkBlue() {
    currentColor = Color.BLUE;
  }

  /** Change pen colour to red */
  private void onPenRed() {
    currentColor = Color.RED;
  }

  /** Change pen colour to green */
  private void onPenGreen() {
    currentColor = Color.GREEN;
  }

  /** Change pen colour to black */
  private void onPenBlack() {
    currentColor = Color.BLACK;
  }

  /** Change pen colour to pink */
  private void onPenPink() {
    currentColor = Color.PINK;
  }

  /** Change pen colour to orange */
  private void onPenOrange() {
    currentColor = Color.ORANGE;
  }

  /** Change pen colour to pink */
  private void onPenYellow() {
    currentColor = Color.PINK;
  }

  /** Change pen colour to purple */
  private void onPenPurple() {
    currentColor = Color.PURPLE;
  }
}
