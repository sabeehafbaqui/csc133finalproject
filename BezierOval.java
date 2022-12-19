package com.example.a3submission;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Shape;

public class BezierOval {

    Ellipse bezier;
    QuadCurve q;

    public BezierOval() {
        bezier = new Ellipse();
        q = new QuadCurve();
        q.setStartX(0.0f);
        q.setStartY(50.0f);
        q.setEndX(50.0f);
        q.setEndY(50.0f);
        q.setControlX(25.0f);
        q.setControlY(0.0f);

        q.setFill(Color.DEEPPINK);

    }

}