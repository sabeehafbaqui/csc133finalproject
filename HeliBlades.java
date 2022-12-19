package com.example.a3submission;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class HeliBlades extends GameObject {

    private Rectangle l1, l2;

    HeliBlades() {
        alive = true;
        l1 = new Rectangle(3, 30);
        l1.setScaleY(2);
        l1.setTranslateY(-15);
        l1.setTranslateX(-1);
        add(l1);
        l2 = new Rectangle(3, 30);
        l2.setScaleY(2);
        l2.setRotate(90);
        l2.setTranslateY(-15);
        translate(0, 5);
        add(l2);

    }



    @Override
    Shape getShapeBounds() {
        return l1;
    }

}