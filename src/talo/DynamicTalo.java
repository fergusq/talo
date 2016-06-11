package talo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/** Dynamically loads a game.
 * 
 * @author expositionrabbit*/
public class DynamicTalo {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		boolean unoffical = false;
		
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch(arg) {
			case "unoffical": unoffical = true; break;
			case "offical": unoffical = false; break;
			default: System.err.println("Illegal flag " + arg);
			}
		}
		
		int games = 1, rounds = 1000;
		try (Scanner in = new Scanner(System.in)) {
			System.out.print("Games [1]: ");
			String next = in.nextLine();
			games = next.matches("[0-9]+") ? Integer.parseInt(next) : 1;
			
			System.out.print("Rounds per game (" + games + (games == 1 ? " game" : " games") + ") [1000]: ");
			next = in.nextLine();
			rounds = next.matches("[0-9]+") ? Integer.parseInt(next) : 1000;
		}
		
		Game game = new Game(games);
		
		try {
			Path bots = Paths.get("bin", "talo", "bots");
			for(Path path : Files.newDirectoryStream(bots)) {
				String name = path.getFileName().toString().split("\\.")[0];
				Class<?> bot = Class.forName("talo.bots." + name);
				
				if(!unoffical) {
					if(bot.isAnnotationPresent(Unoffical.class)) { continue; }
				}
				
				game.addPlayer((Class<? extends Player>) bot);
			}
		} catch (Exception e) {
			System.out.println("[!!!] Cannot load bot classes, are you sure they are in bin/talo/bots?");
			e.printStackTrace();
		}
		
		for (int i = 0; i < games; i++) {
			game.play(i, rounds);
		}
		game.printScores();
	}
}
