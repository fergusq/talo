package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Rational", color=Effect.Color.BLACK)
public class MinPlayer implements Player {
	public int getWish(History history) {
		return 1;
	}
}
