package pt.iscte.poo.objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Level {
    private static Point2D initialPosition;

    public static Point2D getInitialPostion() {
        return initialPosition;
    }

    public static Point2D loadRoom(String filename, List<ImageTile> tiles, JumpMan existingJumpMan ) {
        try(Scanner scanner = new Scanner(new File("rooms/" + filename))) {
            String line;
            int y = 0;

            while(scanner.hasNextLine()) {
                line = scanner.nextLine();

                if(line.startsWith("#")) {
                    continue;
                }

                for(int x = 0; x < line.length(); x++) {
                    char symbol = line.charAt(x);
                    Point2D position = new Point2D(x, y);

                    switch(symbol) {
                        case 'W': // Wall
                            tiles.add(new Wall(position));
                            break;
                        case ' ': // Background
                            tiles.add(new Background(position));
                            break;
                        case 'J': // JumpMan
                            if(existingJumpMan != null) {
                                existingJumpMan.setPosition(position);
                                tiles.add(existingJumpMan);
                            } else {
                                JumpMan jumpMan = new JumpMan(position, 3);
                                tiles.add(jumpMan);
                            }
                            tiles.add(new Background(position));
                            initialPosition = position;
                            break;
                        case 'H': // Hero
                            if (existingJumpMan != null) {
                                existingJumpMan.setPosition(position);
                                tiles.add(existingJumpMan);
                            } else {
                                JumpMan jumpMan = new JumpMan(position, 3);
                                tiles.add(jumpMan);
                            }
                            tiles.add(new Background(position));
                            initialPosition = position;
                            break;
                        case 'S': // Ladder
                            tiles.add(new Ladder(position));
                            break;
                        case 't': // Trap
                            tiles.add(new Trap(position));
                            break;
                        case 's': // Sword
                            tiles.add(new Sword(position));
                            tiles.add(new Background(position));
                            break;
                        case '0': // Door
                            tiles.add(new Door(position));
                            break;
                        case 'G': // Donkey
                            tiles.add(new Donkey(position));
                            tiles.add(new Background(position));
                            break;
                        case 'm': // Hammer
                            tiles.add(new Hammer(position));
                            tiles.add(new Background(position));
                            break;
                        case 'P':
                            tiles.add(new Princess(position));
                            tiles.add(new Background(position));
                            break;
                        default: // Default
                            tiles.add(new Background(position));
                            break;
                    }
                }
            y++;
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        return initialPosition;
    }

    // Method with a switch case to get the name of the next level
    public static String getLevelFileName(int levelIndex) {
        switch(levelIndex) {
            case 0:
                return "room0.txt";
            case 1:
                return "room1.txt";
            case 2:
                return "room2.txt";
            default:
                return null; // No more levels
        }
    }
}
