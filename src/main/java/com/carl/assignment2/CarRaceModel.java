/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carl.assignment2;

import com.carl.assignment2.CarModel.CarType;
import java.util.ArrayList;
import java.util.List;

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
    }
    
        
}
