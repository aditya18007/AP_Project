package game;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class helpHandler extends clickable {

    private static boolean playOnce;
    private Map map;
    private Help helpBox;

    public void setMap(Map map){
        this.map = map;
    }

    public void setHelpBox(Help box){
        this.helpBox = box;
    }

    public void makeGlow(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        Glow glow = new Glow();
        if (!helpHandler.playOnce){
            super.play();
            helpHandler.playOnce = true;
        }
        glow.setLevel(0.3);
        i.setEffect(glow);
    }

    public void removeGlow(MouseEvent mouseEvent) {
        super.glowRemover(mouseEvent);
        playOnce = false;
    }

    public void closeButton(MouseEvent mouseEvent) {
        this.helpBox.STAGE().close();
        mainMenu.getRoot().setEffect(null);
    }
}
