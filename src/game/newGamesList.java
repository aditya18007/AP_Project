package game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class newGamesList {

    private final Stage STAGE;
    private Map map;
    private Scene newGameScene;
    private newGamesOptionHandler handler;
    public newGamesList(Map map) throws Exception{

        this.map = map;
        this.STAGE = new Stage();
        this.STAGE.initStyle(StageStyle.UNDECORATED);
        this.STAGE.setHeight(1000);
        this.STAGE.setWidth(1000);
        this.STAGE.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                this.STAGE.close();
                mainMenu.getRoot().setEffect(null);
            }
        });
        this.STAGE.initModality(Modality.APPLICATION_MODAL);
        this.STAGE.initOwner(this.map.STAGE());

            //See main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/newGamesList.fxml"));
        Parent root = loader.load();

        newGamesOptionHandler helper = (newGamesOptionHandler) loader.getController();
        this.handler = helper;
            this.handler.setMap(this.map);
            this.handler.setHelpBox(this);

            mainMenuHandler.dialog = this.STAGE;
            this.newGameScene = new Scene(root);
    }

    public void show(){
        this.STAGE.setScene(this.newGameScene);
        BoxBlur blur = new BoxBlur();
        blur.setHeight(1080.0);
        blur.setWidth(1920.0);
        blur.setInput(new GaussianBlur(100));
        this.STAGE.show();
        mainMenu.getRoot().setEffect(blur);
    }

    public  Stage STAGE(){
            return this.STAGE;
        }

    public Map getMap(){
            return this.map;
        }

}
