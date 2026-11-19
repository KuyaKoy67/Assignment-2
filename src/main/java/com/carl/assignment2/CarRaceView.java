package com.carl.assignment2;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * This is how the view of the race will look like
 *
 * @author carln
 */
public class CarRaceView {
    private List<CarView> carViews;
    private Button startBtn;
    private Button pauseBtn;
    private Button resetBtn;
    private Slider speedSlider;
    private Label resultsLabel;

    private VBox slideshowPane;
    private ImageView carImageView;
    private MediaPlayer mediaPlayer;
    private MediaPlayer takeoffPlayer;
    private Timeline slideshowTimeline;
    private int imageIndex = 0;
    private BorderPane racePane;

    private String[] carImages = {
            "Red Car.png",
            "Yellow Car.png",
            "Blue Car.png",
            "Green Car.png"
    };

    public CarRaceView() {
        Label greetingLabel = new Label("Hello and welcome to the Car Race!");
        greetingLabel.getStyleClass().add("greeting-label");

        carImageView = new ImageView();
        carImageView.setFitWidth(400);
        carImageView.setPreserveRatio(true);

        slideshowPane = new VBox(20, greetingLabel, carImageView);
        slideshowPane.setAlignment(Pos.CENTER);
        slideshowPane.getStyleClass().add("slideshow-pane");
    }

    /**
     * Method that shows the slideshow at the start of the application
     * and plays the guitar music
     */
    public void showSlideshow() {
        Media sound = new Media(getClass().getResource("guitar.wav").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0);
        mediaPlayer.play();

        Timeline fade = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(mediaPlayer.volumeProperty(), 1)),
                new KeyFrame(Duration.seconds(7), new KeyValue(mediaPlayer.volumeProperty(), 1)),
                new KeyFrame(Duration.seconds(8), new KeyValue(mediaPlayer.volumeProperty(), 0))
        );
        fade.play();

        imageIndex = 0;
        carImageView.setImage(new Image(getClass().getResourceAsStream(carImages[0])));

        slideshowTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            imageIndex++;
            if (imageIndex < carImages.length) {
                carImageView.setImage(new Image(getClass().getResourceAsStream(carImages[imageIndex])));
            }
        }));
        slideshowTimeline.setCycleCount(carImages.length);
        slideshowTimeline.play();
    }

    /**
     * Plays the takeoff audio/media when the race starts
     */
    public void playTakeoff() {
        if (takeoffPlayer != null) {
            takeoffPlayer.stop();
        }
        Media takeoffMedia = new Media(getClass().getResource("TakeOff.mp4").toExternalForm());
        takeoffPlayer = new MediaPlayer(takeoffMedia);
        takeoffPlayer.play();
    }

    /**
     * Stops the takeoff audio/media
     */
    public void stopTakeoff() {
        if (takeoffPlayer != null) {
            takeoffPlayer.stop();
        }
    }

    /**
     * pauses the take off music
     */
    public void pauseTakeoff() {
        if (takeoffPlayer != null) {
            takeoffPlayer.pause();
        }
    }

    /**
     * resumes the take off music
     */
    public void resumeTakeoff() {
        if (takeoffPlayer != null) {
            takeoffPlayer.play();
        }
    }

    /**
     * Builds the race screen: track, cars, controls and results area
     * @param cars the car models from the race model
     */
    public void buildRacePane(List<CarModel> cars) {
        // This is the track
        Pane track = new Pane();
        track.setPrefSize(800, 340);
        track.getStyleClass().add("track-pane");

        // These are the start and finish lines
        Rectangle startBar = new Rectangle(0, 0, 40, 340);
        startBar.setFill(Color.DARKGREEN);
        Rectangle finishBar = new Rectangle(760, 0, 40, 340);
        finishBar.setFill(Color.DARKGREEN);

        Text startText = new Text(14, 120, "S\nT\nA\nR\nT");
        Text finishText = new Text(774, 90, "A\nR\nR\nI\nV\nA\nL");
        for (Text t : new Text[]{startText, finishText}) {
            t.setFill(Color.WHITE);
            t.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            t.setTextAlignment(TextAlignment.CENTER);
        }
        track.getChildren().addAll(startBar, finishBar, startText, finishText);

        // These are for dashed lane lines
        for (int i = 1; i < 4; i++) {
            Line lane = new Line(40, 80 * i + 10, 760, 80 * i + 10);
            lane.setStroke(Color.WHITE);
            lane.getStrokeDashArray().addAll(15.0, 10.0);
            track.getChildren().add(lane);
        }

        // These are for cars
        carViews = new ArrayList<>();
        for (CarModel car : cars) {
            CarView carView = new CarView(car.getColor(), car.getNumber());
            carView.setLayoutX(50 + car.getCoordinateX());
            carView.setLayoutY(car.getCoordinateY());
            carViews.add(carView);
            track.getChildren().add(carView);
        }

        // Controls on the bottom left
        startBtn = new Button("Start");
        startBtn.setId("start-btn");

        pauseBtn = new Button("Pause");
        pauseBtn.setId("pause-btn");

        resetBtn = new Button("Reset");
        resetBtn.setId("reset-btn");

        HBox buttons = new HBox(10, startBtn, pauseBtn, resetBtn);

        speedSlider = new Slider(0.5, 3.0, 1.0);
        Label speedTitle = new Label("Simulation Speed:");
        speedTitle.getStyleClass().add("control-label");

        Label speedValue = new Label("1.0x");
        speedValue.getStyleClass().add("control-label");

        speedSlider.valueProperty().addListener((obs, oldV, newV) ->
                speedValue.setText(String.format("%.1fx", newV.doubleValue())));
        HBox speedBox = new HBox(10, speedTitle, speedSlider, speedValue);
        speedBox.setAlignment(Pos.CENTER_LEFT);

        VBox controls = new VBox(15, buttons, speedBox);
        controls.setPadding(new Insets(10));
        controls.setPrefWidth(380);

        // Results on the bottom right
        resultsLabel = new Label("Press Start to begin the race!");
        resultsLabel.setWrapText(true);
        resultsLabel.getStyleClass().add("results-label");

        VBox resultsBox = new VBox(resultsLabel);
        resultsBox.getStyleClass().add("results-box");
        resultsBox.setPrefWidth(400);

        HBox bottom = new HBox(20, controls, resultsBox);
        bottom.getStyleClass().add("bottom-panel");

        racePane = new BorderPane();
        racePane.getStyleClass().add("race-pane");
        racePane.setCenter(track);
        racePane.setBottom(bottom);
    }

    /**
     * Stops the slideshow sound
     */
    public void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public BorderPane getRacePane() {
        return racePane;
    }

    public List<CarView> getCarViews() {
        return carViews;
    }

    public Button getStartBtn() {
        return startBtn;
    }

    public Button getPauseBtn() {
        return pauseBtn;
    }

    public Button getResetBtn() {
        return resetBtn;
    }

    public Slider getSpeedSlider() {
        return speedSlider;
    }

    /**
     * Shows a message in the results area
     * @param text the message
     */
    public void setResults(String text) {
        resultsLabel.setText(text);
    }

    /**
     * getter method that returns the slideshowTimeline
     * @return the slideshowTimeline
     */
    public Timeline getSlideshowTimeline() {
        return slideshowTimeline;
    }

    /**
     * getter method that returns the slideshowPane
     * @return the slideshowPane
     */
    public VBox getSlideshowPane() {
        return slideshowPane;
    }
}