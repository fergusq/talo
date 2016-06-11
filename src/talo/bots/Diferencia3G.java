package talo.bots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import talo.Competitor;
import talo.History;
import talo.History.Wish;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

/** The first player to beat Diferencia.*/
@PlayerDetails(name="Diferencia3G", color=Effect.Color.RED)
@Competitor
public class Diferencia3G implements Player {

	@Override
	public int getWish(History history) {
		if(history.rounds() == 0) { return 250; }
		
		if(history.rounds() == 1) {
			List<Wish> wishes = history.getWishesOnRound(0);
			int[] guesses = wishes.stream().mapToInt(w -> w.wish/2).toArray();
			Arrays.sort(guesses);
			return guesses[guesses.length / 2 - 1];
		}
		
		if(history.rounds() == 2) {
			List<Wish> round1 = history.getWishesOnRound(history.rounds()-2);
			List<Wish> round2 = history.getWishesOnRound(history.rounds()-1);
			
			List<Integer> guesses = new ArrayList<>();
			
			for(int i = 0; i < round1.size(); i++) {
				guesses.add(round2.get(i).wish + (round2.get(i).wish - round1.get(i).wish));
			}
			
			Collections.sort(guesses);
			
			return guesses.get(guesses.size()/2 - 1);
			
		}
		
		List<Wish> roundm3 = history.getWishesOnRound(history.rounds()-3);
		List<Wish> roundm2 = history.getWishesOnRound(history.rounds()-2);
		List<Wish> roundm1 = history.getWishesOnRound(history.rounds()-1);
		
		List<Integer> guesses = new ArrayList<>();
		
		for(int i = 0; i < roundm1.size(); i++) {
			float m3 = roundm3.get(i).wish;
			float m2 = roundm2.get(i).wish;
			float m1 = roundm1.get(i).wish;
			
			float diff1 = m1 - m2;
			float diff2 = m2 - m3;
			guesses.add((int) (m1 + (diff1 + diff2) / 2));
			//System.out.println(m1 + ", " + m2 + ", " + m3 + " ----> " + guesses.get(guesses.size()-1));
		}
		
		Collections.sort(guesses);
		
		return guesses.get(guesses.size()/2 - 1);
	}	
}
