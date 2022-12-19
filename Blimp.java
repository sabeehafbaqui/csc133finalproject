package com.example.a3submission;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
public class Blimp extends GameObject {

    Ellipse e;
    int fuel;
    Label fText;

    public Blimp() throws FileNotFoundException {
        FileInputStream file = new FileInputStream("images/blimp.png");
        Image blimppic = new Image(file);
        ImageView img = new ImageView(blimppic);
        img.setFitWidth(500);

        fuel = (int)Math.floor(Math.random() * (10000 - 5000) + 5000);
        fText = new Label(String.valueOf(fuel));
        e = new Ellipse(130, 30);
        e.setTranslateY(180);
        e.setTranslateX(247);

        img.setPreserveRatio(true);

        fText.setTranslateX(230);
        fText.setTranslateY(170);
        fText.setScaleY(-1);

        add(e);
        add(fText);
        add(img);
        setTranslateX(-Game.GAME_WIDTH / 2);
    }

    public void update(){
        setTranslateX(getTranslateX() + 0.5);
    }

    @Override
    Shape getShapeBounds() {
        return e;
    }

    int getFuel(){
        return fuel;
    }

}