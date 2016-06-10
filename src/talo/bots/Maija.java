package talo.bots;

import talo.History;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

@PlayerDetails(name="Maija Vilkkumaa", color=Effect.Color.RED)
public class Maija implements Player {
	
	static String tuu_meille_ja_kato_mun_kaa = "kissavideoita";
	int guess = 0;

	@Override
	public int getWish(History history) {
		int i = tuu_meille_ja_kato_mun_kaa.charAt(++guess % tuu_meille_ja_kato_mun_kaa.length());
		guess = i;
		
		int mod = history.withLastRound(
				l -> l.stream().reduce((w1, w2) -> Math.random() > 0.5 ? w1 : w2).get().wish,
				() -> 10);
		
		return 1 + (mod == 0 ? i % 10 : i % mod);
	}
	
	@Override
	public String getComment() { return "kissavideoita"; }
	
}