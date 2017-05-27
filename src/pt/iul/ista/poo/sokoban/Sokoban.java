package pt.iul.ista.poo.sokoban;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.*;
import javax.swing.*;

/**
 * This class serves as the game engine. Manages information about the player, builds the levels, updates the game any time a key is pressed and ends the game.
 */

public class Sokoban implements Observer {

	private String playerName;                                                      // Nome da pessoa que esta a jogar para registar caso faca um novo melhor score
	private Player player;                                                          // Objeto principal (empilhadora)
	private int level;                                                              // Nivel atual do jogador
	private int totalTargets = 0;                                                   // Total de alvos no nivel, para com o numero de alvos que ja tem caixas em cima
	private int steps = 0;                                                          // Numero de movimentos feitos, usado para guardar como scores nos ficheiros
	protected int energy = 100;                                                     // Energia restante no player, nao necessariamente o numero de passos restantes
	private ArrayList<String> notPassableObjects = new ArrayList<String>();
	private ArrayList<AbstractObject> objects = new ArrayList<AbstractObject>();    // Para ser usada no motor do jogo (saber se a empilhadora e as caixas se podem mexer)
	private ArrayList<Crate> crates = new ArrayList<Crate>();                       // Lista sem paredes e chao para verificar o status do jogo mais eficientemente (se ja estao todas as caixas nos alvos, se algo caiu no buraco ou apanhou a bateria)
	protected long tStart;                                                          // Tempo de inicio do nivel
	private long tEnd;                                                              // Tempo de fim do nivel
	protected int indexToRemove = -1;
	private HighScores highscore = new HighScores(this);

	public Sokoban(String playerName) {
		this.playerName = playerName;
		this.level = 0;
		buildLevel();
		System.out.println(this.playerName);
		updateStatusMessage();
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getLevel() {
		return level;
	}

	public int getSteps(){
		return steps;
	}

	public int getEnergy() {
		return energy;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<AbstractObject> getObjects() {
		return objects;
	}

	public ArrayList<String> getNotPassableObjects() {
		return notPassableObjects;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
     * Builds the level by adding the objects to an ArrayList, and sending that list to the ImageMatrixGUI class.
     * Also adds the objects to specific ArrayLists, depending on their characteristics, so it's easier to access them.
     */

	private void buildLevel() {
		ArrayList<ImageTile> tiles = new ArrayList<ImageTile>(); // Para ser usado na construcao grafica do jogo apenas
		try (final Scanner file = new Scanner(new File("levels/level" + level + ".txt"))) {
			totalTargets = 0;
			for (int y = 0; file.hasNextLine(); y++) {
				String line = file.nextLine();
				AbstractObject obj;
				for (int x = 0; x < line.length(); x++) {
					switch (line.charAt(x)) {
						case 's':
							obj = new SmallStone(new Position(x, y));
							notPassableObjects.add("SmallStone");
							tiles.add(obj);
							objects.add(obj);
							break;
						case 'S':
							obj = new BigStone(new Position(x, y));
							notPassableObjects.add("BigStone");
							tiles.add(obj);
							objects.add(obj);
							break;
						case '#':
							obj = new Wall(new Position(x, y));
							notPassableObjects.add("Parede");
							tiles.add(obj);
							objects.add(obj);
							break;
						case 'X':
							obj = new Target(new Position(x, y));
							tiles.add(obj);
							objects.add(obj);
							totalTargets += 1;
							break;
						case 'C':
							obj = new Crate(new Position(x, y));
							notPassableObjects.add("Caixote");
							tiles.add(obj);
							objects.add(obj);
							crates.add((Crate)obj);
							break;
						case 'b':
							obj = new Battery(new Position(x, y));
							tiles.add(obj);
							objects.add(obj);
							break;
						case 'O':
							obj = new Hole(new Position(x, y));
							tiles.add(obj);
							objects.add(obj);
							break;
						case 'E':
							player = new Player(new Position(x, y));
							break;
					}
					tiles.add(new Floor(new Position(x, y)));
				}
			}

		} catch (final FileNotFoundException e) {
			JOptionPane pane = new JOptionPane();
			JOptionPane.showMessageDialog(pane, "End of game.");
			System.out.println("End of game.");
			System.exit(0);
		}
		tiles.add(player);
		ImageMatrixGUI.getInstance().addImages(tiles);
	}

    /**
     *  Method that runs every time the player presses a key.
     *  Runs the move method if the player pressed a key associated with a direction.
     *  Resets the game if the ESCAPE key is pressed.
     *  Removes an item if the indexToRemove has a value different than -1.
     *
     * @param key Value associated to the pressed key.
     *
     * @see pt.iul.ista.poo.sokoban.ActiveObject#move(Position, Sokoban, Direction)
     */

	@Override
	public void update(Observable o, Object key) {
		if (player != null) {
			if(steps==0)
				tStart=System.currentTimeMillis();
			if (Direction.isDirection(((Integer)key).intValue()))
				player.move(player.getPosition().plus(Direction.directionFor(((Integer)key).intValue()).asVector()), this, Direction.directionFor(((Integer)key).intValue()));
			else if ((((Integer)key).intValue())==KeyEvent.VK_ESCAPE)
				resetGame();
			else if ((((Integer)key).intValue())==KeyEvent.VK_PLUS || (((Integer)key).intValue())==KeyEvent.VK_PERIOD){
				level++;
				resetGame();
			} else if ((((Integer)key).intValue())==KeyEvent.VK_MINUS || (((Integer)key).intValue())==KeyEvent.VK_COMMA){
				level--;
				resetGame();
			}

			updateStatusMessage();
			verifyStatus();
			removeItem(indexToRemove);
		} else {
			throw new IllegalArgumentException("No player spawn position.");
		}
	}

    /**
     * Checks if the game's objectives have been completed, or if the player has failed.
     */

	private void verifyStatus(){
		int targetsCovered = 0;
		for (Crate crate : crates){
			if (crate.onTopOfTarget(this))
					targetsCovered++;
		}
		if(targetsCovered == totalTargets){
			playSound("win");
			highscore.manageScores(timeToComplete());
			level += 1;
			resetGame();
		}
		if(energy<=0)
			gameOver();
	}

    /**
     * Resets the game.
     */

	private void resetGame(){
		objects.clear();
		crates.clear();
		ImageMatrixGUI.getInstance().clearImages();
		steps = 0;
		energy = 100;
		highscore = new HighScores(this);
		buildLevel();
		updateStatusMessage();
		ImageMatrixGUI.getInstance().update();
		tStart = System.currentTimeMillis();
	}

    /**
     * Method that runs if the player loses.
     * Shows the GAME OVER screen.
     */

	public void gameOver(){
		ImageMatrixGUI.getInstance().clearImages();
		ArrayList<ImageTile> images = new ArrayList<ImageTile>();
		for (int y = 0; y<10; y++)
			for(int x = 0; x<10; x++)
				images.add(new GameOver(x,y));
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
		delay(2500);
		resetGame();
	}

	private double timeToComplete(){
		tEnd = System.currentTimeMillis();
		double deltaTime = (tEnd - tStart)/1000.0;
		return deltaTime;
	}

	public void delay(int miliseconds){
		try {
			TimeUnit.MILLISECONDS.sleep(miliseconds);
		} catch (InterruptedException e) {
			System.out.println("Game Closed before time out.");
		}
	}

	public void removeItem(int i){
		if(indexToRemove >= 0) {
			ImageMatrixGUI.getInstance().removeImage(objects.get(i));
			objects.remove(i);
			indexToRemove = -1;
		}
	}

	private void updateStatusMessage(){
		ImageMatrixGUI.getInstance().setStatusMessage("Jogador: " + playerName + "    Level: " + level + "    Energy: " + energy + "%");
	}

	public void playSound(String file){
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sounds/"+file+".wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}