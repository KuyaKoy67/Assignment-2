package com.carl.assignment2;


import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.util.Duration;

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

    /**
     * Connects the buttons and the slider to their actions
     */
    private void setupControls() {
        view.getStartBtn().setOnAction(e -> startRace());
        view.getPauseBtn().setOnAction(e -> pauseRace());
        view.getResetBtn().setOnAction(e -> resetRace());
        view.getSpeedSlider().valueProperty().addListener(
                (obs, oldV, newV) -> changeSpeed(newV.doubleValue()));
    }

    private void startRace() {
        if (moves.isEmpty()) {
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
        } else {
            // events after pausing
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getStatus() == Animation.Status.PAUSED) {
                    moves.get(i).play();
                    view.getCarViews().get(i).resumeWheels();
                }
            }
        }
    }

    private void carFinished(CarModel car, CarView carView) {
        model.updateCarPosition(car, model.getFinishLineX());
        model.recordFinish(car);
        carView.stopWheels();
        //TODO: show results
    }

    /**
     * Shows the winner and the final order
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
     * Converts a color to its name for the messages
     * @param color the car color
     * @return the color name
     */
    private String getColorName(Color color) {
        if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.GOLD)) {
            return "Yellow";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        }
        return "Blue";
    }
}
