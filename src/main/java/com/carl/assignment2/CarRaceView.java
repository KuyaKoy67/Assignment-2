/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carl.assignment2;

import java.util.List;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        "/downloads/Red Car.png",
        "/downloads/Yellow Car.png",
        "/downloads/Blue Car.png",
        "/downloads/Green Car.png"
    };
}
