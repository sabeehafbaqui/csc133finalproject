package com.example.a3submission;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class GameText extends GameObject {

    private Label w;
    Color color;
    Boolean isP;
    Boolean isF;

    GameText(int percent, Boolean isPercentage) {
        isP = isPercentage;
        if (isP) {
            w = new Label(percent + "%");
            isF = false;
        } else {
            w = new Label("F:" + percent);
            isF = true;
        }
        color = Color.BLUE;
        w.setTextFill(color);
        w.setScaleY(-1);
        add(w);
    }

    GameText(String text){
        isP = false;
        isF = false;
        w = new Label(text);
        color = Color.BLUE;
        w.setTextFill(color);
        w.setScaleY(-1);
        w.setFont(new Font("Robot", 30));
        add(w);
    }

    public void setText(int percent) {
        if(isP){
            w.setText(percent + "%");
        }
        if (isF){
            w.setText("F: " + percent);
        } else {
            w.setText(String.valueOf(percent));
        }
    }

    public void changeColor(Color c) {
        color = c;
        w.setTextFill(color);
    }

    @Override
    Shape getShapeBounds() {
        return null;
    }
}