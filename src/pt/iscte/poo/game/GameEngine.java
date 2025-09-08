package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.objects.JumpMan;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.objects.Level;
import pt.iscte.poo.objects.Princess;
import pt.iscte.poo.objects.Banana;
import pt.iscte.poo.objects.Donkey;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

	private long startTime;
	private long endTime;

	String firstLevelName = Level.getLevelFileName(0); // In case JumpMan dies

	private int tickCounter = 0;
	private int levelIndex = 0;

	private List<Donkey> donkeys;
	private List<Banana> bananas;
	private Princess princess;
	private JumpMan jumpMan;

	private List<ImageTile> tiles;

	ImageGUI gui = ImageGUI.getInstance();

	public GameEngine() {
		tiles = new ArrayList<>();
		bananas = new ArrayList<>();

		Point2D initialPosition = Level.loadRoom("room0.txt", tiles, null);
		jumpMan = JumpMan.findJumpMan(tiles);

		if(jumpMan == null) {
			jumpMan = new JumpMan(initialPosition, 3);
			tiles.add(jumpMan);
		}

		donkeys = Donkey.findAllDonkeys(tiles);

		ImageGUI.getInstance().addImages(tiles);

		startTime = System.currentTimeMillis();
	}

	public void keyPressed(int key) {
		Vector2D moveVector = null;

		if (key == KeyEvent.VK_UP) moveVector = Direction.UP.asVector();
        if (key == KeyEvent.VK_RIGHT) moveVector = Direction.RIGHT.asVector();
        if (key == KeyEvent.VK_DOWN) moveVector = Direction.DOWN.asVector();
        if (key == KeyEvent.VK_LEFT) moveVector = Direction.LEFT.asVector();

		if(moveVector != null) {
			Point2D newPosition = jumpMan.getPosition().plus(moveVector);
			if(jumpMan.isValidPosition(newPosition, tiles)) {
				jumpMan.setPosition(newPosition);
				jumpMan.pickUpItem(tiles);

				checkVictory();
			}
		}
	}

	public void tick(int ticks) {
		System.out.println("Tic tac.. " + ticks);
		tickCounter++;

		if(tickCounter % 3 == 0) {
			Banana.spawnBanana(donkeys, bananas, tiles);
		}

		if(tickCounter % 2 == 0) {
			Banana.moveBanana(bananas, tiles, jumpMan);

			if(jumpMan.getLife() == 0) resetGame();
		}

		if(jumpMan.isAtDoor(tiles)) {
			loadNextLevel();
			ImageGUI.getInstance().setStatusMessage("Passed to the level " + (levelIndex + 1));
			return;
		}

		if(jumpMan.isAtTrap(tiles)) {
			jumpMan.setLife(jumpMan.getLife() - 1);
			ImageGUI.getInstance().setStatusMessage("JumpMan lifes: " + jumpMan.getLife());

			if(jumpMan.getLife() == 0) resetGame();
		}

		if(jumpMan.isFloating(tiles)) {
			Point2D newPosition = jumpMan.getPosition().plus(new Vector2D(0, 1)); // Position below

			if(jumpMan.isValidPosition(newPosition, tiles)) {
				jumpMan.setPosition(newPosition);
				ImageGUI.getInstance().update();
			}
		}

		JumpMan.killDonkey(donkeys, jumpMan, tiles);
		
		if(jumpMan.getLife() == 0) resetGame();

		if(tickCounter % 3 == 0) {
			for(int i = 0; i < donkeys.size(); i++) {
				Donkey donkey = donkeys.get(i);
				donkey.move(tiles, donkeys);
			}
			ImageGUI.getInstance().update();
		}
	}

	private void checkVictory() {
		if(princess != null && jumpMan.getPosition().equals(princess.getPosition()) && donkeys.isEmpty() && levelIndex == 2) {
			endTime = System.currentTimeMillis();
			long time = (endTime - startTime) / 1000;
			
			ImageGUI.getInstance().setStatusMessage("JumpMan saved the Princess! You won!");

			JOptionPane.showMessageDialog(null, "Victory! You saved the Princess!", "Victory", JOptionPane.INFORMATION_MESSAGE);

			HighScoreManager.setHighScore(time);

			System.exit(0);
		}
	}

	private void resetGame() {
		tiles.clear();
		levelIndex = 0;
		Level.loadRoom(firstLevelName, tiles, jumpMan);

		ImageGUI.getInstance().clearImages();
		ImageGUI.getInstance().addImages(tiles);
		jumpMan.setLife(3);
		ImageGUI.getInstance().setStatusMessage("JumpMan died, game reseted!");
	}

	private void loadNextLevel() {
		String nextLevelFileName = Level.getLevelFileName(levelIndex + 1);

		if(nextLevelFileName != null) {
			levelIndex++;
			tiles.clear();

			Point2D newPosition = Level.loadRoom(nextLevelFileName, tiles, jumpMan);
			jumpMan.setPosition(newPosition);

			donkeys = Donkey.findAllDonkeys(tiles);
			princess = Princess.findPrincess(tiles);

			ImageGUI.getInstance().clearImages();
			ImageGUI.getInstance().addImages(tiles);
			ImageGUI.getInstance().update();
		} else {
			ImageGUI.getInstance().setStatusMessage("End of the game!");
		}
	}
}
