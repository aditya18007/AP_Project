package game;

import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;

public class clickable {

    private static File mouseCLick;
    private static Media bgMusic;
    private static MediaPlayer backgroundAudio;

    static {

        try{
            mouseCLick = new File("mouseClick.mp3");
            bgMusic = new Media(mouseCLick.toURI().toURL().toString());
            backgroundAudio = new   javafx.scene.media.MediaPlayer(bgMusic);
            backgroundAudio.setStartTime(new Duration(1000));
            backgroundAudio.setStopTime(new Duration(1100));
            backgroundAudio.setOnEndOfMedia(() ->{
                backgroundAudio.stop();
            });


        } catch (MalformedURLException m){
            System.out.println("Error Loading File !");
        }
    }

    public static void play(){
        clickable.backgroundAudio.play();
    }

    public static void glowRemover(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        Glow glow = new Glow();
        glow.setLevel(0.0);
        i.setEffect(glow);
        InnerShadow shadow = new InnerShadow();
        shadow.setChoke(0.5);
        shadow.setHeight(30.0);
        shadow.setRadius(14.5);
        shadow.setWidth(30.0);
        i.setEffect(shadow);
    }
}
