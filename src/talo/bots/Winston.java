package talo.bots;

import java.lang.reflect.Field;
import java.util.Map;

import talo.Competitor;
import talo.History;
import talo.Player;
import talo.PlayerDetails;
import talo.Unoffical;
import talo.util.Effect;

/** Prevails over all by rewriting history.
 * 
 * 1984 – THIS IS WHAT THE REFRANCE
 * SWEET BRO AND HELLA JEFF – THIS IS WHAT THE "THIS IS WHAT THE" REFRANCE"*/
@PlayerDetails(name="Winston", color=Effect.Color.RED)
@Unoffical
@Competitor
public class Winston implements Player {

	@SuppressWarnings("unchecked")
	@Override
	public int getWish(History history) {
		
		Class<History> cls = History.class;
		
		try {
			Field points = cls.getDeclaredField("points");
			points.setAccessible(true);
			((Map<Class<? extends Player>, Long>) points.get(history)).put(Winston.class, 19841984194L);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return 2+2;
	}
	
	
} 