package com.example.project2;//import com.example.project2;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

//You need this for GameObject. You can also use it if you want to make non-GameObject Groups that you can add to them that update each frame. I wouldn't bother with that.
interface Updatable{
    void update();
}

//This code is great. I'd leave it untouched.
class GameObject extends Group implements Updatable{
    protected Translate myTranslation;
    protected Rotate myRotation;
    protected Scale myScale;

    public GameObject(){
        myTranslation = new Translate();
        myRotation = new Rotate();
        myScale = new Scale();
        this.getTransforms().addAll(myTranslation,myRotation,myScale);
    }

    public void rotate(double degrees) {
        myRotation.setAngle(degrees);
        myRotation.setPivotX(0);
        myRotation.setPivotY(0);
    }

    public void scale(double sx, double sy) {
        myScale.setX(sx);
        myScale.setY(sy);
    }

    public void translate(double tx, double ty) {
        myTranslation.setX(tx);
        myTranslation.setY(ty);
    }

    public double getMyRotation(){
        return myRotation.getAngle();
    }

    public void update(){
        for(Node n : getChildren()){
            if(n instanceof Updatable)
                ((Updatable)n).update();
        }
    }

    void add(Node node) {
        this.getChildren().add(node);
    }
}

//This is a good example of how to make objects in your game.
class Helicopter extends GameObject{
    public Helicopter(){
        super();
        Ellipse helicopter = new Ellipse();	//It's creating an Ellipse and adding it to itself, so the ellipse will actually be drawn.
        helicopter.setRadiusX(10);
        helicopter.setRadiusY(10);
        helicopter.setFill(Color.BEIGE);
        add(helicopter);
    }
}


class Seed extends GameObject {
    public Seed() {
        Polygon polygon = new Polygon();
        polygon.setFill(Color.BLUE);
        polygon.getPoints().addAll(new Double[]{
                0.0, 20.0,
                -20.0, -20.0,
                20.0, -20.0});
        add(polygon);

        GameText text = new GameText(".");
        text.setTranslateY(100);
        text.setTranslateX(-350);
        text.setScaleX(4);
        add(text);

        this.getTransforms().clear();
        this.getTransforms().addAll(myRotation,myTranslation,myScale);
    }
    int offset = 1;
    public void update(){			//Here's one with an update() function. You use this if you want it to do something each frame.
        myTranslation.setY(myTranslation.getY() + offset);
        if(myTranslation.getY() > 40)
            offset = -offset;
        if(myTranslation.getY()<10)
            offset = -offset;
    }
}

//This looks good to keep if you want text in your game.
class GameText extends GameObject {
    Text text;

    public GameText(String textString){
        text = new Text(textString);
        text.setScaleY(-1);
        text.setFont(Font.font(25));
        add(text);
    }
    public GameText(){
        this("");
    }
    public void setText(String textString){
        text.setText(textString);
    }
}

class Water extends GameObject {
    public Water() {
        Helicopter myHelicopter = new Helicopter();
        myHelicopter.setScaleX(2.5);
        myHelicopter.setScaleY(1.5);

        add(makeSeed(0,40,0.25,1,0));
        add(makeSeed(0,40,0.25,1,-90));
        add(makeSeed(0,40,0.25,1,180));
        add(makeSeed(0,40,0.25,1,90));
        add(myHelicopter);
    }
    private Node makeSeed(double tx, double ty, double sx, double sy, int degrees){
        Seed seed = new Seed();
        seed.rotate(degrees);
        seed.translate(tx, ty);
        seed.scale(sx, sy);
        return seed;
    }

    public void left() {

    }

    public void right() {
    }
}

//This is the main class everything else runs from.
public class RainMaker extends Application {
    int counter=0;
    @Override
    public void start(Stage primaryStage) throws Exception {	//This method is basically like main(). It runs when the Application starts.
        Pane root = new Pane();							//This Pane is the root node, that will be added to the Scene later.

        //init(root);		//Why is it initialize twice? Just use one.
        init(root);

        root.setScaleY(-1);
        root.setTranslateX(250);
        root.setTranslateY(-250);
        Scene scene = new Scene(root, 500, 500);		//Creates a Scene, which is the main thing to worry about. Everything you make is added to the Scene, and it's where you put all the listeners.
        primaryStage.setScene(scene);
        primaryStage.setTitle("AffineFlameFX!");

        AnimationTimer timer = new AnimationTimer() {	//This runs each frame. Note that if you call update() on a GameObject, it will automatically call it on all children.
            @Override
            public void handle(long now) {
                if(counter++ %2==0) {
                    fo.rotate(fo.getMyRotation() + 1);
                    fo.update();
                }
            }
        };
        scene.setOnKeyPressed(e ->{						//This runs when you press a key. There's several similar functions in the Scene documentation. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html
            switch(e.getCode()){
                case    LEFT: fo.left();   break;
                case    RIGHT:fo.right();  break;
                default:
                    ;
            }
        });
        primaryStage.show();
        timer.start();

    }
    Water fo;

    public void init(Pane parent){					//This is for initializing the game. Create any GameObjects and add them to the parent.
        parent.getChildren().clear();
        fo = new Water();

        Line xAxis = new Line(-125,0,125,0);

       // GameText t = new GameText("More Text!");
        //t.translate(25,125);
        //parent.getChildren().add(t);

        Circle c = new Circle(2,Color.RED);
        c.setTranslateX(25);
        c.setTranslateY(125);

        parent.getChildren().add(c);
        parent.getChildren().add(xAxis);
        parent.getChildren().add(fo);
    }
}