package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;


public class Map extends Application implements Serializable {

    private User user;
    private MediaPlayer backgroundAudio;
    private Stage stage;
    private Scene currentLevel;
    private FXMLLoader LoaderOne;
    private FXMLLoader LoaderTwo;
    private FXMLLoader LoaderThree;
    private FXMLLoader LoaderFour;
    private FXMLLoader LoaderFive;
    private mainMenu mainMenu;
    String[] loaders;

    public Map() throws Exception{
        try{
            loaders = new String[5];



            loaders[0] = "/game/levelOne.fxml";

            loaders[1] = "/game/levelTwo.fxml";

            loaders[2] = "/game/levelThree.fxml";

            loaders[3] = "/game/levelFour.fxml";

            loaders[4] = "/game/levelFive.fxml";

        } catch (Exception e){
            System.out.println("FATAL ! FILE NOT FOUND");
            e.printStackTrace();
        }
    }

    public void start(Stage stage ) throws Exception{

        this.stage = stage;
        this.stage.initStyle(StageStyle.UNDECORATED);

        this.stage.setHeight(1000);
        this.stage.setWidth(1000);
        this.stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                this.stage.close();
            }
        });

        this.initializeBackgroundAudio();
        new logoReveal(this);
    }

    private void initializeBackgroundAudio() throws java.net.MalformedURLException{
        File audioFile = new File("background.mp3");
        Media bgMusic = new Media(audioFile.toURI().toURL().toString());
        this.backgroundAudio = new   javafx.scene.media.MediaPlayer(bgMusic);
        this.backgroundAudio.seek(Duration.seconds(2));
        this.backgroundAudio.setOnEndOfMedia(() -> {
            this.backgroundAudio.seek(Duration.ZERO);
        });
    }

    public void createNewGame(String[] args){
        launch(args);
    }

    public Stage STAGE(){
        return this.stage;
    }

    public MediaPlayer getBackgroundAudio(){
        return this.backgroundAudio;
    }

    public static void main(String[] args) throws Exception {

        Map map = new Map();
        map.createNewGame(args);
    }

    public void setMainMenu(mainMenu menu){
        this.mainMenu = menu;
    }

    public mainMenu getMainMenu(){
        return this.mainMenu;
    }


    void newLevel(int level){
        try{
            //loaders[0] = new FXMLLoader(getClass().getResource("/game/levelOne.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loaders[level-1]));
            Level l = new Level(this,this.stage,loader,level);
            l.map = this;
            l.add();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void loadLevel( int level){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loaders[level-1]));
            Level l = new Level(this,this.stage,loader,level);
            l.deserialize();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

