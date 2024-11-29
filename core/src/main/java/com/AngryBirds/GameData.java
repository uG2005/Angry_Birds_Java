package com.AngryBirds;

import java.io.*;
import java.nio.file.*;

public class GameData {
    private static final String FILE_PATH = "game_data.txt";
    private static int currentLevel = 1;
    private static int score = 0;

    static {
        loadData();
    }

    private static void loadData() {
        Path path = Paths.get(FILE_PATH);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        switch (parts[0].trim()) {
                            case "level":
                                currentLevel = Integer.parseInt(parts[1].trim());
                                break;
                            case "score":
                                score = Integer.parseInt(parts[1].trim());
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveData() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            writer.write("level=" + currentLevel);
            writer.newLine();
            writer.write("score=" + score);
        } catch (IOException e) {
            System.err.println("Error saving game data: " + e.getMessage());
        }
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int level) {
        currentLevel = level;
        saveData();
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int newScore) {
        score = newScore;
        saveData();
    }
}
