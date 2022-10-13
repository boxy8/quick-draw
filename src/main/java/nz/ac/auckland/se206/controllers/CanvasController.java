package nz.ac.auckland.se206.controllers;

import static nz.ac.auckland.se206.ml.DoodlePrediction.getFormattedPredictions;

import ai.djl.ModelException;
import ai.djl.modality.Classifications;
import ai.djl.translate.TranslateException;
import com.opencsv.exceptions.CsvException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.games.Game;
import nz.ac.auckland.se206.ml.DoodlePrediction;
import nz.ac.auckland.se206.profiles.Profile;
import nz.ac.auckland.se206.profiles.ProfileHolder;
import nz.ac.auckland.se206.sounds.SoundEffects;
import nz.ac.auckland.se206.speech.TextToSpeech;
import nz.ac.auckland.se206.words.WordHolder;

/**
 * This is the controller of the canvas. You are free to modify this class and the corresponding
 * FXML file as you see fit. For example, you might no longer need the "Predict" button because the
 * DL model should be automatically queried in the background every second.
 *
 * <p>!! IMPORTANT !!
 *
 * <p>Although we added the scale of the image, you need to be careful when changing the size of the
 * drawable canvas and the brush size. If you make the brush too big or too small with respect to
 * the canvas size, the ML model will not work correctly. So be careful. If you make some changes in
 * the canvas and brush sizes, make sure that the prediction works fine.
 */
public abstract class CanvasController implements SwitchInListener, SwitchOutListener {
  @FXML protected Canvas canvas;

  @FXML protected Label wordLabel;

  @FXML protected Label timerLabel;

  @FXML protected VBox toolsContainer;

  @FXML protected RadioButton paintButton;

  @FXML protected Label predictionsLabel;

  @FXML protected Label resultLabel;

  @FXML protected AnchorPane endGameContainer;

  protected GraphicsContext graphic;
  protected DoodlePrediction model;
  protected Game game;
  protected int startingTime;
  protected int accuracyCondition;
  protected double confidenceCondition;
  protected int timeLeft;
  protected boolean drawingStarted; // Tells label to update
  protected Timeline timeline;
  protected TextToSpeech textToSpeech;

  private SoundEffects timerSoundEffect;
  private SoundEffects winSoundEffect;
  private SoundEffects loseSoundEffect;
  private SoundEffects zenMode; // yet to be added in with zen mode

  /**
   * JavaFX calls this method once the GUI elements are loaded. In our case we create a listener for
   * the drawing, and we load the ML model.
   *
   * @throws ModelException If there is an error in reading the input/output of the DL model.
   * @throws IOException If the model cannot be found on the file system.
   * @throws URISyntaxException
   * @throws CsvException
   */
  public void initialize() throws ModelException, IOException, CsvException, URISyntaxException {
    winSoundEffect = new SoundEffects("win");
    loseSoundEffect = new SoundEffects("lose");

    predictionsLabel.setWrapText(true); // wrap predictions that are too long

    model = new DoodlePrediction();

    graphic = canvas.getGraphicsContext2D();
    graphic.setLineWidth(7);
    graphic.setLineCap(StrokeLineCap.ROUND);

    /**
     * @author pelgrim <https://stackoverflow.com/users/8937787/pelgrim>
     * @copyright 2018 pelgrim
     * @license CC BY-SA 3.0
     * @see {@link https://stackoverflow.com/a/47284341/1248177|How to draw a continuous line with
     *     mouse on JavaFX canvas?}
     */
    // Start drawing on mouse click smoothly
    canvas.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            graphic.beginPath();
            graphic.moveTo(event.getX(), event.getY());
            graphic.stroke();
          }
        });
    // continue drawing on mouse drag smoothly
    canvas.addEventHandler(
        MouseEvent.MOUSE_DRAGGED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            graphic.lineTo(event.getX(), event.getY()); // draw path
            graphic.stroke(); // fill in path
            graphic.closePath();
            graphic.beginPath();
            graphic.moveTo(event.getX(), event.getY());
          }
        });
    // stop drawing when the left click is released
    canvas.addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            graphic.lineTo(event.getX(), event.getY());
            graphic.stroke();
            graphic.closePath();
          }
        });

    // Tells label to update when user starts drawing
    // and also start predictions
    canvas.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (!drawingStarted) {
              drawingStarted = true;
            }
          }
        });
  }

  /** Resets game when switching to this screen by clearing everything */
  @Override
  public void onSwitchIn() {
    String currentWord = WordHolder.getInstance().getCurrentWord();
    wordLabel.setText(currentWord); // display new category
    resultLabel.setText(""); // reset win/lose indicator

    // stop predictions from taking place
    drawingStarted = false;

    // empty label when starting game
    resetPredictionLabel();

    // hide end game buttons
    endGameContainer.setVisible(false);

    // enable canvas and drawing buttons
    canvas.setDisable(false);

    // set Accuracy win condition according to Accuracy difficulty chosen
    switch (ProfileHolder.getInstance()
        .getCurrentProfile()
        .getSetting2Difficulty()
        .get(Game.Setting.ACCURACY)) {
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
    switch (ProfileHolder.getInstance()
        .getCurrentProfile()
        .getSetting2Difficulty()
        .get(Game.Setting.TIME)) {
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
    }

    toolsContainer.setDisable(false);

    // reset to pen function
    paintButton.fire();
    game = new Game(currentWord, Game.GameMode.NORMAL);
    clearCanvas();
    startTimer();
  }

  /** Used to start the timer for predictions and also the clock */
  protected void startTimer() {
    resetTimer();
    getCurrentSnapshot(); // calling this first seems to stop initial freezing problem
    try {

      timerSoundEffect = new SoundEffects("timer");
      timerSoundEffect.playRepeateSound();
    } catch (URISyntaxException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  // update predictions and timer
                  if (drawingStarted) {
                    onPredict(getCurrentSnapshot());
                  }
                  countDown();
                }));
    timeline.setCycleCount(Animation.INDEFINITE); // countdown value (seconds)
    timeline.play();
  }

  /**
   * reset the game timer back to the time associated with the time difficulty then refresh the
   * label to the time also
   */
  protected void resetTimer() {
    timeLeft = startingTime;
    updateTimerDisplay(timeLeft);
  }

  /** Reset the prediction label so that we don't have guesses before user starts drawing */
  protected void resetPredictionLabel() {
    predictionsLabel.setText(" ");
  }

  /** Updates the time to reduce by one each time it is run, it also ends game at 0 seconds */
  protected void countDown() {
    timeLeft--;
    updateTimerDisplay(timeLeft);
    if (timeLeft == 0) {
      endGame();
    }
  }

  /** Formats the timer to make it look nice with minutes and seconds */
  protected void updateTimerDisplay(int s) {
    timerLabel.setText(String.format("%02d:%02d", (s / 60), (s % 60)));
  }

  /**
   * Ends the game by stopping all running events, enabling/disabling required buttons, runs end
   * screen
   */
  protected void endGame() {
    timerSoundEffect.stopSound();
    SoundEffects.playBackgroundMusic();
    timeline.stop(); // stop timer/prediction updates
    canvas.setDisable(true);
    toolsContainer.setDisable(true);

    // display and announce a message based on game result

    if (game.getIsWin()) {
      winSoundEffect.playSound();
      resultLabel.setText("You win!");
      speak("Congratulations!");
    } else {
      loseSoundEffect.playSound();
      resultLabel.setText("You lost!");
      speak("Maybe next time!");
    }

    endGameContainer.setVisible(true);

    // set game time
    int gameDuration = startingTime - timeLeft;
    game.setDuration(gameDuration);

    // update profile statistics with finished game
    Profile userProfile = ProfileHolder.getInstance().getCurrentProfile();
    userProfile.updateAllStats(game);

    // save to profile json if non-guest user
    if (!userProfile.isGuest()) {
      try {
        userProfile.saveToFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Takes user to the category display from the canvas screen
   *
   * @param event
   */
  @FXML
  protected void onNewGame(ActionEvent event) {
    SoundEffects.playBackgroundMusic();
    SceneManager.changeScene(event, SceneManager.AppUi.DIFFICULTY_SELECTOR);
  }

  /**
   * This method executes when the user clicks the "Predict" button. It gets the current drawing,
   * queries the DL model and prints on the console the top 5 predictions of the DL model and the
   * elapsed time of the prediction in milliseconds.
   *
   * @throws TranslateException If there is an error in reading the input/output of the DL model.
   */
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
                  predictionsLabel.setText(getFormattedPredictions(predictions));
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
          }
        });

    Thread backgroundPerson = new Thread(backgroundTask);
    backgroundPerson.start();
  }

  /** Save the current snapshot as a file image. */
  @FXML
  protected void onSave(ActionEvent event) {

    FileChooser savefile = new FileChooser();
    // set the default options for the file chooser
    savefile.setTitle("Save File");
    // saving files as a png so making that the default
    FileChooser.ExtensionFilter extensionFilter =
        new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
    savefile.getExtensionFilters().add(extensionFilter);
    // default name setting
    // using string builder for better performance
    StringBuilder sb = new StringBuilder();
    sb.append(ProfileHolder.getInstance().getCurrentProfile().getUsername())
        .append("'s ")
        .append(WordHolder.getInstance().getCurrentWord());
    savefile.setInitialFileName(sb.toString());
    // get the stage
    Button button = (Button) event.getSource();
    Window stage = button.getScene().getWindow();
    // display file save selection to user
    File file = savefile.showSaveDialog(stage);
    if (file != null) {
      try {
        ImageIO.write(getCurrentSnapshot(), "png", file);
      } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("Error could not write image");
      }
    }
  }

  /**
   * Checks whether the player has won, i.e. whether the current word is in top predictions as
   * determined by accuracy difficulty.
   *
   * @param classifications The list of predictions
   * @return whether the player has won or not
   */
  protected boolean isWin(List<Classifications.Classification> classifications) {
    // go through top predictions determined by accuracy difficulty
    for (int i = 0; i < accuracyCondition; i++) {

      // if top word is correct with confidence above the required amount
      if ((classifications.get(i).getProbability() > confidenceCondition)
          && classifications
              .get(i)
              .getClassName()
              .replaceAll("_", " ")
              .equals(WordHolder.getInstance().getCurrentWord())) {
        return true;
      }
    }
    return false;
  }

  @FXML
  protected void onPaintTool() {
    graphic.setStroke(Color.BLACK);
  }

  /** Enables the eraser for the user */
  @FXML
  protected void onEraseTool() {
    // change to eraser
    graphic.setStroke(Color.WHITE);
  }

  @FXML
  protected void onClearTool() {
    clearCanvas();
  }

  /** Clears the canvas */
  protected void clearCanvas() {
    graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  /**
   * Get the current snapshot of the canvas.
   *
   * @return The BufferedImage corresponding to the current canvas content.
   */
  protected BufferedImage getCurrentSnapshot() {
    final Image snapshot = canvas.snapshot(null, null);
    final BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

    // Convert into a binary image.
    final BufferedImage imageBinary =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

    final Graphics2D graphics = imageBinary.createGraphics();

    graphics.drawImage(image, 0, 0, null);

    // To release memory we dispose.
    graphics.dispose();

    return imageBinary;
  }

  /**
   * Getter method for text to speech so that it can be stopped at the end of the game
   *
   * @return
   */
  public TextToSpeech getTextToSpeech() {
    return textToSpeech;
  }

  /**
   * Takes in a string message and uses text to speech to speak the message.
   *
   * @param msg The message to be spoken.
   */
  protected void speak(String msg) {

    // Do task in background so it doesn't freeze GUI
    Task<Void> backgroundTask =
        new Task<>() {

          @Override
          protected Void call() throws Exception {
            // run text to speech
            textToSpeech = new TextToSpeech();
            // read the message that is sent to this method
            textToSpeech.speak(msg);
            return null;
          }
        };
    // run thread to make sure GUI does not freeze
    Thread backgroundPerson = new Thread(backgroundTask);
    backgroundPerson.start();
  }

  /** Called when the game is left mid way through stops game from running */
  @Override
  public void onSwitchOut() {
    // terminate any unfinished game
    timerSoundEffect.stopSound();
    SoundEffects.playBackgroundMusic();

    if (!(timeline.getStatus() == Animation.Status.STOPPED)) {
      timeline.stop();
    }
  }
}
