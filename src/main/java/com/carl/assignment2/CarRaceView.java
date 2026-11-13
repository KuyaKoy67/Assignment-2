/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        carImageView = new ImageView();
        carImageView.setFitWidth(400);
        carImageView.setPreserveRatio(true);

        slideshowPane = new VBox(20, greetingLabel, carImageView);
        slideshowPane.setAlignment(Pos.CENTER);
    }
    
    /**
     * Method that shows the slideshow at the start of the application
     * and plays music
     */
    public void showSlideshow() {
        Media sound = new Media(getClass().getResource("introForProject.mp3").toExternalForm());
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
        
        slideshowTimeline.setOnFinished(e -> mediaPlayer.stop());

        slideshowTimeline.play();
    }
    
    /**
    * Builds the race screen: track, cars, controls and results area
    * @param cars the car models from the race model
    */
    public void buildRacePane(List<CarModel> cars) {
        // This is the track
        Pane track = new Pane();
        track.setPrefSize(800, 340);
        track.setStyle("-fx-background-color: #3a3a3a;");

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
        pauseBtn = new Button("Pause");
        resetBtn = new Button("Reset");
        HBox buttons = new HBox(10, startBtn, pauseBtn, resetBtn);

        speedSlider = new Slider(0.5, 3.0, 1.0);
        Label speedValue = new Label("1.0x");
        speedSlider.valueProperty().addListener((obs, oldV, newV) ->
            speedValue.setText(String.format("%.1fx", newV.doubleValue())));
        HBox speedBox = new HBox(10, new Label("Simulation Speed:"), speedSlider, speedValue);

        VBox controls = new VBox(15, buttons, speedBox);
        controls.setPadding(new Insets(15));
        controls.setPrefWidth(380);
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
