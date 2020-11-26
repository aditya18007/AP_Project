package game;

import javafx.scene.image.ImageView;

public class Tile {

    private int X;
    private int Y;
    private ImageView tile;

    public Tile(int x, int y, ImageView tile) {
        this.X = x;
        this.Y = y;
        this.tile = tile;
    }

    ImageView getImageView(){
        return this.tile;
    }
}
