/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carl.assignment2;

import com.carl.assignment2.CarModel.CarType;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author carln
 */
public class CarRaceModel {
    private List<CarModel> cars;
    private List<CarModel> finishOrder;
    private double finishLineX;

    public CarRaceModel(double finishLineX) {
        this.finishLineX = finishLineX;
        this.cars = new ArrayList<>();
        this.finishOrder = new ArrayList<>();
        createCars();
    }

    /**
     * Creates the 4 cars, one per lane
     */
    private void createCars() {
        cars.add(new CarModel(CarType.HONDA, 45, 0, 20, 1.0, Color.RED));
        cars.add(new CarModel(CarType.TOYOTA, 54, 0, 100, 1.0, Color.YELLOW));
        cars.add(new CarModel(CarType.SUZUKI, 27, 0, 180, 1.0, Color.GREEN));
        cars.add(new CarModel(CarType.HYUNDAI, 15, 0, 260, 1.0, Color.BLUE));
    }

    /**
     * method that updates the x position of the car
     * @param car the car model
     * @param x the position in the x coordinate
     */
    public void updateCarPosition(CarModel car, double x) {
        car.setCoordinateX(x);
    }

    /**
     * Adds the car to the finishing order (only once)
     * @param car the car that crossed the finish line
     */
    public void recordFinish(CarModel car) {
        if (!finishOrder.contains(car)) {
            finishOrder.add(car);
        }
    }

    /**
     * @return the first car to finish, or null if none yet
     */
    public CarModel getWinner() {
        if (finishOrder.isEmpty()) {
            return null;
        }
        return finishOrder.get(0);
    }

    /**
     * @return true when every car has finished
     */
    public boolean isRaceOver() {
        return finishOrder.size() == cars.size();
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public List<CarModel> getFinishOrder() {
        return finishOrder;
    }

    public double getFinishLineX() {
        return finishLineX;
    }

    /**
     * Clears the results and puts every car back at the start
     */
    public void reset() {
        finishOrder.clear();
        for (CarModel car : cars) {
            car.reset();
        }
    }
}
