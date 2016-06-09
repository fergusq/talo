package talo;

import talo.bots.*;

public class Talo {
	private Talo() {}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.addPlayer(MinPlayer.class);
		game.addPlayer(MaxPlayer.class);
		game.addPlayer(RandPlayer.class);
		game.addPlayer(Rand100Player.class);
		game.addPlayer(HighBot.class);
		game.addPlayer(Kymmenes.class);
		game.play(1000);
		game.printScores();
	}
}
