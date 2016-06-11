package talo.bots;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import talo.Competitor;
import talo.History;
import talo.Player;
import talo.PlayerDetails;
import talo.Unoffical;
import talo.History.Wish;
import talo.util.Effect;

@PlayerDetails(name="KIROAJA", color=Effect.Color.RED)
@Unoffical
@Competitor
public class KIROAJA implements Player {

	@SuppressWarnings("unchecked")
	@Override
	public int getWish(History history) {
		
		if(history.rounds() == 0) { return Integer.MIN_VALUE; }
		
		try {
			Field points = history.getClass().getDeclaredField("points");
			points.setAccessible(true);
			Map<Class<? extends Player>, Long> map = (Map<Class<? extends Player>, Long>) points.get(history);
			
			Class<? extends Player> key = map.keySet().stream()
					.sorted((k1, k2) -> Math.random() > 0.5 ? 1 : -1)
					.findAny().get();
			
			Field wishes = history.getClass().getDeclaredField("wishesOnRounds");
			wishes.setAccessible(true);
			
			List<Map<Class<? extends Player>, Wish>> wishList = (List<Map<Class<? extends Player>, Wish>>) wishes.get(history);
			
			Wish badWish = wishList.get(history.rounds()-1).values().stream().reduce((w1, w2) -> w1.roundPoints > w2.roundPoints ? w2 : w1).get();
			
			for(Map<Class<? extends Player>, Wish> submap : wishList) {
				submap.put(key, badWish);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return 0;
	}
}