package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

/**
 * Wishes to have a house that is a little better than the previous best house.
 */
@PlayerDetails(name="Paras", color=Effect.Color.BLUE)
public class TotalMaxBot implements Player {
	private double factor = 1.5;
	public int getWish(History history) {
		long sum = 0;
		for (int i = Math.max(10, history.rounds()-10); i < history.rounds(); i++) {
			sum += history.getWishesOnRound(i).stream()
				.mapToInt(w -> w.roundPoints).max().getAsInt();
		}
		final long finalSum = sum;
		if (history.rounds() % 2 == 0) {
			factor = history.withLastRound(
				r -> (double) r.stream().mapToInt(w -> w.roundPoints).max().orElse(1) / finalSum * 10,
				() -> 1.5
			);
		}
		return sum == 0 ? (new Random()).nextInt(1000) : (int) (finalSum / 10 * factor);
	}
	
	public String getComment() {
		return ""+factor;
	}
}
