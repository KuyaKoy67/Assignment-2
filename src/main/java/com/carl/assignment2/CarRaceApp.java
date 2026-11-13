package com.carl.assignment2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author carln
 */
public class CarRaceApp extends Application {

    @Override
    public void start(Stage stage) {
        CarRaceView view = new CarRaceView();
        Scene scene = new Scene(view.getSlideshowPane(), 800, 500);

        stage.setTitle("CarRace simulation");
        stage.setScene(scene);
        stage.show();

        view.showSlideshow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}