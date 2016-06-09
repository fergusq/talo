package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Kymmenen", color=Effect.Color.CYAN)
public class Kymmenen implements Player {
	public int getWish(History history) {
		return 10;
	}
}
