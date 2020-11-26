package game;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;


public class lawn {
    private Tile[][] tiles;
    private final Parent root;
    private final String[] romanNos;

    lawn(Parent root){
        this.root = root;
        this.tiles = new Tile[5][9];
        this.romanNos = new String[9];
        this.romanNos[0] = "One";
        this.romanNos[1] = "Two";
        this.romanNos[2] = "Three";
        this.romanNos[3] = "Four";
        this.romanNos[4] = "Five";
        this.romanNos[5] = "Six";
        this.romanNos[6] = "Seven";
        this.romanNos[7] = "Eight";
        this.romanNos[8] = "Nine";

        for (int x = 0 ; x < 5 ; x++){
            for (int y = 0 ; y < 9 ;y++){

                String id = "#"+this.romanNos[x]+this.romanNos[y];
                ImageView i = (ImageView) this.root.lookup(id);
                this.tiles[x][y] = new Tile(x,y,i);
            }
        }
    }

    public Tile getTile(int x , int y){
        return this.tiles[x][y];
    }
}
