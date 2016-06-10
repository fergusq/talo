package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

@PlayerDetails(name="Siirtyjä", color=Effect.Color.BLUE)
public class Siirtyjä implements Player {
	double edellinenArvaus = (new Random()).nextInt(1000);
	public int getWish(History history) {
		int result = history.withLastRound(
			r -> {
				r.sort((a, b) -> b.roundPoints-a.roundPoints);
				int paras = r.get(0).roundPoints;
				int toka = r.get(1).roundPoints;
				int i = 2;
				while (toka == paras && i < r.size()) {
					toka = r.get(i).roundPoints;
					i++;
				}
				if (edellinenArvaus > paras) edellinenArvaus -= 1.2;
				else if (edellinenArvaus == paras) edellinenArvaus -= 1.1;
				else if (edellinenArvaus < paras) edellinenArvaus += 1.3;
				return (int) edellinenArvaus;
			},
			() -> (int) edellinenArvaus
		);
		return result;
	}
}
