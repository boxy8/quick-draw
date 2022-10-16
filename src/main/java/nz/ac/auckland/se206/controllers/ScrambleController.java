package nz.ac.auckland.se206.controllers;

import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.words.WordHolder;

public class ScrambleController extends CanvasController {

  /** sets the display word to the scrambled word selected */
  @Override
  public void setDisplayWord() {
    String scrambledWord = WordHolder.getInstance().getScrambledWord();
    wordLabel.setText(scrambledWord);
  }

  /**
   * sets the end game button's visibility to value desired and shows user word
   *
   * @param visibility whether you want it visible or not
   */
  @Override
  protected void setEndgameVisibility(boolean visibility) {
    endGameContainer.setVisible(visibility);
    if (visibility) {
      // check for game end
      String currentWord = WordHolder.getInstance().getCurrentWord();
      wordLabel.setText(currentWord);
    }
  }

  /** Resets game when switching to this screen by clearing everything */
  @Override
  public void onSwitchIn() {

    // display new category
    String currentWord = WordHolder.getInstance().getCurrentWord();
    setDisplayWord();

    // stop predictions from taking place
    drawingStarted = false;

    // empty label when starting game
    resetPredictionLabel();

    // hide end game buttons
    setEndgameVisibility(false);

    // enable canvas and drawing buttons
    canvas.setDisable(false);

    // set Accuracy win condition according to Accuracy difficulty chosen
    // ranges from accuracy condition 1 to 3
    switch (ProfileHolder.getInstance()
        .getCurrentProfile()
        .getSetting2Difficulty()
        .get(Game.Setting.ACCURACY)) {
      case SUPER_EASY:
        accuracyCondition = 5;
        break;
      case EASY:
        accuracyCondition = 3;
        break;
      case MEDIUM:
        accuracyCondition = 2;
        break;
      case HARD:
        accuracyCondition = 1;
        break;
      default:
        break;
    }

    // set starting time according to Time difficulty chosen
    // ranges from 15 seconds to 60 seconds
    switch (ProfileHolder.getInstance()
        .getCurrentProfile()
        .getSetting2Difficulty()
        .get(Game.Setting.TIME)) {
      case SUPER_EASY:
        startingTime = 90;
        break;
      case EASY:
        startingTime = 60;
        break;
      case MEDIUM:
        startingTime = 45;
        break;
      case HARD:
        startingTime = 30;
        break;
      case MASTER:
        startingTime = 15;
        break;
    }

    // set Confidence win condition according to Confidence difficulty chosen
    // ranges from 0.01 to 0.5
    switch (ProfileHolder.getInstance()
        .getCurrentProfile()
        .getSetting2Difficulty()
        .get(Game.Setting.CONFIDENCE)) {
      case EASY:
        confidenceCondition = 0.01;
        break;
      case MEDIUM:
        confidenceCondition = 0.1;
        break;
      case HARD:
        confidenceCondition = 0.25;
        break;
      case MASTER:
        confidenceCondition = 0.5;
        break;
      default:
        break;
    }

    // hide save image and restart
    toolsContainer.setDisable(false);

    // reset to pen function
    paintButton.fire();
    game = new Game(currentWord, Game.GameMode.SCRAMBLE);

    // reset canvas and restart timer
    clearCanvas();
    startTimer();
  }
}
