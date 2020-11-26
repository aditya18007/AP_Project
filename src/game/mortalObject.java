package game;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.HashMap;

public class mortalObject extends Rectangle {

    int[] Xs = {174+(0*180) , 174+(1*180) , 174+(2*180) , 174+(3*180) , 174+(4*180) , 174+(5*180) , 174+(6*180) , 174+(7*180) , 174+(8*180) };
    int[] Ys = {102+(0*180) , 102+(1*180) , 102+(2*180) , 102+(3*180) , 102+(4*180)};
    HashMap<Double , Integer> rows;
    int countdown;
    int row;
    boolean isActive;
    Group Root;
    String type;
    boolean isWaiting = false;
    boolean isMoving = false;
    mortalObject(int countToDeath , int row ,Group Root){
        this.row = row;
        this.Root = Root;
        countdown = countToDeath;
        this.Root.getChildren().add(this);
        this.isActive = true;
        this.rows = new HashMap<>();
        this.rows.put(102.0+(0*180),1);
        this.rows.put(102.0+(1*180),2);
        this.rows.put(102.0+(2*180),3);
        this.rows.put(102.0+(3*180),4);
        this.rows.put(102.0+(4*180),5);
    }

    void setInactive(){
        this.isActive = false;
    }

    void takeHit(int damage){

        long min = Math.min(this.countdown,damage);
        this.countdown -= min;
        if (this.isDead()){
            this.Root.getChildren().remove(this);
            this.setInactive();
        }
    }

    boolean isDead(){
        return this.countdown == 0;
    }
}

class Test{

    public static void main(String[] args) {

    }
}

class mortalObjectSerializable implements Serializable{

    double currentX;
    double currentY;

    boolean isActive;
    int countDown;
    boolean isAttacking;

    int row;

    boolean isWaiting = false;
    boolean isMoving = false;
    Duration Delay = new Duration(0);
    String name;

    mortalObjectSerializable( Lawnmower o){
        this.currentX = o.getX() + o.getTranslateX();
        this.currentY = o.getY() ;
        this.isActive = o.isActive;
        this.countDown = o.getCountdown();
        this.row = o.getRow();
        this.name = o.type();
    }

    mortalObjectSerializable(Zombie o ){
        this.currentX = o.getX()+o.getTranslateX();
        this.currentY = o.getY() ;
        this.isActive = o.isActive;
        this.countDown = o.getCountdown();
        this.row = o.getRow();
        this.name = o.type();
        if (o.isWaiting){
            this.Delay = o.p.getTotalDuration().subtract(o.p.getCurrentTime());
        } else {
            if (this.isMoving){
                this.Delay = Duration.ZERO;
            }
        }
    }
    mortalObjectSerializable(Peashooter o ){
        this.currentX = o.getX() + o.getTranslateX();
        this.currentY = o.getY() ;
        this.isActive = o.isActive;
        this.countDown = o.getCountdown();
        this.row = o.getRow();
        this.name = o.type();
    }
    mortalObjectSerializable(PotatoMine o ){
        this.currentX = o.getX() + o.getTranslateX();
        this.currentY = o.getY() ;
        this.isActive = o.isActive;
        this.countDown = o.getCountdown();
        this.row = o.getRow();
        this.name = o.type();
    }
    mortalObjectSerializable(Sunflower o ){
        this.currentX = o.getX() + o.getTranslateX();
        this.currentY = o.getY() ;
        this.isActive = o.isActive;
        this.countDown = o.getCountdown();
        this.row = o.getRow();
        this.name = o.type();
    }
    mortalObjectSerializable(WallNut o ){
        this.currentX = o.getX() + o.getTranslateX();
        this.currentY = o.getY() ;
        this.isActive = o.isActive;
        this.countDown = o.getCountdown();
        this.row = o.getRow();
        this.name = o.type();
    }
    mortalObjectSerializable(Wallet o ){
        this.currentX = Integer.MAX_VALUE;
        this.currentY = Integer.MAX_VALUE ;
        this.isActive = false;
        this.countDown = o.getX();
        this.row = -1;
        this.name = o.getName();
    }
    mortalObjectSerializable(Progress o ){
        this.currentX = o.getDead();
        this.currentY = Integer.MAX_VALUE ;
        this.isActive = false;
        this.countDown = 0;
        this.row = -1;
        this.name = o.getName();
    }
}

class Wallet{
    int x;
    final String name = "WALLET";
    Wallet(int i){
        this.x = i;
    }

    int getX(){
        return this.x;//sounts sun
    }

    String getName(){
        return this.name;
    }
}
class Progress{

    double dead;
    final String name = "DEAD";
    Progress(double i){
        this.dead = i;
    }

    double getDead(){
        return this.dead;//sounts sun
    }

    String getName(){
        return this.name;
    }
}