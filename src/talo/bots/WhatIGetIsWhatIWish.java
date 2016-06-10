package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

@PlayerDetails(name="Arvaan mitä saan", color=Effect.Color.BLUE)
public class WhatIGetIsWhatIWish implements Player {
	int kierros = 0;
	int pisteet = 0;
	int edellinenArvaus = 0;
	public int getWish(History history) {
		return history.withLastRound(
			r -> {
				kierros++;
				if (r.stream().mapToInt(w -> w.roundPoints).max().getAsInt() <= edellinenArvaus) {
					pisteet += edellinenArvaus;
				}
				edellinenArvaus = pisteet / kierros;
				return edellinenArvaus + 1;
			},
			() -> (new Random()).nextInt(1000)
		);
	}
}
