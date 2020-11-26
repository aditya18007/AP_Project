package game;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;


public abstract class Zombie extends mortalObject{

    boolean pause = false;
    int doesDamage ;
    TranslateTransition t;
    PauseTransition p;
    int enemyIndex;
    Duration delay;
    Group Root;
    boolean canEatBrain = false;
    boolean isCounted = false;
    boolean TranslatePaused = false;
    boolean WaitPaused = false;

    Zombie(String type , int toDeath , double Delay ,int row , Group Root){
        super(toDeath,row,Root);
        this.canEatBrain = false;
        this.row = row;
        this.type = type;
        this.setHeight(200);
        this.setWidth(120);
        this.delay = new Duration(Delay*1000);
        this.Root = Root;
        this.setVisible(false);
        this.isActive = false;
        this.isWaiting = true;
        this.isMoving = false;
        this.countdown = toDeath;
        this.row = row;
    }

    Zombie(String type,int toDeath , Duration Delay, int row , Group Root){
        super(toDeath,row,Root);
        this.canEatBrain = false;
        this.row = row;
        this.countdown = toDeath;
        this.row = row;
        this.setHeight(200);
        this.type = type;
        this.setWidth(120);
        this.delay = Delay;
        this.Root = Root;
        this.setVisible(false);
        this.isActive = false;
        this.isWaiting = true;
        this.isMoving = false;

    }

    boolean isCounted(){
        return isCounted;
    }

    void countDone(){
        isCounted = true;
    }

    void Pause(){
        if (this.isDead()){
            return;
        }
        if (this.isWaiting){
            this.p.pause();
            WaitPaused = true;
            return;
        }
        if (this.isMoving){
            this.t.pause();
            this.TranslatePaused = true;
            return;
        }

    }

    void Play(){
        if (this.isDead()){
            return;
        }
          if (this.WaitPaused){
              this.p.play();
              this.WaitPaused = false;
              return;
          }
          if (this.TranslatePaused){
              this.t.play();
              this.TranslatePaused = false;
              return;
          }
    }

    void Move(double distance){
        TranslateTransition translate = new TranslateTransition();
        this.t = translate;
        translate.setToX(-(distance));
        double result = (distance/1920.0)*50.0;
        translate.setDuration(Duration.seconds(result));
        translate.setNode(this);
        PauseTransition p = new PauseTransition(this.delay);
        this.p = p;
        p.setOnFinished(e ->{
            this.setVisible(true);
            this.isActive = true;
            this.isWaiting = false;
            this.isMoving = true;
            this.canEatBrain = false;
            translate.play();
        });
        p.play();
        translate.setOnFinished(r ->{
            this.canEatBrain = true;
        });
    }

    boolean canEatBrain(){
        return this.canEatBrain;
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
    int getRow(){
        return this.row;
    }
}

class footballZombie extends Zombie {

    footballZombie(int Y , double Delay , Group Root){
        super("FOOTBALLZOMBIE",28 ,Delay,Y,Root);
        this.setY(this.Ys[Y-1]-60);
        this.type = "FOOTBALLZOMBIE";
        this.setX(1900);
        this.setFill(new ImagePattern(new Image("media/zombie_football.gif")));
        this.doesDamage = 2;
        this.row = Y;
        this.Move(2200);
    }

    footballZombie(double x , double y , Duration delay , Group Root , int row){
        super("FOOTBALLZOMBIE",14,delay,row,Root);
        this.setY(y);
        this.setX(x);
        this.row = row;
        this.type = "FOOTBALLZOMBIE";
        this.setFill(new ImagePattern(new Image("media/zombie_football.gif")));
        this.doesDamage = 1;
        this.Move(x);
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

class normalZombie extends Zombie{

    normalZombie(int Y , double Delay , Group Root){
        super("NORMALZOMBIE",14,Delay,Y,Root);
        this.setY(this.Ys[Y-1]-50);
        this.row = Y;
        this.setX(1920);
        this.type = "NORMALZOMBIE";
        this.setFill(new ImagePattern(new Image("media/zombie_normal.gif")));
        this.doesDamage = 1;
        this.Move(2200);
    }

    normalZombie(double x , double y , Duration delay , Group Root , int row){
        super("NORMALZOMBIE",14,delay,row,Root);
        this.setY(y);
        this.setX(x);
        this.type = "NORMALZOMBIE";
        this.setFill(new ImagePattern(new Image("media/zombie_normal.gif")));
        this.doesDamage = 1;
        this.row = row;
        this.Move(x);
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
