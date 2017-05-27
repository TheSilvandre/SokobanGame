package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public abstract class AbstractObject implements ImageTile, ActiveObject{

	protected Position position;

	public AbstractObject(Position position) {
		this.position = position;
	}

	@Override
	public abstract String getName();

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public abstract int getLevel();

	public abstract boolean canMove(Position position, Sokoban sokoban, Direction direction);

	@Override
	public abstract void move(Position position, Sokoban sokoban, Direction direction);
}

