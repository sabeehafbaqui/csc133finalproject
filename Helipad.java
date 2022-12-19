package com.example.a3submission;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Helipad extends GameObject {

    Rectangle shapeBound;
    Boolean isS = false;


    Helipad() throws FileNotFoundException {
        alive = true;
        FileInputStream file = new FileInputStream("images/helipad.jpg");
        Image img = new Image(file);
        ImageView helipad = new ImageView(img);
        helipad.setFitWidth(80);
        helipad.setFitHeight(80);
        helipad.preserveRatioProperty();
        Rectangle rect = new Rectangle(helipad.getFitWidth() + 15, helipad.getFitWidth() + 15, Color.WHITE);
        rect.setTranslateX(-7);
        rect.setTranslateY(-7);

        shapeBound = new Rectangle(rect.getWidth() + 10, rect.getHeight() + 10);
        shapeBound.setStroke(Color.YELLOW);
        shapeBound.setFill(Color.rgb(255, 255, 255, 0.1));
        shapeBound.setStrokeWidth(3);
        shapeBound.setTranslateX(-12);
        shapeBound.setTranslateY(-12);

        shapeBound.setVisible(false);
        add(shapeBound);
        add(rect);
        add(helipad);
        translate(150, 30);
    }

    public void toggle(){
        isS = !isS;
    }

    public void update(){
        if(isS == false){
            shapeBound.setVisible(false);
        }
        if(isS == true){
            shapeBound.setVisible(true);
        }
    }

    @Override
    Shape getShapeBounds() {
        return shapeBound;
    }
}