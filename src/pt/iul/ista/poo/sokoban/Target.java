package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class Target extends AbstractObject implements ActiveObject{

	public Target(Position position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Alvo";
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public boolean canMove(Position position, Sokoban sokoban, Direction direction) {
		return true;
	}

	@Override
	public void move(Position position, Sokoban sokoban, Direction direction) {
		return;
	}

}
