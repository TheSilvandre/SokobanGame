package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class SmallStone extends AbstractObject{

    public SmallStone(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "SmallStone";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public boolean canMove(Position position, Sokoban sokoban, Direction direction) {
        for (AbstractObject i : sokoban.getObjects()) {
            if (i.getPosition().equals(position) && i.getName().equals("Buraco")) {
                sokoban.indexToRemove = sokoban.getObjects().indexOf(this);
                return true;
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

