package talo.bots;

import java.util.ArrayList;
import java.util.List;

import talo.History;
import talo.History.Wish;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

/** Copies the last guess of the best player.*/
@PlayerDetails(name="Zero", color=Effect.Color.RED)
public class ColorMedia implements Player {

	@Override
	public int getWish(History history) {
		// The model number of one particularly hellish printer
		if(history.rounds() == 0) { return 450; }
		
		List<List<Wish>> wishes = new ArrayList<>();
		for(int round = 0; round < history.rounds(); round++) {
			history.getWishesOnRound(round);
		}
		
		return 0;
	}
}