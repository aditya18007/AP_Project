package game;


import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;


public class Sun extends movingStuff {

    private int start_X;
    private final int start_Y = 105;

    private int mid_X;
    private int mid_Y;

    private int end_X;
    private int end_Y;

    private Label label;
    private Group group;
    private final Image image = new Image("media/sun.gif");
    private final ImageView imageView = new ImageView(image);
    private SequentialTransition seq;

    public Sun(Label label, Group group) {

        label.setText("0");
        this.imageView.setFitHeight(70);
        this.imageView.setFitWidth(70);
        this.label = label;
        this.group = group;

        ImageView i = this.imageView;
        EventHandler<? super MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setText(String.valueOf(Integer.valueOf(label.getText())+50));
                seq.stop();
                updateXY();
                group.getChildren().remove(i);
                move();
            }
        };
        this.imageView.setOnMouseClicked(eventHandler);
        Random r = new Random();
        this.start_X = r.nextInt(1450)+174;
        this.mid_X = this.start_X - 100;
        this.end_X = this.start_X + 100;

        this.mid_Y = this.start_Y + r.nextInt(300);
        this.end_Y = 2*this.mid_Y;
        currentTransition = new ArrayList<>();

    }

    public void move(){

        ImageView i = this.imageView;
        i.setVisible(false);
        Group g = this.group;

        final Path path = new Path();
        path.getElements().add(new MoveTo(this.start_X,this.start_Y));
        path.getElements().add(new CubicCurveTo(this.start_X, this.start_Y, this.mid_X, this.mid_Y, this.end_X, this.end_Y));
        path.setOpacity(0);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(8));
        pathTransition.setPath(path);
        pathTransition.setNode(this.imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        PauseTransition pauseInitial = new PauseTransition(new Duration(5000));

        pauseInitial.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                g.getChildren().remove(i);
            }
        });

        PauseTransition pause = new PauseTransition(Duration.millis(8000));
        PauseTransition pause_1 = new PauseTransition(new Duration(8000));

        pause_1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                i.setVisible(true);
            }
        });

        seq = new SequentialTransition(pause_1,pathTransition,pauseInitial,pause);
        currentTransition.add(seq);

        seq.play();
        seq.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                g.getChildren().remove(i);
                updateXY();
                move();
            }
        });

        this.group.getChildren().add(this.imageView);
        this.group.getChildren().add(path);
    }

    private void updateXY() {
        Random r = new Random();
        this.start_X = r.nextInt(1450)+174;
        this.mid_X = this.start_X - 100;
        this.end_X = this.start_X + 100;

        this.mid_Y = this.start_Y + r.nextInt(300);
        this.end_Y = 2*this.mid_Y;
    }

}

