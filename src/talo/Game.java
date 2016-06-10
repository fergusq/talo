package talo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import talo.util.Effect;

public class Game {
	
	private ArrayList<Class<? extends Player>> playerClasses = new ArrayList<>();
	private HashMap<Class<? extends Player>, String> comments = new HashMap<>();
	private History history;
	
	public void addPlayer(Class<? extends Player> player) {
		playerClasses.add(player);
	}
	
	public void play(int game, int rounds) {
		System.out.print("[Game " + game + "]");
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
			for (Player p : players) {
				int wish = p.getWish(history);
				if (wish < 0) wish = 0;
				wishes.add(new WishPair(p, wish));
			}
			wishes.sort((w1, w2) -> w1.w - w2.w);
			int median = wishes.get((wishes.size() - 1) / 2).w;
			for (WishPair w : wishes) {
				if (w.w <= median) {
					history.addPoints(w.p.getClass(), w.w, w.w);
				} else {
					history.addPoints(w.p.getClass(), w.w, 0);
				}
			}
			System.out.print(String.format("\r[Game %s] %d/%d (%.2f %%) : %d",
				game, i+1, rounds, 100d*(i+1)/rounds, median));
		}
		for (Player p : players) {
			comments.put(p.getClass(), p.getComment());
		}
		history.nextRound();
		System.out.println();
	}
	
	public void printScores() {
		System.out.println(Effect.get().bold()
			+ String.format("%1$-25s │ %2$12s │ %3$10s │ %4$14s │ %5$13s │ %6$6s │ %7$15s",
					"BOT",
					"TOTAL POINTS", "AVG POINTS",
					"TOTAL WISH", "AVG WISH",
					"WIN %", "COMMENT")
			+ Effect.reset());
		
		List<Map.Entry<Class<? extends Player>, Long>> entries = new ArrayList<>(history.getPointsMap().entrySet());
		entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
		
		for (Map.Entry<Class<? extends Player>, Long> score : entries) {
			PlayerDetails details = score.getKey().getAnnotation(PlayerDetails.class);
			Effect.Color bgcolor = details == null ? Effect.Color.BLACK : details.color();
			String name = details == null ? score.getKey().getSimpleName() : details.name();
			System.out.println(Effect.get().bgcolor(bgcolor).color(Effect.Color.WHITE)
				+ String.format("%1$-25s │ %2$12d │ %3$10.2f │ %4$14d │ %5$13.2f │ %6$6.2f │ %7$15s",
						name,
						score.getValue(),
						history.getAveragePoints(score.getKey()),
						history.getTotalWish(score.getKey()),
						history.getAverageWish(score.getKey()),
						history.getWinningPercent(score.getKey()),
						comments.get(score.getKey()))
				+ Effect.reset());
		}
	}
}
