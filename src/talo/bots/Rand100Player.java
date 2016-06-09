package talo.bots;

import java.util.Random;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Random 100", color=Effect.Color.BLUE)
public class Rand100Player implements Player {
	Random rnd = new Random();
	public int getWish(History history) {
		int a = Math.abs(rnd.nextInt()) % 100 + 1;
		return a;
	}
}
