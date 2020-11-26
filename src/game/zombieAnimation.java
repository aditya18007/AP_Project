package game;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class zombieAnimation extends movingStuff{

    private final Image moving;
    private final Image dying;

    private int start_x;
    private int start_y;
    private Group group;
    private ImageView imageView;

    public zombieAnimation(Image moving, Image dying, int row, Group parent) {
        this.group = parent;
        this.moving = moving;
        this.dying = dying;
        this.start_x = 1940;
        this.imageView = new ImageView();
        switch (row){

            case 0:
                this.start_y = 102;
                break;
            case 1:
                this.start_y = 282;
                break;
            case 2:
                this.start_y = 462;
                break;
            case 3:
                this.start_y = 642;
                break;
            case 4:
                this.start_y = 822;
                break;
        }
        currentTransition = new ArrayList<>();
    }

    public void move(){

        ImageView i = new ImageView();
        i.setX(this.start_x);
        i.setY(this.start_y);
        i.setFitHeight(250);
        i.setFitWidth(180);
        Random r = new Random();
        i.setVisible(true);
        PauseTransition StartPause = new PauseTransition();
        StartPause.setDuration(new Duration(0));

        Group tempG = this.group;

        StartPause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tempG.getChildren().add(i);
            }
        });

        TranslateTransition translate = new TranslateTransition(Duration.seconds(4));
        translate.setToX(-(1620+155));
        translate.setDuration(Duration.seconds(20));
        translate.setNode(i);
        i.setImage(moving);

        PauseTransition pause = new PauseTransition();
        pause.setDuration(new Duration(300));

        SequentialTransition s = new SequentialTransition(StartPause,translate,pause);
        currentTransition.add(s);
        translate.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                i.setImage(dying);
            }
        });
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                i.setImage(null);
            }
        });
        s.play();
        s.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tempG.getChildren().remove(i);
                move();
            }
        });
    }
}
