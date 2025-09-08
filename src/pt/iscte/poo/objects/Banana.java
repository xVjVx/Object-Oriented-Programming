package pt.iscte.poo.objects;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;


public class Banana implements ImageTile {
    private Point2D position;
    
    public Banana(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Banana";
    }

    @Override 
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public void setPosition(Point2D newPosition) {
        this.position = newPosition;
    }

    public boolean isValidPosition(Point2D newPosition) {
        return position.getY() < 10;
    }

    public static void spawnBanana(List<Donkey> donkeys, List<Banana> bananas, List<ImageTile> tiles) {
        for(Donkey donkey : donkeys) {
				if(Math.random() < 0.5) {
					Banana banana = new Banana(donkey.getPosition());
					bananas.add(banana);
					tiles.add(banana);
					ImageGUI.getInstance().addImage(banana);
				}
			}
    }

    public static void moveBanana(List<Banana> bananas, List<ImageTile> tiles, JumpMan jumpMan) {
        for(Banana banana : new ArrayList<>(bananas)) {
            Point2D newPosition = banana.getPosition().plus(new Vector2D(0, 1));

            if(!banana.isValidPosition(newPosition)) {
                tiles.remove(banana);
                bananas.remove(banana);
                ImageGUI.getInstance().removeImage(banana);
                continue;
            }

            if(banana.getPosition().equals(jumpMan.getPosition())) {
                jumpMan.setLife(jumpMan.getLife() - 1);
                ImageGUI.getInstance().setStatusMessage("JumpMan lifes: " + jumpMan.getLife());

                tiles.remove(banana);
                bananas.remove(banana);
                ImageGUI.getInstance().removeImage(banana);
                continue;
            }
            banana.setPosition(newPosition);
        }
    }   
}   
