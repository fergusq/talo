package talo.bots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import talo.History;
import talo.History.Wish;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

/** Tries to guess what other bots will guess based on
 * how they changed their guess last turn.*/
@PlayerDetails(name="Diferencia", color=Effect.Color.RED)
public class Diferencia implements Player {

	@Override
	public int getWish(History history) {
		if(history.rounds() < 2) { return 1 + new Random().nextInt(9); }
		
		List<Wish> round1 = history.getWishesOnRound(history.rounds()-2);
		List<Wish> round2 = history.getWishesOnRound(history.rounds()-1);
		
		List<Integer> guesses = new ArrayList<>();
		
		for(int i = 0; i < round1.size(); i++) {
			guesses.add(round2.get(i).wish + (round2.get(i).wish - round1.get(i).wish));
		}
		
		Collections.sort(guesses);
		
		return guesses.get(guesses.size()/2 - 1);
	}	
}
