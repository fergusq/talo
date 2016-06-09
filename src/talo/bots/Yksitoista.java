package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Yksitoista", color=Effect.Color.MAGENTA)
public class Yksitoista implements Player {
	public int getWish(History history) {
		return 11;
	}
}
