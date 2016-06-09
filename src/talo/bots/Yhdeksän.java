package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Yhdeksän", color=Effect.Color.CYAN)
public class Yhdeksän implements Player {
	public int getWish(History history) {
		return 9;
	}
}
