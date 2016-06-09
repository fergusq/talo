package talo.bots;

import java.util.stream.IntStream;

import talo.History;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

/** Marie haluaa olla aina halvin, joten hän valitsee alhaisimman
 * viime kierrosten parhaan voittohinnan tai Marie-keksien hinnan
 * 1,63 € ensimmäisellä kierroksella. */
@PlayerDetails(name="Marie", color=Effect.Color.RED)
public class MarieBot implements Player {

	@Override
	public int getWish(History history) {
		return IntStream.range(0, history.rounds())
				.mapToObj(history::getWishesOnRound)
				.map(l -> l.stream().mapToInt(w -> w.roundPoints).max())
				.mapToInt(o -> o.isPresent() ? o.getAsInt() : Integer.MAX_VALUE)
				.min().orElse(163); // Marie-keksien hinta eräässä kaupassa
	}
	
}