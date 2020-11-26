package game;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;


class Lawnmower extends mortalObject {

    ArrayList<Zombie> enemies;
    Group Root;
    Timeline timeline ;
    boolean isRunning = false;
    private boolean isPaused = false;

    Lawnmower(int i , Group Root){

        super(Integer.MAX_VALUE,i,Root);
        this.type = "LAWNMOWER";
        this.Root = Root;
        this.setFill(new ImagePattern(new Image("media/lawnMover.png")));
        this.setX(20);
        this.setY(this.Ys[i-1]-30);
        this.setHeight(150);
        this.setWidth(200);
        timeline = new Timeline();
        double duration = 6;
        Rectangle shape = (Rectangle) this;
        KeyValue end = new KeyValue(this.translateXProperty(), (1700) , new Interpolator() {
            @Override
            protected double curve(double t) {

                for (Zombie enemy : enemies){
                    if (enemy.isActive && !enemy.isDead()) {
                        if (!Shape.intersect(shape, enemy).getBoundsInLocal().isEmpty()) {
                            enemy.takeHit(Integer.MAX_VALUE);//Random number
                        }
                    }
                }
                return t;
            }
        });

        KeyFrame endF = new KeyFrame(Duration.seconds(duration), end);
        timeline.getKeyFrames().addAll( endF);
        timeline.setAutoReverse(false);
        timeline.setCycleCount(1);
    }

    public void fire(){
        System.out.println("Should Move");;
        this.isRunning = true;
        Play();

        timeline.setOnFinished(e ->{
            Root.getChildren().remove(this);
            isActive = false;
            this.countdown = 0;
        });
        timeline.play();
    }

    void Pause(){
        if (this.isRunning)
        timeline.pause();
        this.isPaused = true;
    }

    void Play(){
        if (this.isRunning){
            if (this.isPaused)
                timeline.play();
        }
        this.isPaused = false;
    }

    int getRow(){
        return this.row;
    }

    String type(){
        return this.type;
    }
    int getCountdown(){
        return this.countdown;
    }
    void setRow(int t){
        this.row = t;
    }
    void setCountdown(int c){
        this.countdown = c;
    }
}

