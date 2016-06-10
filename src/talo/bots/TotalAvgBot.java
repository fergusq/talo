package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

/**
 * Wishes to have a house that is a little better than an average house.
 */
@PlayerDetails(name="Keskiverto", color=Effect.Color.BLUE)
public class TotalAvgBot implements Player {
	public int getWish(History history) {
		long sum = 0;
		for (int i = Math.max(0, history.rounds()-10); i < history.rounds(); i++) {
			sum += (long) history.getWishesOnRound(i).stream()
				.mapToInt(w -> w.roundPoints).average().getAsDouble();
		}
		return sum == 0 ? (new Random()).nextInt(1000) : (int) (sum / 10 * 1.5);
	}
}
