package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

@PlayerDetails(name="1,5", color=Effect.Color.BLUE)
public class YksiPilkkuViisi implements Player {
	public int getWish(History history) {
		int result = history.withLastRound(
			r -> {
				r.sort((a, b) -> b.roundPoints-a.roundPoints);
				return (r.get(1).roundPoints + r.get(0).roundPoints) / 2;
			},
			() -> (new Random()).nextInt(1000)
		);
		return result;
	}
}
