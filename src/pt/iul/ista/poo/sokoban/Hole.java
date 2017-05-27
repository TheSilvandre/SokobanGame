package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.util.ArrayList;

public class Hole extends AbstractObject{

	public Hole(Position position){
		super(position);
	}

	@Override
	public String getName() {
		return "Buraco";
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public boolean canMove(Position position, Sokoban sokoban, Direction direction) {
		sokoban.getPlayer().setPosition(position.plus(direction.inverse().asVector()));
		playerFalling(sokoban);
		sokoban.gameOver();
		return false;
	}

	@Override
	public void move(Position position, Sokoban sokoban, Direction direction) {
	}

	private void playerFalling(Sokoban sokoban){
		Player player = sokoban.getPlayer();
		int delayTime = 80;
		ArrayList<String> a = new ArrayList<String>();
		a.add(""); a.add("_1"); a.add("_2");
		sokoban.playSound("gameover");
		for (int i=0; i<3; i++){
			player.setImageName("Empilhadora_D" + a.get(i));
			ImageMatrixGUI.getInstance().update();
			sokoban.delay(delayTime);
			player.setImageName("Empilhadora_L" + a.get(i));
			ImageMatrixGUI.getInstance().update();
			sokoban.delay(delayTime);
			player.setImageName("Empilhadora_U" + a.get(i));
			ImageMatrixGUI.getInstance().update();
			sokoban.delay(delayTime);
			player.setImageName("Empilhadora_R" + a.get(i));
			ImageMatrixGUI.getInstance().update();
			sokoban.delay(delayTime);
		}
	}

}
