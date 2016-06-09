package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Dreamer", color=Effect.Color.BLACK)
public class MaxPlayer implements Player {
	public int getWish(History history) {
		return Integer.MAX_VALUE;
	}
}
