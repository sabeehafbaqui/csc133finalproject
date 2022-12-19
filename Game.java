package com.example.a3submission;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.*;

public class Game extends Pane {

    static final int GAME_HEIGHT = 600;
    static final int GAME_WIDTH = 500;
    static final double WIND_SPEED = .3;

    Set<KeyCode> keysDown = new HashSet<>();

    int key(KeyCode k) {
        return keysDown.contains(k) ? 1 : 0;
    }

    Helipad helipad;

    Helicopter helicopter;


    GameText gt;

    BackGroundImage bg;


    Pond[] pond = new Pond[]{new Pond(),new Pond(), new Pond()};

    ArrayList<Cloud> cloud = new ArrayList<>(Arrays.asList(new Cloud(), new Cloud(),
            new Cloud(), new Cloud()));

    Iterator<Cloud> it = cloud.iterator();

    Blimp blimp;


    Distance[] distances = new Distance[]{
            new Distance(cloud.get(0).getLocation(), pond[0].getLocation()), // index 0
            new Distance(cloud.get(0).getLocation(), pond[1].getLocation()), // index 1
            new Distance(cloud.get(0).getLocation(), pond[2].getLocation()), // index 2
            new Distance(cloud.get(1).getLocation(), pond[0].getLocation()), // index 3
            new Distance(cloud.get(1).getLocation(), pond[1].getLocation()), // index 4
            new Distance(cloud.get(1).getLocation(), pond[2].getLocation()), // index 5
            new Distance(cloud.get(2).getLocation(), pond[0].getLocation()), // index 6
            new Distance(cloud.get(2).getLocation(), pond[1].getLocation()), // index 7
            new Distance(cloud.get(2).getLocation(), pond[2].getLocation()), // index 8
            new Distance(cloud.get(3).getLocation(), pond[0].getLocation()), // index 9
            new Distance(cloud.get(3).getLocation(), pond[1].getLocation()), // index 10
            new Distance(cloud.get(3).getLocation(), pond[2].getLocation())  // index 11
    };

    double totalS;


    public Game() throws FileNotFoundException {
        bg = new BackGroundImage();
        helipad = new Helipad();
        helicopter = new Helicopter();
        blimp = new Blimp();
        getChildren().addAll(bg, blimp, helipad);
        for(Pond p: pond){
            getChildren().addAll(p);
        }
        for(Cloud c: cloud){
            getChildren().addAll(c);
        }
        for(Distance d: distances){
            getChildren().addAll(d);
        }
        getChildren().add(helicopter);
    }

    public void handleKeyPressed(KeyEvent e) {
        keysDown.add(e.getCode());
    }

    public void handleKeyReleased(KeyEvent e) {
        keysDown.remove(e.getCode());
    }

    public void checkGameStatus(){
        if(helicopter.getFuel() == 0){
            helicopter.alive = false;
            gt = new GameText("Game is Over! \n Press 'R' to restart");
            gt.translate((GAME_WIDTH / 2) - 80, GAME_HEIGHT / 2 + 30);
            gt.changeColor(Color.INDIANRED);
            getChildren().removeAll(helicopter, helipad);
            getChildren().add(gt);
        }
        if(helicopter.getFuel() > 0 && helicopter.alive && totalS >= 300 * 0.8 &&
                helicopter.intersects(helipad) && !helicopter.engOn){
            gt = new GameText("You won! \n 'R' to continue");
            gt.translate((GAME_WIDTH / 2) - 120, (GAME_HEIGHT / 2) - 30);
            gt.changeColor(Color.CADETBLUE);
            getChildren().add(gt);
        }
        totalS = pond[0].getRadius() + pond[1].getRadius() + pond[2].getRadius();
    }

    public void updateClouds(){

    }

    public void updateDistance(){
        distances[0].update(cloud.get(0), pond[0]);
        distances[1].update(cloud.get(0), pond[1]);
        distances[2].update(cloud.get(0), pond[2]);
        distances[3].update(cloud.get(1), pond[0]);
        distances[4].update(cloud.get(1), pond[1]);
        distances[5].update(cloud.get(1), pond[2]);
        distances[6].update(cloud.get(2), pond[0]);
        distances[7].update(cloud.get(2), pond[1]);
        distances[8].update(cloud.get(2), pond[2]);
        distances[9].update(cloud.get(3), pond[0]);
        distances[10].update(cloud.get(3), pond[1]);
        distances[11].update(cloud.get(3), pond[2]);
    }


    public void init() throws FileNotFoundException {
        getChildren().removeAll(helicopter, helipad, blimp);
        for(Cloud c: cloud){
            getChildren().remove(c);
        }
        for(Distance d: distances){
            getChildren().remove(d);
        }
        for(Pond p: pond){
            getChildren().remove(p);
        }
        getChildren().remove(gt);
        cloud.clear();
        pond = new Pond[]{new Pond(),new Pond(), new Pond()};
        //clouds = new Cloud[]{new Cloud(), new Cloud(), new Cloud(), new Cloud()};
        helicopter = new Helicopter();
        helipad = new Helipad();
        blimp = new Blimp();
        cloud = new ArrayList<>(Arrays.asList(new Cloud(), new Cloud(),
                new Cloud(), new Cloud()));
        distances = new Distance[]{
                new Distance(cloud.get(0).getLocation(), pond[0].getLocation()),
                new Distance(cloud.get(0).getLocation(), pond[1].getLocation()),
                new Distance(cloud.get(0).getLocation(), pond[2].getLocation()),
                new Distance(cloud.get(1).getLocation(), pond[0].getLocation()),
                new Distance(cloud.get(1).getLocation(), pond[1].getLocation()),
                new Distance(cloud.get(1).getLocation(), pond[2].getLocation()),
                new Distance(cloud.get(2).getLocation(), pond[0].getLocation()),
                new Distance(cloud.get(2).getLocation(), pond[1].getLocation()),
                new Distance(cloud.get(2).getLocation(), pond[2].getLocation()),
                new Distance(cloud.get(3).getLocation(), pond[0].getLocation()),
                new Distance(cloud.get(3).getLocation(), pond[1].getLocation()),
                new Distance(cloud.get(3).getLocation(), pond[2].getLocation())
        };
        gt = new GameText("");

        getChildren().addAll(blimp, helipad);
        for(Pond p: pond){
            getChildren().add(p);
        }
        for(Cloud c: cloud){
            getChildren().add(c);
        }
        for(Distance d: distances){
            getChildren().add(d);
        }
        getChildren().add(helicopter);
    }


}