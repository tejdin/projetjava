package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public final class HighScore {

	private static final Path FILE = Path.of("highscore.txt");

	private HighScore() {}

	public static int load() {
		try {
			return Integer.parseInt(Files.readString(FILE).trim());
		} catch (IOException | NumberFormatException e) {
			return 0;
		}
	}

	public static void save(int score) {
		try {
			Files.writeString(FILE, Integer.toString(score));
		} catch (IOException e) {
			IO.println("Error while saving the score: " + e.getMessage());
		}
	}
}
