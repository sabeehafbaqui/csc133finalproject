package com.example.a1submission;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

interface Updatable {
    void update();
}

class GameText extends GameObject {

    private Label l;
    Color color;
    Boolean isP;

    GameText(int percent, Boolean isPercentage) {
        isP = isPercentage;
        if (isP) {
            l = new Label(percent + "%");
        } else {
            l = new Label("F:" + percent);
        }
        color = Color.BLUE;
        l.setTextFill(color);
        l.setScaleY(-1);
        add(l);
    }

    GameText(String text){
        isP = false;
        l = new Label(text);
        color = Color.BLUE;
        l.setTextFill(color);
        l.setScaleY(-1);
       // l.setFont(new Font("Robot", 30));
        add(l);
    }

    public void changeColor(Color c) {
        color = c;
        l.setTextFill(color);
    }

    public void setText(int percent) {
        if (isP) {
            l.setText(percent + "%");
        } else {
            l.setText("F: " + percent);
        }
    }

    @Override
    Shape getShapeBounds() {
        return null;
    }
}

abstract class GameObject extends Group {
    protected Translate myTranslation;
    protected Rotate myRotation;
    protected Scale myScale;
    Boolean ok;

    public GameObject() {
        myTranslation = new Translate();
        myRotation = new Rotate();
        myScale = new Scale();
        this.getTransforms().addAll(myTranslation, myRotation, myScale);
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

    public double getMyRotation() {
        return myRotation.getAngle();
    }

    public void update() {
        for (Node n : getChildren()) {
            if (n instanceof Updatable)
                ((Updatable) n).update();
        }
    }

    void add(Node node) {
        this.getChildren().add(node);
    }

    abstract Shape getShapeBounds();

    public boolean intersects(GameObject obj) {
        return ok && obj.ok &&
                !Shape.intersect(getShapeBounds(), obj.getShapeBounds()).getBoundsInLocal().isEmpty();
    }

}

class Pond extends GameObject implements Updatable {

    Ellipse ellipse;
    GameText percent;
    Random r;
    int low = 800 / 3;
    int lowWidth;
    int high;
    int highWidth;
    int result;
    int resultWidth;
    double radius;

    Pond() {
        ok = true;
        radius = Math.random() * 60;
        ellipse = new Ellipse(radius, radius);
        r = new Random();
        high = 800 - (int) ellipse.getRadiusY();
        lowWidth = (int) ellipse.getRadiusX();
        highWidth = 400 - (int) ellipse.getRadiusX();
        result = r.nextInt(high - low) + low;
        resultWidth = r.nextInt(highWidth - lowWidth) + lowWidth;
        ellipse.setFill(Color.LIGHTBLUE);
        add(ellipse);
        translate(resultWidth, result);
        percent = new GameText((int) radius, true);
        percent.changeColor(Color.WHITE);
        add(percent);
        percent.translate(-8, -5);
    }

    public void update() {
        result = r.nextInt(high - low) + low;
        resultWidth = r.nextInt(highWidth - lowWidth) + lowWidth;
        translate(resultWidth, result);
    }

    @Override
    Shape getShapeBounds() {
        return ellipse;
    }

}

class Cloud extends GameObject implements Updatable {

    Ellipse ellipse;
    GameText percent;
    Random r;
    int low = 800 / 3;
    int lowWidth;
    int high;
    int highWidth;
    int result;
    int resultWidth;
    int rgbColor = 255;
    int saturation = 0;
    Boolean isRaining = false;

    Cloud() {
        ok = true;
        ellipse = new Ellipse(60, 60);
        r = new Random();
        high = 800 - (int) ellipse.getRadiusY();
        lowWidth = (int) ellipse.getRadiusX();
        highWidth = 400 - (int) ellipse.getRadiusX();
        result = r.nextInt(high - low) + low;
        resultWidth = r.nextInt(highWidth - lowWidth) + lowWidth;
        ellipse.setFill(Color.rgb(rgbColor, rgbColor, rgbColor));
        add(ellipse);
        translate(resultWidth, result);
        percent = new GameText(saturation, true);
        add(percent);
        percent.translate(-8, -5);

    }

    public void update() {
        if (saturation < 100) {
            rgbColor -= 1;
            ellipse.setFill(Color.rgb(rgbColor, rgbColor, rgbColor));
            saturation++;
            percent.setText(saturation);
            if (saturation == 30) {
                isRaining = true;
            }
        }
    }

    @Override
    Shape getShapeBounds() {
        return ellipse;
    }

}

class Helipad extends GameObject {

    Ellipse ellipse;
    Rectangle rectangle;

    Helipad() {
        ok = true;
        ellipse = new Ellipse(30, 30);
        ellipse.setStroke(Color.GRAY);
        rectangle = new Rectangle(80, 80);
        rectangle.setStroke(Color.GRAY);
        add(rectangle);
        add(ellipse);
        ellipse.setTranslateX(40);
        ellipse.setTranslateY(40);
        translate(150, 20);
    }

    @Override
    Shape getShapeBounds() {
        return ellipse;
    }
}

class Helicopter extends GameObject implements Updatable {

    Ellipse e;
    Line l;
    Color color = Color.YELLOW;
    int fuel;
    GameText fuelText;
    int speed = 3;
    // double angle =
    int y = 60;
    int x = 190;
    Point2D location = new Point2D(x, y);
    double rotation;

    Helicopter() {
        ok = true;
        e = new Ellipse(10, 10);
        l = new Line(0, 0, 0, 25);
        fuel = 25000;
        //rotation = Math.toRadians(getMyRotation());
        fuelText = new GameText(fuel, false);
        e.setFill(color);
        l.setStroke(color);
        add(e);
        add(l);
        add(fuelText);
        fuelText.translate(-25, -30);
        fuelText.changeColor(Color.YELLOW);
        translate(location.getX(), location.getY());

    }

    @Override
    Shape getShapeBounds() {
        return e;
    }

    public void update() {
        location = location.add(speed * Math.sin(-1*Math.PI*getMyRotation()/180), speed *
                Math.cos(-1*Math.PI*getMyRotation()/180));
        translate(location.getX(), location.getY());
        if(fuel > 0){
            fuel -= 10;
            fuelText.setText(fuel);
        }
    }

    public void handleKeyPress(KeyEvent evt) {
        if (evt.getCode() == KeyCode.RIGHT) {
            rotate(getMyRotation() - 15);
        }

        if (evt.getCode() == KeyCode.LEFT) {
            rotate(getMyRotation() + 15);
        }
    }

}

class Game extends Pane {

    static final int GAME_WIDTH = 400;
    static final int GAME_HEIGHT = 800;

    Set<KeyCode> keysDown = new HashSet<>();

    int key(KeyCode k) {
        return keysDown.contains(k) ? 1 : 0;
    }

    Helipad helipad;
    Helicopter helicopter;
    Cloud cloud;
    Pond pond;
    GameText gameOver;

    public Game() {
        helipad = new Helipad();
        cloud = new Cloud();
        helicopter = new Helicopter();
        pond = new Pond();
        if (pond.resultWidth > cloud.resultWidth && pond.resultWidth < cloud.resultWidth + cloud.ellipse.getRadiusX()) {
            if (pond.result < cloud.result && pond.result > cloud.result + cloud.ellipse.getRadiusY()) {
                pond.update();
            }
        }
        getChildren().addAll(pond, cloud, helipad, helicopter);
        setPrefSize(400, 800);

    }

    public void handleKeyPressed(KeyEvent e) {
        keysDown.add(e.getCode());
    }

    public void handleKeyReleased(KeyEvent e) {
        keysDown.remove(e.getCode());
    }

    public void checkGameStatus(){
        if(helicopter.fuel == 0){
            gameOver = new GameText("Game Over!");
            gameOver.translate((GAME_WIDTH / 2) - 80, GAME_HEIGHT / 2 + 30);
            gameOver.changeColor(Color.RED);
            getChildren().removeAll(pond, cloud, helicopter, helipad);
            getChildren().add(gameOver);
        }
    }

}

public class GameApp extends Application {

    // private static final int GAME_WIDTH = 400;
    // private static final int GAME_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Game game = new Game();
        root.getChildren().add(game);
        Scene scene = new Scene(root, game.GAME_WIDTH, game.GAME_HEIGHT, Color.BLACK);
        scene.setOnKeyPressed(e -> {
            game.handleKeyPressed(e);
            game.helicopter.handleKeyPress(e);
            if (game.helicopter.intersects(game.cloud)) {
                if (game.key(KeyCode.SPACE) == 1) {
                    game.cloud.update();
                }
            }
        });

        scene.setOnKeyReleased(e -> {
            game.handleKeyReleased(e);
        });

        primaryStage.setTitle("GAME_WINDOW_TITLE");
        primaryStage.setScene(scene);
        game.setScaleY(-1);
        primaryStage.setResizable(false);
        primaryStage.show();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (game.key(KeyCode.UP) == 1) {
                    game.helicopter.update();
                }

                game.checkGameStatus();
            }
        };

        gameLoop.start();

    }

    public static void main(String[] args) {
        Application.launch();
    }

}
