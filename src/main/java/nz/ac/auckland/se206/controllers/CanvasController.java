package nz.ac.auckland.se206.controllers;

import static nz.ac.auckland.se206.ml.DoodlePrediction.getFormattedPredictions;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.imageio.ImageIO;

import com.opencsv.exceptions.CsvException;

import ai.djl.ModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.Classifications.Classification;
import ai.djl.translate.TranslateException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.ml.DoodlePrediction;
import nz.ac.auckland.se206.speech.TextToSpeech;
import nz.ac.auckland.se206.words.WordHolder;

/**
 * This is the controller of the canvas. You are free to modify this class and
 * the corresponding FXML file as you see fit. For example, you might no longer
 * need the "Predict" button because the DL model should be automatically
 * queried in the background every second.
 *
 * <p>
 * !! IMPORTANT !!
 *
 * <p>
 * Although we added the scale of the image, you need to be careful when
 * changing the size of the drawable canvas and the brush size. If you make the
 * brush too big or too small with respect to the canvas size, the ML model will
 * not work correctly. So be careful. If you make some changes in the canvas and
 * brush sizes, make sure that the prediction works fine.
 */
public class CanvasController implements SwitchListener {

	@FXML
	private Canvas canvas;

	@FXML
	private Label wordLabel;

	@FXML
	private Label timerLabel;

	@FXML
	private Button clearButton;

	@FXML
	private Button eraserButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button newGameButton;

	@FXML
	private Label predictionsLabel;

	@FXML
	private Label resultLabel;

	private GraphicsContext graphic;
	private DoodlePrediction model;
	private int timeLeft;
	private boolean drawingStarted; // Tells label to update
	private Timeline timeline;
	private boolean gameWon;
	private TextToSpeech textToSpeech;

	/**
	 * JavaFX calls this method once the GUI elements are loaded. In our case we
	 * create a listener for the drawing, and we load the ML model.
	 *
	 * @throws ModelException     If there is an error in reading the input/output
	 *                            of the DL model.
	 * @throws IOException        If the model cannot be found on the file system.
	 * @throws URISyntaxException
	 * @throws CsvException
	 */
	public void initialize() throws ModelException, IOException, CsvException, URISyntaxException {

		drawingStarted = false; // stop predictions from taking place
		resetPredictionLabel(); // empty label when starting game

		model = new DoodlePrediction();

		graphic = canvas.getGraphicsContext2D();
		graphic.setLineWidth(4);
		graphic.setLineCap(StrokeLineCap.ROUND);

		/**
		 * @author pelgrim <https://stackoverflow.com/users/8937787/pelgrim>
		 * @copyright 2018 pelgrim
		 * @license CC BY-SA 3.0
		 * @see {@link https://stackoverflow.com/a/47284341/1248177|How to draw a
		 *      continuous line with mouse on JavaFX canvas?}
		 */
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				graphic.beginPath();
				graphic.moveTo(event.getX(), event.getY());
				graphic.stroke();
			}
		});

		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				graphic.lineTo(event.getX(), event.getY()); // draw path
				graphic.stroke(); // fill in path
				graphic.closePath();
				graphic.beginPath();
				graphic.moveTo(event.getX(), event.getY());
			}
		});

		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				graphic.lineTo(event.getX(), event.getY());
				graphic.stroke();
				graphic.closePath();
			}
		});

		// Tells label to update when user starts drawing
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				drawingStarted = true;
			}
		});

	}

	@Override
	public void onSwitch() {
		wordLabel.setText(WordHolder.getInstance().getCurrentWord()); // display new category
		resultLabel.setText(""); // reset win/lose indicator
		// hide end game buttons
		saveButton.setVisible(false);
		newGameButton.setVisible(false);
		// enable canvas and drawing buttons
		canvas.setDisable(false);
		clearButton.setDisable(false);
		eraserButton.setDisable(false);
		// reset to pen function
		graphic.setStroke(Color.BLACK);
		gameWon = false;
		onClear(); // clear canvas
		startTimer();
	}

	private void startTimer() {
		resetTimer();
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			// update predictions and timer
			if (drawingStarted) {
				onPredict(getCurrentSnapshot());
			}

			countDown();
		}));
		timeline.setCycleCount(Animation.INDEFINITE); // countdown value (seconds)
		timeline.play();
	}

	private void resetTimer() {
		timeLeft = 60;
		updateTimerDisplay(timeLeft);
	}

	private void resetPredictionLabel() {
		predictionsLabel.setText(" ");
	}

	private void countDown() {
		timeLeft--;
		updateTimerDisplay(timeLeft);
		if (timeLeft == 0) {
			endGame();
		}
	}

	private void updateTimerDisplay(int s) {
		timerLabel.setText(String.format("%02d:%02d", (s / 60), (s % 60)));
	}

	private void endGame() {
		timeline.stop(); // stop timer/prediction updates
		canvas.setDisable(true);
		clearButton.setDisable(true);
		eraserButton.setDisable(true);

		resetPredictionLabel();

		// display and announce a message based on game result
		if (gameWon) {
			resultLabel.setText("You win!");
			speak("Congratulations!");
		} else {
			resultLabel.setText("You lost!");
			speak("Maybe next time!");
		}

		saveButton.setVisible(true);
		newGameButton.setVisible(true);
	}

	@FXML
	private void onNewGame(ActionEvent event) {
		Object controller = SceneManager.getController(AppUi.CATEGORY_DISPLAY);
		if (controller instanceof SwitchListener switchListener) {
			switchListener.onSwitch();
		}

		drawingStarted = false; // stops making predictions until the user draws something

		// switch view
		Button button = (Button) event.getSource();
		Scene scene = button.getScene();
		scene.setRoot(SceneManager.getUiRoot(AppUi.CATEGORY_DISPLAY));
	}

	/** This method is called when the "Clear" button is pressed. */
	@FXML
	private void onClear() {
		graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	/**
	 * This method executes when the user clicks the "Predict" button. It gets the
	 * current drawing, queries the DL model and prints on the console the top 5
	 * predictions of the DL model and the elapsed time of the prediction in
	 * milliseconds.
	 *
	 * @throws TranslateException If there is an error in reading the input/output
	 *                            of the DL model.
	 */
	private void onPredict(BufferedImage canvasImg) {

		Task<Void> backgroundTask = new Task<>() {

			@Override
			protected Void call() throws Exception {
				List<Classification> predictions = model.getPredictions(canvasImg, 10);
				Platform.runLater(() -> {
					predictionsLabel.setText(getFormattedPredictions(predictions));
				});

				// update gameWon boolean if player has won after the last prediction update
				if (isWin(predictions)) {
					gameWon = true;
				}
				return null;
			}
		};

		// after prediction has finished, end game if player won
		backgroundTask.setOnSucceeded(event -> {
			if (gameWon) {
				endGame();
			}
		});

		Thread backgroundPerson = new Thread(backgroundTask);
		backgroundPerson.start();
	}

	/** Save the current snapshot as a file image. */
	@FXML
	private void onSave(ActionEvent event) {

		FileChooser savefile = new FileChooser();
		savefile.setTitle("Save File");

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
	 * Checks whether the player has won, i.e. whether the current word is in top 3
	 * predictions.
	 *
	 * @param classifications The list of predictions
	 * @return whether the player has won or not
	 */
	private boolean isWin(List<Classifications.Classification> classifications) {
		// go through top three predictions
		for (int i = 0; i < 3; i++) {
			if (classifications.get(i).getClassName().replaceAll("_", " ")
					.equals(WordHolder.getInstance().getCurrentWord())) {
				return true;
			}
		}
		return false;
	}

	@FXML
	private void onToggleErase() {
		graphic.setStroke(graphic.getStroke().equals(Color.BLACK) ? Color.WHITE : Color.BLACK);
	}

	/**
	 * Get the current snapshot of the canvas.
	 *
	 * @return The BufferedImage corresponding to the current canvas content.
	 */
	private BufferedImage getCurrentSnapshot() {
		final Image snapshot = canvas.snapshot(null, null);
		final BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

		// Convert into a binary image.
		final BufferedImage imageBinary = new BufferedImage(image.getWidth(), image.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);

		final Graphics2D graphics = imageBinary.createGraphics();

		graphics.drawImage(image, 0, 0, null);

		// To release memory we dispose.
		graphics.dispose();

		return imageBinary;
	}

	public TextToSpeech getTTS() {
		return textToSpeech;
	}

	/**
	 * Takes in a string message and uses text to speech to speak the message.
	 *
	 * @param msg The message to be spoken.
	 */
	private void speak(String msg) {

		// Do task in background so it doesn't freeze GUI
		Task<Void> backgroundTask = new Task<>() {

			@Override
			protected Void call() throws Exception {
				textToSpeech = new TextToSpeech();
				textToSpeech.speak(msg);
				return null;
			}
		};

		Thread backgroundPerson = new Thread(backgroundTask);
		backgroundPerson.start();
	}
}
