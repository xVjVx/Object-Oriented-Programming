package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

public class Hammer implements ImageTile {
    private Point2D position;

    public Hammer(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 2;
    }
}
