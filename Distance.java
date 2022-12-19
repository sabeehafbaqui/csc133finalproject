package com.example.a3submission;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class Distance extends GameObject implements Updatable {

    Line line;
    int dist;
    Label l;
    Point2D location;
    Boolean isShowing = false;

    public Distance(Point2D c, Point2D p){
        line = new Line(c.getX(), c.getY(), p.getX(), p.getY());
        location = new Point2D(line.getTranslateX(), line.getTranslateY());
        line.setStroke(Color.RED);
        dist = (int)Math.sqrt(Math.pow((p.getX() - c.getX()), 2) + Math.pow((p.getY() - c.getY()), 2));
        l = new Label(String.valueOf(dist));
        l.setFont(new Font(15));
        l.setTextFill(Color.WHITE);
        l.setScaleY(-1);
        l.setTranslateX(location.getX() + (p.getX() - c.getX()) / 2);
        l.setTranslateY(location.getY() + (p.getY() - c.getY()) / 2);
        getChildren().addAll(line, l);
    }

    public void update(Cloud c, Pond p){
        location = new Point2D(c.getLocation().getX(), c.getLocation().getY());
        line.setStartX(location.getX());
        line.setStartY(location.getY());
        dist = (int)Math.sqrt(Math.pow((p.getLocation().getX() - c.getLocation().getX()), 2) +
                Math.pow((p.getLocation().getY() - c.getLocation().getY()), 2));
        l.setTranslateX(location.getX() + (p.getLocation().getX() - c.getLocation().getX()) / 2);
        l.setTranslateY(location.getY() + (p.getLocation().getY() - c.getLocation().getY()) / 2);
        l.setText(String.valueOf(dist));
        if(isShowing){
            line.setVisible(true);
            l.setVisible(true);
        } else {
            line.setVisible(false);
            l.setVisible(false);
        }
    }

    public int returnDistance(){
        return dist;
    }

    public void toggle(){
        isShowing = !isShowing;
    }


    @Override
    Shape getShapeBounds() {
        return line;
    }
}