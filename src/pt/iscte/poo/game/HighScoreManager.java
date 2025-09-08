package pt.iscte.poo.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class HighScoreManager {
    private static String HIGHSCORE_FILE = "highscore.txt";

    public static long getHighScore() {
        try {
            if(Files.exists(Paths.get(HIGHSCORE_FILE))) {
                String data = Files.readString(Paths.get(HIGHSCORE_FILE)).trim();
                return Long.parseLong(data);
            }
        } catch(IOException | NumberFormatException e) {
            System.out.println("Error reading HighScore file: " + e.getMessage());
        }
        return Long.MAX_VALUE;
    }

    public static void setHighScore(long time) {
        long currentHighScore = getHighScore();

        if(time < currentHighScore) {
            try {
                Files.writeString(Paths.get(HIGHSCORE_FILE), String.valueOf(time));
                JOptionPane.showMessageDialog(null, 
                    "New HighScore: " + time + " seconds!",
                    "HighScore",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch(IOException e) {
                System.out.println("Error writing in the file: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null,
            "Your time:" + time + " seconds. HighScore: " + currentHighScore + " seconds",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
