package talo.bots;

import java.util.Optional;
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
			.map(l -> l.stream()
					.reduce((w1, w2) -> w1.roundPoints > w2.roundPoints ? w1 : w2))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.sorted((w1, w2) -> w1.roundPoints > w2.roundPoints ? 1 : -1)
			.findFirst()
			.map(w -> w.wish)
			.orElse(163); // Marie-keksien hinta eräässä kaupassa
	}
	
}