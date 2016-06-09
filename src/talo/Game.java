package talo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import talo.util.Effect;

public class Game {
	
	private ArrayList<Class<? extends Player>> playerClasses = new ArrayList<>();
	private History history;
	
	public void addPlayer(Class<? extends Player> player) {
		playerClasses.add(player);
	}
	
	public void play(int rounds) {
		history = new History();
		class WishPair {
			Player p;
			Integer w;
			WishPair(Player p_, Integer w_) {
				p = p_; w = w_;
			}
		}
		List<Player> players = new ArrayList<>();
		for (Class<? extends Player> pc : playerClasses) {
			try {
				players.add(pc.newInstance());
			} catch (Exception e) {}
		}
		for (int i = 0; i < rounds; i++) {
			history.nextRound();
			ArrayList<WishPair> wishes = new ArrayList<>();
			for (Player p : players) wishes.add(new WishPair(p, p.getWish(history)));
			wishes.sort((w1, w2) -> w1.w - w2.w);
			int median = wishes.get((wishes.size() - 1) / 2).w;
			for (WishPair w : wishes) {
				if (w.w <= median) {
					history.addPoints(w.p.getClass(), w.w, w.w);
				} else {
					history.addPoints(w.p.getClass(), w.w, 0);
				}
			}
		}
		history.nextRound();
	}
	
	public void printScores() {
		System.out.println(Effect.get().bold()
			+ String.format("%1$-25s │ %2$13s │ %3$14s │ %4$13s │ %5$13s",
					"BOT", "AVG WISH", "TOTAL WISH", "AVG POINTS", "TOTAL POINTS")
			+ Effect.reset());
		
		List<Map.Entry<Class<? extends Player>, Long>> entries = new ArrayList<>(history.getPointsMap().entrySet());
		entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
		
		for (Map.Entry<Class<? extends Player>, Long> score : entries) {
			PlayerDetails details = score.getKey().getAnnotation(PlayerDetails.class);
			Effect.Color bgcolor = details == null ? Effect.Color.BLACK : details.color();
			String name = details == null ? score.getKey().getSimpleName() : details.name();
			System.out.println(Effect.get().bgcolor(bgcolor).color(Effect.Color.WHITE)
				+ String.format("%1$-25s │ %2$13d │ %3$14d │ %4$13d │ %5$13d", name,
						history.getAverageWish(score.getKey()),
						history.getTotalWish(score.getKey()),
						history.getAveragePoints(score.getKey()),
						score.getValue())
				+ Effect.reset());
		}
	}
}
