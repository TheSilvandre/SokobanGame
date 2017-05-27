package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class Player extends AbstractObject implements ActiveObject{

	private String imageName;

	public Player(Position position) {
		super(position);
		imageName = "Empilhadora_U";
	}

	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public int getLevel() {
		return 2;
	}

	@Override
	public Position getPosition() {
		return super.getPosition();
	}

	public void setPosition(Position position) {
		super.position = position;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public void move(Position position, Sokoban sokoban, Direction direction){
		if (this.canMove(position, sokoban, direction)) {
			this.position = position;
			setImageName("Empilhadora_" + direction.toString().charAt(0));
			ImageMatrixGUI.getInstance().update();
			sokoban.setSteps(sokoban.getSteps() + 1);
			sokoban.setEnergy(sokoban.getEnergy() - 1);
		}
	}

	@Override
	public boolean canMove(Position position, Sokoban sokoban, Direction direction){
		for (AbstractObject i : sokoban.getObjects()) {
			if (i.getPosition().equals(position) && !(i.canMove(position.plus(direction.asVector()), sokoban, direction)))
				return false;
			if (i.getPosition().equals(position) && (i.canMove(position.plus(direction.asVector()), sokoban, direction)))
				i.move(position.plus(direction.asVector()), sokoban, direction);
		}
		return true;
	}
}
