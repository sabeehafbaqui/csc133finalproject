package com.example.a3submission;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.Random;

class Cloud extends GameObject implements Updatable {

    Ellipse e;
    GameText percent;
    Random r;
    int low = 800 / 3;
    int lowWidth;
    int high;
    int highWidth;
    double result;
    double resultWidth;

    Point2D loc;
    int rgbColor = 255;
    double saturation = 0;
    Boolean isRaining = false;

   // Media sound;
   // MediaPlayer mediaPlayer;


    Cloud() {
        alive = true;
        e = new Ellipse(60,  60);
        r = new Random();
        high = Game.GAME_HEIGHT - (int) e.getRadiusY();
        lowWidth = (int) e.getRadiusX();
        highWidth = Game.GAME_WIDTH - (int) e.getRadiusX();
        result = r.nextInt(high - low) + low;
        resultWidth = r.nextInt(highWidth - lowWidth) + lowWidth;
        loc = new Point2D(resultWidth, result );
        e.setFill(Color.rgb(rgbColor, rgbColor, rgbColor));
        add(e);
        translate(loc.getX(), loc.getY());
        percent = new GameText((int)saturation, true);
        add(percent);
        percent.translate(-8, -5);

     //   sound = new Media(new File("sounds/thunder.mp3").toURI().toString());
       // mediaPlayer = new MediaPlayer(sound);

    }

    public void updateClosestPond(Pond p){

    }

    public void update() {
        resultWidth += Game.WIND_SPEED;
        result += Game.WIND_SPEED;
        loc = new Point2D(resultWidth, result);
        translate(loc.getX(), loc.getY());
        if (result >= Game.GAME_HEIGHT || resultWidth >= Game.GAME_WIDTH){
            resultWidth = -30;
            result = 0 - (Math.random() * 400);
            loc = new Point2D(resultWidth, result);
        }
    }




    public void seedClouds(Boolean seeding){
        if(seeding == true){
            if (saturation < 100) {
                rgbColor -= 1;
                e.setFill(Color.rgb(rgbColor, rgbColor, rgbColor));
                saturation++;
                percent.setText((int)saturation);
                if (saturation == 30) {
                    isRaining = true;
                }
            }
        }
        if(seeding == false){
            if (saturation > 0){
                if (rgbColor < 255){
                    rgbColor += 0.04;
                }
                e.setFill(Color.rgb(rgbColor,rgbColor,rgbColor));
                saturation -= 0.04;
                percent.setText((int) saturation);
                if (saturation < 30){
                    isRaining = false;
                }
            }
        }
    }


    public Point2D getLocation(){
        return loc;
    }


    public double getSaturation(){
        return saturation;
    }



    @Override
    Shape getShapeBounds() {
        return e;
    }

}