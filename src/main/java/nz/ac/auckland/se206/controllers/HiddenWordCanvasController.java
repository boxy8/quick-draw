package nz.ac.auckland.se206.controllers;

import ai.djl.modality.Classifications;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.util.Duration;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.ml.DoodlePrediction;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.words.WordHolder;
import org.apache.commons.lang3.StringUtils;

public class HiddenWordCanvasController extends CanvasController {

  private ArrayList<Integer> revealedIndexes = new ArrayList<>();
  private String hiddenWord;

  @Override
  public void onSwitchIn() {
    String currentWord = WordHolder.getInstance().getCurrentWord();
    hiddenWord = StringUtils.repeat('_', currentWord.length());
    // display new category
    wordLabel.setText(hiddenWord);

    revealedIndexes.removeAll(revealedIndexes);

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

    // turn off save and reset
    toolsContainer.setDisable(false);

    // reset to pen function
    paintButton.fire();
    game = new Game(currentWord, Game.GameMode.HIDDEN);
    clearCanvas();
    startTimer();
  }

  @Override
  protected void startTimer() {
    resetTimer();
    // calling this first seems to stop initial freezing problem
    getCurrentSnapshot();
    playGamemodeSoundEffect();
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  // update predictions and timer
                  if (drawingStarted) {
                    onPredict(getCurrentSnapshot());
                  }
                  // reduce time
                  countDown();
                  if ((this.startingTime - this.timeLeft) % 15 == 0) {
                    // show letter
                    revealLetter();
                  }
                  if (this.timeLeft == 0) {
                    // show word at the end
                    wordLabel.setText(WordHolder.getInstance().getCurrentWord());
                  }
                }));
    timeline.setCycleCount(Animation.INDEFINITE); // countdown value (seconds)
    timeline.play();
  }

  /** Reveals a letter to the player by setting the visible string correctly */
  private void revealLetter() {
    Random rand = new Random();
    // get current word
    String currentWord = WordHolder.getInstance().getCurrentWord();
    // find random letter
    int randomIndex = rand.nextInt(0, currentWord.length());
    while (revealedIndexes.contains(randomIndex)) {
      randomIndex = rand.nextInt(0, currentWord.length());
    }
    // build half hidden word
    StringBuilder stringBuilder = new StringBuilder(hiddenWord);
    stringBuilder.setCharAt(randomIndex, currentWord.charAt(randomIndex));
    // convert back to string
    hiddenWord = stringBuilder.toString();
    // display to user
    wordLabel.setText(hiddenWord);
    revealedIndexes.add(randomIndex);
  }

  @Override
  protected void onPredict(BufferedImage canvasImg) {
    // run in new thread to make sure GUI does not freeze
    Task<Void> backgroundTask =
        new Task<>() {
          @Override
          protected Void call() throws Exception {
            // get current prediction from the machine learning model
            List<Classifications.Classification> predictions = model.getPredictions(canvasImg, 345);
            Platform.runLater(
                () -> {
                  // after the prediction is received then update text to show it
                  predictionsLabel.setText(DoodlePrediction.getHiddenPredictions(predictions));
                });

            // update gameWon boolean if player has won after the last prediction update
            if (isWin(predictions)) {
              game.setIsWin(true);
            }
            return null;
          }
        };

    // after prediction has finished, end game if player won
    backgroundTask.setOnSucceeded(
        event -> {
          if (game.getIsWin()) {
            endGame();
            wordLabel.setText(WordHolder.getInstance().getCurrentWord());
          }
        });
    // run thread
    Thread backgroundPerson = new Thread(backgroundTask);
    backgroundPerson.start();
  }
}
