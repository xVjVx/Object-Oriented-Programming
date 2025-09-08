package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Ladder implements ImageTile {
    private Point2D position;

    public Ladder(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Stairs";
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