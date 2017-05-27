package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class Battery extends AbstractObject implements ActiveObject{

	public Battery(Position position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Bateria";
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
		sokoban.playSound("eating");
		sokoban.energy += 11;
		if(sokoban.energy > 100)
			sokoban.energy = 100;
		ImageMatrixGUI.getInstance().setStatusMessage("Jogador: " + sokoban.getPlayerName() + "    Level: " + sokoban.getLevel() + "    Energy: " + sokoban.getEnergy() + "%");
		sokoban.indexToRemove = sokoban.getObjects().indexOf(this);
	}

}