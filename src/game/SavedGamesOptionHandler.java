package game;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SavedGamesOptionHandler extends clickable {

    private static boolean playOnce;
    private Map map;
    private savedGames savedGames;

    public void setMap(Map map){
        this.map = map;
    }

    public void setHelpBox(savedGames box){
        this.savedGames = box;
    }

    public void makeGlow(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        Glow glow = new Glow();
        if (!SavedGamesOptionHandler.playOnce){
            super.play();
            SavedGamesOptionHandler.playOnce = true;
        }
        glow.setLevel(0.3);
        i.setEffect(glow);
    }

    public void removeGlow(MouseEvent mouseEvent) {
        super.glowRemover(mouseEvent);
        playOnce = false;
    }

    public void closeButton(MouseEvent mouseEvent) {
        savedGames.buttons[0].setText("Level One");
        savedGames.buttons[1].setText("Level Two");
        savedGames.buttons[2].setText("Level Three");
        savedGames.buttons[3].setText("Level Four");
        savedGames.buttons[4].setText("Level Five");
        this.savedGames.STAGE().close();
        mainMenu.getRoot().setEffect(null);
    }

    public void loadLevelFive(MouseEvent mouseEvent) {
        try{
            new FileInputStream("saveFive.txt");
            this.map.loadLevel(5);
            this.savedGames.STAGE().close();
            mainMenu.getRoot().setEffect(null);
            this.map.getBackgroundAudio().setMute(true);
        } catch (FileNotFoundException e){
            //Set button text : not saved
            savedGames.buttons[4].setText("Not Saved");
        }
    }

    public void loadLevelFour(MouseEvent mouseEvent) {
        try{
            new FileInputStream("saveFour.txt");
            this.map.loadLevel(4);
            this.savedGames.STAGE().close();
            mainMenu.getRoot().setEffect(null);
            this.map.getBackgroundAudio().setMute(true);
        } catch (FileNotFoundException e){
            //Set button text : not saved
            savedGames.buttons[3].setText("Not Saved");
        }
    }

    public void loadLevelOne(MouseEvent mouseEvent) {
        try{
            new FileInputStream("saveOne.txt");
            this.map.loadLevel(1);
            this.savedGames.STAGE().close();
            mainMenu.getRoot().setEffect(null);
            this.map.getBackgroundAudio().setMute(true);
        } catch (FileNotFoundException e){
            //Set button text : not saved
            savedGames.buttons[0].setText("Not Saved");
        }
    }

    public void loadLevelThree(MouseEvent mouseEvent) {
        try{
            new FileInputStream("saveThree.txt");
            this.map.loadLevel(3);
            this.savedGames.STAGE().close();
            mainMenu.getRoot().setEffect(null);
            this.map.getBackgroundAudio().setMute(true);
        } catch (FileNotFoundException e){
            //Set button text : not saved
            savedGames.buttons[2].setText("Not Saved");
        }
    }

    public void loadLevelTwo(MouseEvent mouseEvent) {
        try{
            new FileInputStream("saveTwo.txt");
            this.map.loadLevel(2);
            this.savedGames.STAGE().close();
            mainMenu.getRoot().setEffect(null);
            this.map.getBackgroundAudio().setMute(true);
        } catch (FileNotFoundException e){
            //Set button text : not saved
            savedGames.buttons[1].setText("Not Saved");
        }
    }
}
