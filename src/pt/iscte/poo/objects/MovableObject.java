package pt.iscte.poo.objects;

import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

// Interface to interact with movable objects such as JumpMan, Donkey Kong, etc.
public interface MovableObject {

    // Method do define a new position
    void setPosition(Point2D newPosition);

    // Method to verify if the new position is a valid move
    boolean isValidPosition(Point2D newPosition, List<ImageTile> tiles);
}
