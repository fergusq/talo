package talo;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import java.util.function.Function;
import java.util.function.Supplier;

public class History {

	public static class Wish {
		public final int wish, roundPoints;
		private Wish(int wish, int roundPoints) {
			this.wish = wish;
			this.roundPoints = roundPoints;
		}
	}
	
	private List<Map<Class<? extends Player>, Wish>> wishesOnRounds = new ArrayList<>();
	private Map<Class<? extends Player>, Long> points = new HashMap<>();
	
	void nextRound() {
		wishesOnRounds.add(new HashMap<>());
	}
	
	void addPoints(Class<? extends Player> player, int wish, int points) {
		Map<Class<? extends Player>, Wish> pointsMap = currentRound();
		pointsMap.put(player, new Wish(wish, points));
		this.points.put(player, this.points.containsKey(player) ? this.points.get(player) + points : points);
	}
	
	long getPoints(Class<? extends Player> player) {
		return points.get(player);
	}
	
	int getAveragePoints(Class<? extends Player> player) {
		return (int) (getPoints(player) / rounds());
	}
	
	int getAverageWish(Class<? extends Player> player) {
		return (int) (getTotalWish(player) / rounds());
	}
	
	long getTotalWish(Class<? extends Player> player) {
		long sum = wishesOnRounds.stream()
				.mapToLong(m -> m.containsKey(player) ? m.get(player).wish : 0)
				.sum();
		return sum;
	}
	
	private Map<Class<? extends Player>, Wish> currentRound() {
		return wishesOnRounds.get(wishesOnRounds.size()-1);
	}
	
	Optional<Map<Class<? extends Player>, Wish>> lastRound() {
		return rounds() > 0 ? Optional.of(wishesOnRounds.get(wishesOnRounds.size()-2)) : Optional.empty();
	}
	
	public <T> T withLastRound(Function<List<Wish>, T> with, Supplier<T> without) {
		if (rounds() > 0) return with.apply(new ArrayList<>(lastRound().get().values()));
		else return without.get();
	}
	
	Map<Class<? extends Player>, Wish> getWishesMapOnRound(int round) {
		return wishesOnRounds.get(0);
	}
	
	public List<Wish> getWishesOnRound(int round) {
		return new ArrayList<>(wishesOnRounds.get(0).values());
	}
	
	Map<Class<? extends Player>, Long> getPointsMap() {
		return points;
	}
	
	public int rounds() {
		return wishesOnRounds.size()-1;
	}
}
