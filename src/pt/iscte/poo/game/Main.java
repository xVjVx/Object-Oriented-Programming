package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;

public class Main {

	public static void main(String[] args) {
		ImageGUI gui = ImageGUI.getInstance();
		GameEngine engine = new GameEngine();
		gui.setStatusMessage("Welcome to the jungle!");
		gui.setEngine(engine);
		gui.go();

		while(true) {
			gui.update();
		}
	}
	
}
