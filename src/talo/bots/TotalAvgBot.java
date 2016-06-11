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
	private double factor = 1.5;
	public int getWish(History history) {
		double sum = 0;
		int i;
		for (i = Math.max(0, history.rounds()-10); i < history.rounds(); i++) {
			sum += history.getWishesOnRound(i).stream()
				.mapToInt(w -> w.roundPoints).average().getAsDouble();
		}
		final double finalSum = sum;
		if (finalSum != 0 && history.rounds() % 10 == 0) {
			factor = history.withLastRound(
				r -> (double) r.stream().mapToInt(w -> w.roundPoints).max().orElse(1) / finalSum * 10,
				() -> 1.5
			);
		}
		return sum == 0 ? (new Random()).nextInt(1000) : (Math.max(1, (int) (finalSum / i * factor)));
	}
	
	public String getComment() {
		return String.format("%.5f", factor);
	}
}
