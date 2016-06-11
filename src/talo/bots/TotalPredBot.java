package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

/**
 * Wishes to have a house that is a little better than the previous best house.
 */
@PlayerDetails(name="Paras++", color=Effect.Color.BLUE)
public class TotalPredBot implements Player {
	public int getWish(History history) {
		if (history.rounds() < 3) return (3-history.rounds())*100;
		int last3 = history.getWishesOnRound(history.rounds()-3).stream()
				.mapToInt(w -> w.roundPoints).max().getAsInt();
		int last1 = history.getWishesOnRound(history.rounds()-1).stream()
				.mapToInt(w -> w.roundPoints).max().getAsInt();
		int ero = last1-last3;
		return last1+ero;
	}
}
