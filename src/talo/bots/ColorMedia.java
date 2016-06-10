package talo.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import talo.History;
import talo.History.Wish;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

/** Copies the last guess of the best player.*/
@PlayerDetails(name="Color Media", color=Effect.Color.RED)
public class ColorMedia implements Player {

	@Override
	public int getWish(History history) {
		// The model number of one particularly hellish printer
		if(history.rounds() == 0) { return 450; }
		
		List<List<Wish>> rounds = new ArrayList<>();
		for(int round = 0; round < history.rounds(); round++) {
			rounds.add(history.getWishesOnRound(round));
		}
		
		List<Integer> playerScores = new ArrayList<>();
		int players = history.getWishesOnRound(0).size();
		
		for(int i = 0; i < players; i++) {
			int playerIndex = i;
			playerScores.add(rounds.stream().mapToInt(l -> l.get(playerIndex).roundPoints).sum());
		}
		
		int bestPlayer = -1;
		int bestResult = -1;
		for(int i = 0; i < players; i++) {
			if(playerScores.get(i) > bestResult) {
				bestResult = playerScores.get(i);
				bestPlayer = i;
			}
		}
		
		int resultPlayer = bestPlayer;
		return history.withLastRound(l -> l.get(resultPlayer).wish, () -> 450);
	}
}