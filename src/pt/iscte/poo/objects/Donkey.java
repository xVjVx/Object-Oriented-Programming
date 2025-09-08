package pt.iscte.poo.objects;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Donkey implements ImageTile, MovableObject {
    private Point2D position;
    private int direction = 1; // 1 = Right , -1 = Left

    public Donkey(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void setPosition(Point2D newPosition) {
        this.position = newPosition;
    }

    @Override
    public boolean isValidPosition(Point2D newPosition, List<ImageTile> tiles) {
        int newX = newPosition.getX();

        if(newX < 0 || newX >= 10) {
            return false;
        }

        for(ImageTile tile : tiles) {
            if(tile.getPosition().equals(newPosition) && tile instanceof Wall) {
                return false;
            }
        }

        return true;
    }

    public void move(List<ImageTile> tiles, List<Donkey> donkeys) {
        Vector2D movement = new Vector2D(direction, 0);
        Point2D newPosition = position.plus(movement);

        boolean collidesWithDonkey = false;

        for(Donkey otherDonkey : donkeys) {
            if(otherDonkey != this && otherDonkey.getPosition().equals(newPosition)) {
                collidesWithDonkey = true;
                break;
            }
        }

        if(!collidesWithDonkey && isValidPosition(newPosition, tiles)) {
            setPosition(newPosition);
        } else {
            direction *= -1; // Reverse the direction
        }
    }

    // Method to find only one Donkey
    public static Donkey findDonkey(List<ImageTile> tiles) {
        for(ImageTile tile : tiles) {
            if(tile instanceof Donkey) {
                return (Donkey) tile;
            }
        }

        return null;
    }

    // Method to find multiple Donkeys in one level
    public static List<Donkey> findAllDonkeys(List<ImageTile> tiles) {
        List<Donkey> donkeyList = new ArrayList<>();
        
        for(ImageTile tile : tiles) {
            if(tile instanceof Donkey) {
                donkeyList.add((Donkey) tile);
            }
        }

        return donkeyList;
    }
}
