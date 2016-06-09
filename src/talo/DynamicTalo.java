package talo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Dynamically loads a game.
 * 
 * @author expositionrabbit*/
public class DynamicTalo {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Game game = new Game();
		
		try {
			Path bots = Paths.get("bin", "talo", "bots");
			for(Path path : Files.newDirectoryStream(bots)) {
				String name = path.getFileName().toString().split("\\.")[0];
				Class<?> bot = Class.forName("talo.bots." + name);
				game.addPlayer((Class<? extends Player>) bot);
			}
		} catch (Exception e) {
			System.out.println("[!!!] Cannot load bot classes, are you sure they are in bin/talo/bots?");
			e.printStackTrace();
		}
		
		game.play(1000);
		game.printScores();
	}
}