package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

@PlayerDetails(name="Kymmenes", color=Effect.Color.BLUE)
public class Kymmenes implements Player {
	public int getWish(History history) {
		int a = history.withLastRound(
			r -> r.stream().mapToInt(w -> w.roundPoints).max().orElse(100) / 10,
			() -> (new Random()).nextInt(1000)
		);
		return a;
	}
}
