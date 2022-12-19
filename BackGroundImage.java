package com.example.a3submission;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BackGroundImage extends com.example.a3submission.GameObject {

    BackGroundImage() throws FileNotFoundException {
        FileInputStream file = new FileInputStream("images/bg2.jpg");
        Image img = new Image(file);
        ImageView maps = new ImageView(img);
        setScaleY(-1.5);
        setScaleX(1.5);
        add(maps);
    }

    @Override
    Shape getShapeBounds() {
        return null;
    }
}