package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;
import java.util.ArrayList;

public interface ActiveObject {

    void move(Position position, Sokoban sokoban, Direction direction);

}