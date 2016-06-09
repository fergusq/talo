package talo.bots;

import java.util.Random;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Random Infinity", color=Effect.Color.BLACK)
public class RandPlayer implements Player {
	Random rnd = new Random();
	public int getWish(History history) {
		int a = Math.abs(rnd.nextInt());
		return a;
	}
}
