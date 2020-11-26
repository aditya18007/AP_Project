package game;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class newGamesOptionHandler extends clickable {

    private static boolean playOnce;
    private Map map;
    private newGamesList newGames;

    public void setMap(Map map){
        this.map = map;
    }

    public void setHelpBox(newGamesList box){
        this.newGames = box;
    }

    public void makeGlow(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        Glow glow = new Glow();
        if (!newGamesOptionHandler.playOnce){
            super.play();
            newGamesOptionHandler.playOnce = true;
        }
        glow.setLevel(0.3);
        i.setEffect(glow);
    }

    public void removeGlow(MouseEvent mouseEvent) {
        super.glowRemover(mouseEvent);
        playOnce = false;
    }

    public void closeButton(MouseEvent mouseEvent) {
        this.newGames.STAGE().close();
        mainMenu.getRoot().setEffect(null);
    }

    public void newLevelFive(MouseEvent mouseEvent) {
        this.map.newLevel(5);
        this.newGames.STAGE().close();
        mainMenu.getRoot().setEffect(null);
        this.map.getBackgroundAudio().setMute(true);
    }

    public void newLevelFour(MouseEvent mouseEvent) {
        this.map.newLevel(4);
        this.newGames.STAGE().close();
        mainMenu.getRoot().setEffect(null);
        this.map.getBackgroundAudio().setMute(true);
    }

    public void newLevelOne(MouseEvent mouseEvent) {
        this.map.newLevel(1);
        this.newGames.STAGE().close();
        mainMenu.getRoot().setEffect(null);
        this.map.getBackgroundAudio().setMute(true);
    }

    public void newLevelThree(MouseEvent mouseEvent) {
        this.map.newLevel(3);
        this.newGames.STAGE().close();
        mainMenu.getRoot().setEffect(null);
        this.map.getBackgroundAudio().setMute(true);
    }

    public void newLevelTwo(MouseEvent mouseEvent) {

            this.map.newLevel(2);
            this.newGames.STAGE().close();
            mainMenu.getRoot().setEffect(null);
            this.map.getBackgroundAudio().setMute(true);

    }
}
