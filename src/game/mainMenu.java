package game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainMenu {

    private static Parent root;
    private Scene scene;
    private Stage stage;
    private Map map;
    private mainMenuHandler handler ;

    public mainMenu(Map map) throws Exception{
        this.stage = map.STAGE();
        this.map = map;
        this.map.setMainMenu(this);
        this.handler = new mainMenuHandler();

        //THIS IS GEM.
        //WASTED TWO DAYS TO GET REFERENCE TO Controller Class of mainMenu.fxml that is being loaded
        //Lesson : NEVER COPY PASTE ANY CODE
        //I used to copy-paste how controller classes are loaded

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/mainMenu.fxml"));
        Parent root = loader.load();
        mainMenuHandler helper = (mainMenuHandler) loader.getController();

        this.root = root;
        Group x = new Group();
        this.handler = helper;
        this.handler.setMap(this.map);
        this.handler.setMenu(this);
        this.scene = new Scene(root);
    }

    public void startMenu(){
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public Map getMap() {
        return this.map;
    }

    public static Parent getRoot(){
        return mainMenu.root;
    }
}
