package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Door implements ImageTile {
    private Point2D position;

    public Door(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "DoorOpen";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
