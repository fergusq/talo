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
		
		@Override
		public String toString() {
			return "[" + wish + ", " + roundPoints + "]";
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
	
	double getAveragePoints(Class<? extends Player> player) {
		return (double) getPoints(player) / rounds();
	}
	
	double getAverageWish(Class<? extends Player> player) {
		return (double) getTotalWish(player) / rounds();
	}
	
	long getTotalWish(Class<? extends Player> player) {
		long sum = wishesOnRounds.stream().limit(rounds())
				.mapToLong(m -> m.get(player).wish)
				.sum();
		return sum;
	}
	
	double getWinningPercent(Class<? extends Player> player) {
		long sum = getRoundsWon(player);
		return (double) sum / rounds() * 100;
	}
	
	long getRoundsWon(Class<? extends Player> player) {
		return wishesOnRounds.stream().limit(rounds())
				.filter(m -> m.get(player).roundPoints > 0)
				.count();
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
		return wishesOnRounds.get(round);
	}
	
	public List<Wish> getWishesOnRound(int round) {
		return new ArrayList<>(wishesOnRounds.get(round).values());
	}
	
	Map<Class<? extends Player>, Long> getPointsMap() {
		return points;
	}
	
	public int rounds() {
		return wishesOnRounds.size()-1;
	}
}
