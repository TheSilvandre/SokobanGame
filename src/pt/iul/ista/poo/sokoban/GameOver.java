package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Position;

public class GameOver implements ImageTile {

    private String imageName;
    private Position position;

    public GameOver(int x, int y){
        imageName = "GameOver-"+x+"-"+y;
        position = new Position(y,x);
    }

    @Override
    public String getName() {
        return imageName;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public int getLevel() {
        return 0;
    }

}
