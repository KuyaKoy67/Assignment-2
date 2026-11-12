/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carl.assignment2;

import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author carln
 */
public class CarView extends Group {
    private Group wheel1;
    private Group wheel2;
    private RotateTransition spin1;
    private RotateTransition spin2;

    public CarView(Color color, int number) {
        Polygon body = new Polygon(0, 30, 10, 20, 130, 20, 140, 32, 140, 48, 0, 48);
        body.setFill(color);
        Polygon roof = new Polygon(30, 20, 48, 3, 100, 3, 118, 20);
        roof.setFill(color.darker());

        Rectangle window1 = new Rectangle(50, 7, 22, 11);
        Rectangle window2 = new Rectangle(76, 7, 22, 11);
        window1.setFill(Color.LIGHTSKYBLUE);
        window2.setFill(Color.LIGHTSKYBLUE);

        Rectangle plate = new Rectangle(55, 28, 30, 16);
        plate.setFill(Color.WHITE);
        Text numberText = new Text(59, 41, String.valueOf(number));
        numberText.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        wheel1 = createWheel(30, 50);
        wheel2 = createWheel(110, 50);

        getChildren().addAll(body, roof, window1, window2, plate, numberText, wheel1, wheel2);
    }
    
    /**
     * helper method that creates the wheels with the spoke line 
     * and the rim
     * @param centerX the center x
     * @param centerY the center y
     * @return the wheel in Group
     */
    private Group createWheel(double centerX, double centerY) {
        Circle tire = new Circle(centerX, centerY, 11, Color.BLACK);
        Circle rim = new Circle(centerX, centerY, 7, Color.LIGHTGRAY);
        Line spoke = new Line(centerX, centerY, centerX + 7, centerY);
        spoke.setStroke(Color.DARKGRAY);
        spoke.setStrokeWidth(2);
        return new Group(tire, rim, spoke);
    }
    
    /**
     * helper method that spins the wheels in animation
     */
    public void spinWheels() {
        spin1 = createSpin(wheel1);
        spin2 = createSpin(wheel2);
        spin1.play();
        spin2.play();
    }
    
    /**
     * helper method that calculates the amount of spin
     * @param wheel the wheel
     * @return the rotate translation 
     */
    private RotateTransition createSpin(Group wheel) {
        RotateTransition spin = new RotateTransition(Duration.millis(500), wheel);
        spin.setByAngle(360);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.setInterpolator(Interpolator.LINEAR);
        return spin;
    }
    
}
