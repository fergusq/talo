package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

@PlayerDetails(name="Keskiverto", color=Effect.Color.BLUE)
public class TotalAvgBot implements Player {
	public int getWish(History history) {
		long sum = 0;
		for (int i = 0; i < history.rounds(); i++) {
			sum += (long) history.getWishesOnRound(i).stream()
				.mapToInt(w -> w.roundPoints).average().getAsDouble();
		}
		return sum == 0 ? 1 : (int) (sum / history.rounds());
	}
}
