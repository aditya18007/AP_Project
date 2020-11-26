package game;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class pauseMenuHandler extends clickable {

    private static boolean playOnce;
    private Map map;
    private pauseMenu menu;
    private Help h;
    private Level level;

    public void setMap(Map map , Level level){
        this.map = map;
        this.level = level;
    }

    public void setMenu(pauseMenu menu){
        this.menu = menu;
    }

    public void removeGlow(MouseEvent mouseEvent) {
        super.glowRemover(mouseEvent);
        playOnce = false;

    }

    public void makeGlow(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        Glow glow = new Glow();
        if (!playOnce){
            super.play();
            playOnce = true;
        }
        glow.setLevel(0.3);
        i.setEffect(glow);
    }


    public void closeButton(MouseEvent mouseEvent) {
        this.menu.STAGE().close();
        this.level.Root.setEffect(null);
        this.level.play();
        this.map.getBackgroundAudio().setMute(true);
    }

    public void saveGame(MouseEvent mouseEvent) {

        this.level.serialize();
        this.menu.STAGE().close();
        this.level.Root.setEffect(null);
        this.level.play();
        this.map.getBackgroundAudio().setMute(true);
    }

    public void loadHelp(MouseEvent mouseEvent) throws Exception{
        System.out.println("Show Help");
        if (this.h == null){
            this.h = new Help(this.map);
        }
        this.h.show();
    }

    public void loadMainMenu(MouseEvent mouseEvent) {
        this.map.getMainMenu().startMenu();
        this.menu.STAGE().close();
        this.map.getBackgroundAudio().setMute(false);
        this.level.Root.setEffect(null);
        this.map.getMainMenu().startMenu();
    }

    public void exitGame(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
