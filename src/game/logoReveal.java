package game;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.Serializable;

class logoReveal implements Serializable {

    private Map map;
    private Stage stage;
    logoReveal(Map map) throws Exception{

        this.map = map;
        this.stage = this.map.STAGE();

        stage.setHeight(1000);
        stage.setWidth(1000);
        File f = new File( "LogoReveal.mp4");
        Media media = new Media(f.toURI().toURL().toString());
        javafx.scene.media.MediaPlayer player = new   javafx.scene.media.MediaPlayer(media);
        MediaView viewer = new MediaView(player);

        player.setOnEndOfMedia(() ->{
            try{
                stage.setHeight(1080);
                stage.setWidth(1920);
                stage.setX(0);
                stage.setY(0);
                this.nextWindow();

            } catch (Exception e){
               e.printStackTrace();
            }
        });

        StackPane root = new StackPane();
        Scene scene = new Scene(root,1000,1000);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(viewer);
        stage.setScene(scene);
        stage.show();
        player.play();
        this.map.getBackgroundAudio().play();
    }

    private void nextWindow() throws Exception{
        mainMenu menu = new mainMenu(this.map);
        menu.startMenu();
        System.out.println("HERE");

    }
}
