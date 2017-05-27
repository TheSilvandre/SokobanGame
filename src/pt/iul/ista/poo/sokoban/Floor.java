package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class Floor extends AbstractObject{

	public Floor(Position position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Chao";
	}

	@Override
	public int getLevel() {
		return 0;
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
