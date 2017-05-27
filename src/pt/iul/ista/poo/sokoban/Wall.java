package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class Wall extends AbstractObject{

	public Wall(Position position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Parede";
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public boolean canMove(Position position, Sokoban sokoban, Direction direction) {
		return false;
	}

	@Override
	public void move(Position position, Sokoban sokoban, Direction direction)  {
		return;
	}

}
