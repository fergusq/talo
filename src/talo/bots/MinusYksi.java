package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

@PlayerDetails(name="Minus 10", color=Effect.Color.MAGENTA)
public class MinusYksi implements Player {
	public int getWish(History history) {
		long sum = 0;
		for (int i = Math.max(0, history.rounds()-10); i < history.rounds(); i++) {
			int winningWish = history.getWishesOnRound(i).stream()
				.mapToInt(w -> w.roundPoints).max().getAsInt();
			sum += winningWish;
		}
		final long finalSum = sum;
		if (sum == 0) return (new Random()).nextInt(1000);
		
		return Math.abs((int) (finalSum / 10) - 10);
	}
}
