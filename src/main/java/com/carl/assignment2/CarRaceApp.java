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
        CarRaceModel model = new CarRaceModel(570);
        CarRaceView view = new CarRaceView();
        view.buildRacePane(model.getCars());

        Scene scene = new Scene(view.getSlideshowPane(), 800, 520);
        CarRaceController controller = new CarRaceController(model, view, scene);

        stage.setTitle("CarRace simulation");
        stage.setScene(scene);
        stage.show();

        controller.startSlideshow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}