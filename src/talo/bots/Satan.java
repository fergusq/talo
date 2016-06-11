package talo.bots;

import java.util.Map;

import talo.History;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

/** Prevails over all by rewriting history.*/
@PlayerDetails(name="Satan", color=Effect.Color.BLUE)
public class Satan implements Player {

	@Override
	public int getWish(History history) {
		if (history.rounds() % 6 < 3)
			return 666;
		else
			return history.withLastRound(
				r -> r.stream().mapToInt(w -> w.roundPoints).max().orElse(666) - 2,
				() -> 666
			);
	}
}
