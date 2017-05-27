package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {

	/**
	 * This is the main class of our game. Serves the sole purpose of creating an instance of our game.
	 */

	public static void main(String[] args) throws FileNotFoundException {
		String name = JOptionPane.showInputDialog("Insert your name: ");
		if (name == null)
			System.exit(0);
		else {

			if(name.trim().length()==0)
				name = "Player";

			JOptionPane.showMessageDialog(new JFrame(), "Controlos:\n\nSetas -> mover o jogador\nESC -> reiniciar nível\n'-' ou ',' -> nível anterior\n'+' ou '.' -> nível seguinte\n\nO jogo inclui efeitos sonoros.");

			Sokoban s = new Sokoban(name);

			ImageMatrixGUI.getInstance().setName("Sokoban");
			ImageMatrixGUI.getInstance().addObserver(s);
			ImageMatrixGUI.getInstance().go();

		}
	}

}