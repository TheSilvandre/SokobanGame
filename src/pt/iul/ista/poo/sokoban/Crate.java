package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class Crate extends AbstractObject{

	public Crate(Position position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public boolean canMove(Position position, Sokoban sokoban, Direction direction) {
		for (AbstractObject i : sokoban.getObjects()) {
			if (i.getPosition().equals(position) && equalsNotPassableObject(i, sokoban.getNotPassableObjects()))
				return false;
			else if (i.getPosition().equals(position) && i.getName().equals("Buraco")) {
				sokoban.playSound("gameover");
				sokoban.gameOver();
				return false;
			}
		}
		return true;
	}

	@Override
	public void move(Position position, Sokoban sokoban, Direction direction) {
		sokoban.playSound("push");
		this.position = position;
	}

	public boolean onTopOfTarget(Sokoban sokoban){
		for(AbstractObject a: sokoban.getObjects()){
			if (a.getName().equals("Alvo") && a.getPosition().equals(position))
				return true;
		}
		return false;
	}

	private boolean equalsNotPassableObject(AbstractObject object, ArrayList<String> notPassableObjects){
		for (String a : notPassableObjects)
			if (object.getName().equals(a))
				return true;
		return false;
	}
}
