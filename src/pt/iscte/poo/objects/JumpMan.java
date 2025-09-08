package pt.iscte.poo.objects;

import java.util.List;
import java.util.ArrayList;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class JumpMan implements ImageTile, MovableObject {
    private int floatingTickets = 0;

    private int life;
    private Point2D position;
    private ArrayList<ImageTile> items;

    public JumpMan(Point2D position, int life) {
        this.life = life;
        this.position = position;
        this.items = new ArrayList<>();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public String getName() {
        return "JumpMan";
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
		int x = newPosition.getX();
		int y = newPosition.getY();

		if(x < 0 || x >= 10 || y < 0 || y >= 9) {
			System.out.println("Invalid movement: Out of bounds");
			return false;
		}

        int deltaY = newPosition.getY() - this.position.getY();
        int deltaX = newPosition.getX() - this.position.getX();
        
        if(deltaY < 0) {
            boolean hasLadder = false;

            for(ImageTile tile : tiles) {
                if(tile.getPosition().equals(newPosition) && tile instanceof Ladder) {
                    hasLadder = true;
                    break;
                }
            }
    
            if(!hasLadder) {
                for(ImageTile tile : tiles) {
                    if(tile.getPosition().equals(this.position) && tile instanceof Ladder) {
                        hasLadder = true;
                        break;
                    }
                }
            }
    
            if(!hasLadder) {
                System.out.println("Invalid movement: No ladder to climb");
                return false;
            }
        }

        if(deltaX != 0) {
            boolean isBlocked = false;

            for(ImageTile tile : tiles) {
                if(tile.getPosition().equals(newPosition) && tile instanceof Wall) {
                    isBlocked = true;
                    break;
                }
            }

            if(isBlocked) {
                System.out.println("Invalid movement: Wall in the way");
                return false;
            }
        }

        if(deltaY > 0) {
            boolean canDescend = false;

            for(ImageTile tile : tiles) {
                if(tile.getPosition().equals(newPosition)) {
                    if(tile instanceof Ladder || tile instanceof Background) {
                        canDescend = true;
                        break;
                    }
                }
            }

            if(!canDescend) {
                System.out.println("Invalid movement: Cannot descend");
                return false;
            }
        }
    
        return true;
	}

    public boolean isAtTrap(List<ImageTile> tiles) {
        for(ImageTile tile : tiles) {
            if(tile instanceof Trap && tile.getPosition().equals(this.getPosition().plus(new Vector2D(0, 1)))) { // Verify the position below
                return true;
            }
        }
        return false;
    }

    public boolean isAtDoor(List<ImageTile> tiles) {
        for(ImageTile tile : tiles) {
            if(tile instanceof Door && tile.getPosition().equals(this.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public void pickUpItem(List<ImageTile> tiles) {
        ImageTile itemToRemove = null;

        for(ImageTile tile : tiles) {
            if(Items.ITEMS.contains(tile.getClass()) && tile.getPosition().equals(this.getPosition())) {
                items.add(tile);
                itemToRemove = tile;

                ImageGUI.getInstance().setStatusMessage("JumpMan picked up " + tile.getName());

                break;
            }
        }

        if(itemToRemove != null) {
            tiles.remove(itemToRemove);
            ImageGUI.getInstance().removeImage(itemToRemove);
            ImageGUI.getInstance().update();
        }
    }

    public boolean isFloating(List<ImageTile> tiles) {
        Point2D belowPosition = this.position.plus(new Vector2D(0, 1)); // Position below

        for(ImageTile tile : tiles) {
            if(tile.getPosition().equals(belowPosition) && (tile instanceof Wall || tile instanceof Ladder)) {
                floatingTickets = 0;
                return false;
            }
        }
        
        floatingTickets++;
        return floatingTickets >= 2;
        
    }

    public boolean hasItem(Class<? extends ImageTile> itemClass) {
        return items.stream().anyMatch(item -> itemClass.isInstance(item));
    }

    public static void killDonkey(List<Donkey> donkeys, JumpMan jumpMan, List<ImageTile> tiles) {
        for(Donkey donkey : new ArrayList<>(donkeys)) {
			if(jumpMan.getPosition().equals(donkey.getPosition())) {
				if(jumpMan.hasItem(Sword.class) || jumpMan.hasItem(Hammer.class)) {
					tiles.remove(donkey);
					donkeys.remove(donkey);
                    ImageGUI.getInstance().removeImage(donkey);
					ImageGUI.getInstance().setStatusMessage("JumpMan killed Donkey!");
				} else {
					jumpMan.setLife(jumpMan.getLife() - 1 );
					ImageGUI.getInstance().setStatusMessage("JumpMan lifes: " + jumpMan.getLife());
				}
				ImageGUI.getInstance().update();
			}
		}
    }

    public static JumpMan findJumpMan(List<ImageTile> tiles) {
		for(ImageTile tile : tiles) {
			if(tile instanceof JumpMan) {
				return (JumpMan) tile;
			}
		}

		return null;
	}
}
