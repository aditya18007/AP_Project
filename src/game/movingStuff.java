package game;

import javafx.animation.Transition;

import java.util.ArrayList;

public abstract class movingStuff {

    protected ArrayList<Transition> currentTransition;
    public abstract void move();

    public void pause(){
        for (Transition t : currentTransition){
            if (t!= null){
                t.pause();
            }
        }
    }
    public void play(){
        for (Transition t : currentTransition){
            if (t!= null){
                t.play();
            }
        }
    }
}