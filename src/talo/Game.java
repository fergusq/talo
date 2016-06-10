package talo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static java.util.stream.Collectors.joining;
import java.util.function.Function;

import talo.util.Effect;

public class Game {
	
	private ArrayList<Class<? extends Player>> playerClasses = new ArrayList<>();
	private HashMap<Class<? extends Player>, String> comments = new HashMap<>();
	private HashMap<Class<? extends Player>, Integer> wishes = new HashMap<>();
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
				this.wishes.put(p.getClass(), wish);
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
	
	private class Scoreboard {
		private class Column {
			private String name, nameFormat, dataFormat;
			private Function<Class<? extends Player>, Object> data;
		}
		private ArrayList<Column> columns = new ArrayList<>();
		
		public void addColumn(String nameFormat, String dataFormat,
				String name, Function<Class<? extends Player>, Object> data) {
			Column c = new Column();
			c.name = name;
			c.nameFormat = nameFormat;
			c.dataFormat = dataFormat;
			c.data = data;
			columns.add(c);			
		}
		
		public void printScores() {
			String nameFormat = columns.stream().map(c -> c.nameFormat).collect(joining(" │ "));
			Object[] names = columns.stream().map(c -> c.name).toArray();
			System.out.println(Effect.get().bold()
				+ String.format(nameFormat, names)
				+ Effect.reset());
				
			List<Map.Entry<Class<? extends Player>, Long>> entries
				= new ArrayList<>(history.getPointsMap().entrySet());
			entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
			
			for (Map.Entry<Class<? extends Player>, Long> score : entries) {
				PlayerDetails details = score.getKey().getAnnotation(PlayerDetails.class);
				Effect.Color bgcolor = details == null ? Effect.Color.BLACK : details.color();
				
				String dataFormat = columns.stream().map(c -> c.dataFormat).collect(joining(" │ "));
				Object[] datas = columns.stream().map(c -> c.data.apply(score.getKey())).toArray();
				
				System.out.println(Effect.get().bgcolor(bgcolor).color(Effect.Color.WHITE)
					+ String.format(dataFormat, datas)
					+ Effect.reset());
			}
		}
	}
	
	public void printScores() {
		Scoreboard board = new Scoreboard();
		board.addColumn("%-25s", "%-25s", "BOT", c -> {
			PlayerDetails details = c.getAnnotation(PlayerDetails.class);
			return details == null ? c.getSimpleName() : details.name();
		});
		board.addColumn("%12s", "%12d", "TOTAL POINTS", history::getPoints);
		board.addColumn("%10s", "%10.2f", "AVG POINTS", history::getAveragePoints);
		board.addColumn("%14s", "%14d", "TOTAL WISH", history::getTotalWish);
		board.addColumn("%13s", "%13.2f", "AVG WISH", history::getAverageWish);
		board.addColumn("%10s", "%10d", "LAST WISH", wishes::get);
		board.addColumn("%6s", "%6.2f", "WIN %", history::getWinningPercent);
		board.addColumn("%15s", "%15s", "COMMENT", comments::get);
		
		board.printScores();
	}
}
