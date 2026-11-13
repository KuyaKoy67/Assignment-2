package com.carl.assignment2;


import javafx.animation.TranslateTransition;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class CarRaceController {
    private CarRaceModel model;
    private CarRaceView view;
    private Scene scene;
    private List<TranslateTransition> moves;

    public CarRaceController(CarRaceModel model, CarRaceView view, Scene scene) {
        this.model = model;
        this.view = view;
        this.scene = scene;
        this.moves = new ArrayList<>();
    }
}
