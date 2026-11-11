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
public class CarRaceModel {
    private CarType type;
    private int number;
    private double coordinateX;
    private double coordinateY;
    private double speed;
    private Color color;
    
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
