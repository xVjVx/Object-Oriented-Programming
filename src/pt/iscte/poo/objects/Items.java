package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;

import java.util.Set;
import java.util.HashSet;

public class Items {
    public static final Set<Class<? extends ImageTile>> ITEMS = new HashSet<>();

    static {
        ITEMS.add(Sword.class);
        ITEMS.add(Hammer.class);
    }
}
