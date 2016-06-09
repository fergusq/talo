package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="HighBot", color=Effect.Color.RED)
public class HighBot implements Player {
	public int getWish(History history) {
		int a = history.withLastRound(
			r -> r.stream().mapToInt(w -> w.roundPoints).max().orElse(1),
			() -> 1
		);
		return a;
	}
}
