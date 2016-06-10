package talo.bots;

import talo.Player;
import talo.PlayerDetails;
import talo.History;
import talo.util.Effect;

import java.util.Random;

@PlayerDetails(name="Puolittaja", color=Effect.Color.CYAN)
public class Puolittaja implements Player {
	int edellinenArvaus = (new Random()).nextInt(1000);
	// ei pisteitä -> puolittaa
	// saa pisteitä ja paras -> puoleen väliin toiseksi parasta
	// saa pisteitä ja ei paras -> puoleen väliin itseä ja parasta
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
				if (edellinenArvaus > paras) edellinenArvaus /= 2;
				else if (edellinenArvaus == paras) edellinenArvaus = toka+(edellinenArvaus-toka)/2;
				else if (edellinenArvaus < paras) edellinenArvaus = edellinenArvaus+(paras-edellinenArvaus)/2;
				return edellinenArvaus;
			},
			() -> edellinenArvaus
		);
		return result;
	}
}
