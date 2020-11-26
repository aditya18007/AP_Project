package game;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.util.Duration;
import java.util.ArrayList;

public class Plant extends mortalObject {

    ArrayList<Zombie> attacking = new ArrayList<>() ;
    Plant(int damage ,int row, Group Root) {
        super(damage,row,Root);
    }
}

class Peashooter extends Plant{

    ArrayList<Zombie> enemies;
    Group Root;
    boolean canShoot = false;
    Peashooter(int X , int Y , Group Root ){
        super(8,Y,Root);
        this.Root = Root;
        this.setX(this.Xs[X-1]);
        this.setY(this.Ys[Y-1]);
        canShoot = true;
        this.type = "PEASHOOTER";
        this.setFill(new ImagePattern(new Image("media/peashooter.gif")));
        this.setHeight(120);
        this.setWidth(104);
    }
    Peashooter(double x , double y ,int row,Group Root  ){
        super(8,row,Root);
        this.Root = Root;
        this.setX(x);
        this.setY(y);
        canShoot = true;
        this.type = "PEASHOOTER";
        this.setFill(new ImagePattern(new Image("media/peashooter.gif")));
        this.setHeight(120);
        this.setWidth(104);
    }
    void shoot(){
        if (!canShoot){
            return;
        }
        canShoot = false;
        Circle bullet = new Circle(this.getX()+100,this.getY()+20,10, Color.GREENYELLOW);
        Timeline timeline = new Timeline();
        double duration = (3.0/1900.0)*(1900-this.getX());
        Group Root = this.Root;
        KeyValue end = new KeyValue(bullet.translateXProperty(), (1900-bullet.getCenterX()) , new Interpolator() {
            @Override
            protected double curve(double t) {
                for (Zombie enemy : enemies){
                    if (enemy.isActive){
                        if (!Shape.intersect(bullet, enemy).getBoundsInLocal().isEmpty()) {
                            Root.getChildren().remove(bullet);
                            canShoot = true;
                            enemy.takeHit(1);
                            timeline.stop();
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
        timeline.setOnFinished(e ->{
            Root.getChildren().remove(bullet);
            canShoot = true;
        });

        this.Root.getChildren().add(bullet);
        timeline.play();
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

class PotatoMine extends mortalObject{


    PotatoMine(int rowX , int colY , Group Root) {
        super(Integer.MAX_VALUE,colY, Root);
        this.setX(this.Xs[rowX-1]);
        this.setY(this.Ys[colY-1]);
        this.row = colY;
        initialize();
    }

    PotatoMine(double x , double y , int row , Group Root){
        super(Integer.MAX_VALUE,row,Root);
        this.setX(x);
        this.setY(y);
        initialize();
    }

    void initialize(){
        this.type = "POTATOMINE";
        this.setFill(new ImagePattern(new Image("media/Potatomine.png")));
        this.setHeight(120);
        this.setWidth(120);
        this.isActive = false;
        PauseTransition p = new PauseTransition(Duration.seconds(10));
        p.setOnFinished(e ->{
            this.setEffect(new Glow(1));
        });
        PauseTransition q = new PauseTransition(Duration.millis(500));
        q.setOnFinished(t->{
            this.setEffect(new Glow(0));
            this.isActive = true;
        });
        SequentialTransition seq = new SequentialTransition(p,q);
        seq.play();
    }
    void blast() {
        this.setFill(new ImagePattern(new Image("media/blast.gif")));
        this.countdown = 0;
        PauseTransition p = new PauseTransition(Duration.millis(800));
        p.setOnFinished(t -> {
            this.Root.getChildren().remove(this);
            this.setInactive();
        });
        p.play();
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

class WallNut extends Plant{

    WallNut( int rowX , int colY , Group Root) {
        super(50,colY, Root);

        this.row = colY;
        this.setX(this.Xs[rowX-1]);
        this.setY(this.Ys[colY-1]);
        this.inititalize();
    }

    WallNut(double x , double y ,int row, Group root){
        super(50, row,root);
        this.setX(x);
        this.setY(y);
        inititalize();
    }

    void inititalize(){
        this.type = "WALLNUT";
        this.setHeight(120);
        this.setWidth(90);
        this.setFill(new ImagePattern(new Image("media/walnut_full_life.gif")));
    }
    void halfLife(){
        this.setFill(new ImagePattern(new Image("media/walnut_half_life.gif")));
    }

    void die(){

        this.setFill(new ImagePattern(new Image("media/walnut_dead.gif")));
        PauseTransition p =new PauseTransition(Duration.millis(800));
        p.setOnFinished(r->{
            this.setInactive();

            for (Zombie z : this.attacking){
                z.Play();
            }
            this.Root.getChildren().remove(this);
        });
        p.play();
    }

    void takeHit(int damage){
        long min = Math.min(this.countdown,damage);
        this.countdown -= min;

        if (this.countdown < 26){
            this.halfLife();
        }

        if (this.isDead()){
            this.die();
        }
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

class Sunflower extends Plant{

    Label label;

    Sunflower(int X , int Y , Group Root , Label label ){

        super(5,Y,Root);
        this.Root = Root;
        this.row = Y;
        this.setX(this.Xs[X-1]);
        this.setY(this.Ys[Y-1]);
        this.type = "SUNFLOWER";
        this.setFill(new ImagePattern(new Image("media/sunflower.png")));
        this.setHeight(120);
        this.setWidth(104);
        PauseTransition p = new PauseTransition(Duration.millis(800));
        p.setOnFinished(e ->{
            this.setEffect(new Glow(1));
        });
        PauseTransition q = new PauseTransition(Duration.millis(500));
        q.setOnFinished(t->{
            this.setEffect(new Glow(0));
            this.isActive = true;
        });
        SequentialTransition seq = new SequentialTransition(p,q);
        seq.play();
        Timeline ThrowSuns = new Timeline(
                new KeyFrame(Duration.seconds(20) , e ->{

                    if (this.isActive){
                        this.produce();
                        ImageView i = new ImageView(new Image("media/sun.gif"));
                        i.setFitHeight(70);
                        i.setFitWidth(70);
                        i.setX(this.getX());
                        i.setY(this.getY());
                        PauseTransition f = new PauseTransition(Duration.millis(5000));
                        f.setOnFinished(g ->{
                            Root.getChildren().remove(i);
                        });
                        f.play();
                        Root.getChildren().add(i);
                        i.setOnMouseClicked( o ->{
                            label.setText(Integer.toString(Integer.valueOf(label.getText())+50));
                            Root.getChildren().remove(i);
                        });
                    }
                })
        );
        ThrowSuns.setCycleCount(Timeline.INDEFINITE);
        ThrowSuns.play();
    }

    Sunflower(double x , double y ,int row, Group Root , Label label){
        super(5,row,Root);
        this.Root = Root;
        this.label = label;
        this.setX(x);
        this.setY(y);
        this.type = "SUNFLOWER";
        this.setFill(new ImagePattern(new Image("media/sunflower.png")));
        this.setHeight(120);
        this.setWidth(104);
        PauseTransition p = new PauseTransition(Duration.millis(800));
        p.setOnFinished(e ->{
            this.setEffect(new Glow(1));
        });
        PauseTransition q = new PauseTransition(Duration.millis(500));
        q.setOnFinished(t->{
            this.setEffect(new Glow(0));
            this.isActive = true;
        });
        SequentialTransition seq = new SequentialTransition(p,q);
        seq.play();
        Timeline ThrowSuns = new Timeline(
                new KeyFrame(Duration.seconds(20) , e ->{

                    if (this.isActive){
                        this.produce();
                        ImageView i = new ImageView(new Image("media/sun.gif"));
                        i.setFitHeight(70);
                        i.setFitWidth(70);
                        i.setX(this.getX());
                        i.setY(this.getY());
                        PauseTransition f = new PauseTransition(Duration.millis(5000));
                        f.setOnFinished(g ->{
                            Root.getChildren().remove(i);
                        });
                        f.play();
                        Root.getChildren().add(i);
                        i.setOnMouseClicked( o ->{
                            label.setText(Integer.toString(Integer.valueOf(label.getText())+50));
                            Root.getChildren().remove(i);
                        });
                    }
                })
        );
        ThrowSuns.setCycleCount(Timeline.INDEFINITE);
        ThrowSuns.play();
    }


    void produce(){
        PauseTransition p = new PauseTransition(Duration.millis(700));
        this.setEffect(new Glow(1));
        p.play();
        p.setOnFinished(e->{
            this.setEffect(new Glow(0));
        });
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