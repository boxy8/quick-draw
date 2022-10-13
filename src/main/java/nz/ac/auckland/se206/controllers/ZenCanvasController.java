package nz.ac.auckland.se206.controllers;

import ai.djl.modality.Classifications;
import java.util.List;
import javafx.scene.paint.Color;

public class ZenCanvasController extends CanvasController {
  private Color currentColor; // American spelling : (

  @Override
  protected boolean isWin(List<Classifications.Classification> classifications) {
    return false;
  }

  @Override
  protected void resetTimer() {
    timerLabel.setText("--:--");
  }

  @Override
  protected void countDown() {}

  // COLORS
  private void onPenLightBlue() {
    currentColor = Color.LIGHTBLUE;
  }

  private void onPenDarkBlue() {
    currentColor = Color.BLUE;
  }

  private void onPenRed() {
    currentColor = Color.RED;
  }

  private void onPenGreen() {
    currentColor = Color.GREEN;
  }

  private void onPenBlack() {
    currentColor = Color.BLACK;
  }

  private void onPenPink() {
    currentColor = Color.PINK;
  }

  private void onPenOrange() {
    currentColor = Color.ORANGE;
  }

  private void onPenYellow() {
    currentColor = Color.PINK;
  }

  private void onPenPurple() {
    currentColor = Color.PURPLE;
  }
}
