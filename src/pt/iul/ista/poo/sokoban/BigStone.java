package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class BigStone extends AbstractObject{

    public BigStone(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "BigStone";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public boolean canMove(Position position, Sokoban sokoban, Direction direction) {
        for (AbstractObject i : sokoban.getObjects()) {
            if (i.getName().equals("Buraco") && i.getPosition().equals(this.position)){
                return false;
            }
            else if (i.getPosition().equals(position) && equalsNotPassableObject(i, sokoban.getNotPassableObjects()))
                return false;
        }
        return true;
    }

    @Override
    public void move(Position position, Sokoban sokoban, Direction direction) {
        this.position = position;
    }

    private boolean equalsNotPassableObject(AbstractObject object, ArrayList<String> notPassableObjects){
        for (String a : notPassableObjects)
            if (object.getName().equals(a))
                return true;
        return false;
    }

}
