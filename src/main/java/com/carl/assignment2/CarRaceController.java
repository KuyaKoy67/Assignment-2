package com.carl.assignment2;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Controller class that coordinates user interactions, car movement animations,
 * speed adjustments, and race state updates between the model and view.
 *
 * @author carln
 */
public class CarRaceController {
    private CarRaceModel model;
    private CarRaceView view;
    private Scene scene;
    private List<TranslateTransition> moves;

    /**
     * Constructs a CarRaceController and binds UI controls to their event handlers
     * @param model the CarRaceModel containing race state and car data
     * @param view  the CarRaceView managing the UI and media playback
     * @param scene the primary JavaFX Scene
     */
    public CarRaceController(CarRaceModel model, CarRaceView view, Scene scene) {
        this.model = model;
        this.view = view;
        this.scene = scene;
        this.moves = new ArrayList<>();
        setupControls();
    }

    /**
     * Connects the control buttons and speed slider to their action handlers.
     */
    private void setupControls() {
        view.getStartBtn().setOnAction(e -> startRace());
        view.getPauseBtn().setOnAction(e -> pauseRace());
        view.getResetBtn().setOnAction(e -> resetRace());
        view.getSpeedSlider().valueProperty().addListener(
                (obs, oldV, newV) -> changeSpeed(newV.doubleValue()));
    }

    /**
     * Starts a new race with takeoff audio and car transitions, or resumes
     * paused animations if a race is already in progress.
     */
    private void startRace() {
        if (moves.isEmpty()) {
            // Play takeoff sound when the race starts
            view.playTakeoff();

            // This creates the necessary animation
            double rate = view.getSpeedSlider().getValue();

            for (int i = 0; i < model.getCars().size(); i++) {
                CarModel car = model.getCars().get(i);
                CarView carView = view.getCarViews().get(i);

                TranslateTransition move = new TranslateTransition(
                        Duration.seconds(6 + Math.random() * 4), carView);
                move.setToX(model.getFinishLineX());
                move.setInterpolator(Interpolator.LINEAR);
                move.setRate(rate);
                move.setOnFinished(e -> carFinished(car, carView));
                moves.add(move);

                carView.spinWheels();
                carView.setWheelRate(rate);
                move.play();
            }
            view.setResults("The race has started!");
        } else if (!model.isRaceOver()) {
            // Resume events after pausing
            view.resumeTakeoff();
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getStatus() == Animation.Status.PAUSED) {
                    moves.get(i).play();
                    view.getCarViews().get(i).resumeWheels();
                }
            }
        }
    }

    /**
     * Handles a car crossing the finish line, records its rank, stops its wheels,
     * and displays final results once all cars finish.
     * @param car the CarModel that finished
     * @param carView the corresponding CarView graphic
     */
    private void carFinished(CarModel car, CarView carView) {
        model.updateCarPosition(car, model.getFinishLineX());
        model.recordFinish(car);
        carView.stopWheels();
        if (model.isRaceOver()) {
            view.stopTakeoff();
            showResults();
        }
    }

    /**
     * Formats and displays the winning car and final finishing order in the view.
     */
    private void showResults() {
        CarModel winner = model.getWinner();
        String text = "Race Finished!\n"
                + "Congratulations! Car #" + winner.getNumber()
                + " (" + getColorName(winner.getColor()) + ") is the winner!\n\n"
                + "Final Results:\n";

        List<CarModel> order = model.getFinishOrder();
        for (int i = 0; i < order.size(); i++) {
            CarModel car = order.get(i);
            text += (i + 1) + ". Car #" + car.getNumber()
                    + " (" + getColorName(car.getColor()) + ")\n";
        }
        view.setResults(text);
    }

    /**
     * Starts the introductory slideshow and switches the scene root to the race
     * screen once the slideshow completes.
     */
    public void startSlideshow() {
        view.showSlideshow();
        view.getSlideshowTimeline().setOnFinished(e -> {
            view.stopSound();
            scene.setRoot(view.getRacePane());
        });
    }

    /**
     * Pauses all active car movements, wheel animations, and takeoff audio.
     */
    private void pauseRace() {
        view.pauseTakeoff();
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).getStatus() == Animation.Status.RUNNING) {
                moves.get(i).pause();
                view.getCarViews().get(i).pauseWheels();
            }
        }
    }

    /**
     * Stops all animations and audio, clears transitions, and returns all cars
     * to their starting positions.
     */
    private void resetRace() {
        view.stopTakeoff();
        for (TranslateTransition move : moves) {
            move.stop();
        }
        moves.clear();
        model.reset();
        for (CarView carView : view.getCarViews()) {
            carView.setTranslateX(0);
            carView.stopWheels();
        }
        view.setResults("Press Start to begin the race!");
    }

    /**
     * Updates the playback rate of all car movement and wheel spin animations.
     * @param rate the speed multiplier selected on the slider
     */
    private void changeSpeed(double rate) {
        for (TranslateTransition move : moves) {
            move.setRate(rate);
        }
        for (CarView carView : view.getCarViews()) {
            carView.setWheelRate(rate);
        }
    }

    /**
     * Converts a JavaFX Color object into a readable color name string.
     * @param color the Color of the car
     * @return the human-readable color name
     */
    private String getColorName(Color color) {
        if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.YELLOW)) {
            return "Yellow";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        }
        return "Blue";
    }
}