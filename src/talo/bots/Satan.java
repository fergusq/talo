package talo.bots;

import java.lang.reflect.Field;
import java.util.Map;

import talo.History;
import talo.Player;
import talo.PlayerDetails;
import talo.util.Effect;

/** Prevails over all by rewriting history.*/
@PlayerDetails(name="SATAN", color=Effect.Color.RED)
public class Satan implements Player {

	@SuppressWarnings("unchecked")
	@Override
	public int getWish(History history) {
		
		Class<History> cls = History.class;
		
		try {
			Field points = cls.getDeclaredField("points");
			points.setAccessible(true);
			((Map<Class<? extends Player>, Long>) points.get(history)).put(Satan.class, 666666666666666666L);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return 666;
	}
	
	
}