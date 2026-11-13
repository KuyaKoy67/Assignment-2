/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carl.assignment2;

import javafx.scene.paint.Color;

/**
 *
 * @author carln
 */
public class CarModel {
    private CarType type;
    private int number;
    private double coordinateX;
    private double coordinateY;
    private double startX;
    private double startY;
    private double speed;
    private Color color;

    public CarModel(CarType type, int number, double coordinateX, double coordinateY,
            double speed, Color color) {
        this.type = type;
        this.number = number;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.startX = coordinateX;
        this.startY = coordinateY;
        this.speed = speed;
        this.color = color;
    }

    /**
    * Puts the car back at its starting position
    */
    public void reset() {
        coordinateX = startX;
        coordinateY = startY;
    }
    
    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public double getSpeed() {
        return speed;
    }

    public Color getColor() {
        return color;
    }
    
    public enum CarType {
        HONDA, TOYOTA, SUZUKI, HYUNDAI
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public CarType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }
    
}
