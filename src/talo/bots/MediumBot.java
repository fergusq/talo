package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

@PlayerDetails(name="Medium", color=Effect.Color.RED)
public class MediumBot implements Player {
	public int getWish(History history) {
		int a = history.withLastRound(
			r -> r.stream().mapToInt(w -> w.roundPoints).max().orElse(1),
			() -> (new Random()).nextInt(1000)
		);
		return a;
	}
}
