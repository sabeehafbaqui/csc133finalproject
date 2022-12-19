package com.example.a3submission;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Pond extends GameObject implements Updatable {

    Ellipse ellipse;
    Random r;
    GameText percent;
    int low = 800 / 3;
    int lowWidth;
    int highWidth;
    int high;
    Point2D result;
    double radius;

    Pond() {
        alive = true;
        radius = Math.random() * 60;
        ellipse = new Ellipse(radius, radius);
        r = new Random();
        high = 800 - (int) ellipse.getRadiusY();
        highWidth = Game.GAME_WIDTH - (int) ellipse.getRadiusX();
        lowWidth = (int) ellipse.getRadiusX();
        result = new Point2D((r.nextInt(highWidth - lowWidth) + lowWidth), (r.nextInt(high - low) + low) );
        ellipse.setFill(Color.BLUE);
        add(ellipse);
        translate(result.getX(), result.getY());
        percent = new GameText((int) radius, true);
        percent.changeColor(Color.WHITE);
        add(percent);
        percent.translate(-8, -5);
    }

    public void update() {
        result = new Point2D((r.nextInt(highWidth - lowWidth) + lowWidth), (r.nextInt(high - low) + low));
        translate(result.getX(), result.getY());
        if (radius < 100){
            radius++;
            ellipse.setRadiusY(radius);
            ellipse.setRadiusY(radius);
        }
    }

    public double getRadius(){
        return radius;
    }

    public void makeItRain(int sat){
        if (radius < 100){
            radius = radius + (sat * 0.001);
            ellipse.setRadiusX(radius);
            ellipse.setRadiusY(radius);
            percent.setText((int)radius);
        }
    }

    public Point2D getLocation(){
        return result;
    }

    @Override
    Shape getShapeBounds() {
        return ellipse;
    }

}