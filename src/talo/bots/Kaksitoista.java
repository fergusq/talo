package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Kaksitoista", color=Effect.Color.CYAN)
public class Kaksitoista implements Player {
	public int getWish(History history) {
		return 12;
	}
}
