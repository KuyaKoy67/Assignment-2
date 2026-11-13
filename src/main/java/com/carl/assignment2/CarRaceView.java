/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carl.assignment2;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
