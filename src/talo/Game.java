package talo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import static java.util.Arrays.stream;

import static java.util.stream.Collectors.joining;
import java.util.function.Function;

import java.io.FileWriter;

import talo.util.Effect;

public class Game {
	
	private ArrayList<Class<? extends Player>> playerClasses = new ArrayList<>();
	private HashMap<Class<? extends Player>, String> comments = new HashMap<>();
	private HashMap<Class<? extends Player>, Integer> wishes = new HashMap<>();
	private History[] history;
	
	public Game(int games) {
		history = new History[games];
	}
	
	public void addPlayer(Class<? extends Player> player) {
		playerClasses.add(player);
	}
	
	public void play(int game, int rounds) {
		try {
			System.out.print("[Game " + (game+1) + "]");
			history[game] = new History();
			class WishPair {
				Player p;
				Integer w;
				WishPair(Player p_, Integer w_) {
					p = p_; w = w_;
				}
			}
			List<Player> players = new ArrayList<>();
			List<FileWriter> plots = new ArrayList<>();
			int i_ = 0;
			for (Class<? extends Player> pc : playerClasses) {
				try {
					players.add(pc.newInstance());
				} catch (Exception e) {}
				plots.add(new FileWriter(i_++ +".txt"));
			}
			for (int i = 0; i < rounds; i++) {
				history[game].nextRound();
				ArrayList<WishPair> wishes = new ArrayList<>();
				for (Player p : players) {
					int wish = p.getWish(history[game]);
					plots.get(players.indexOf(p)).write(wish+"\n");
					this.wishes.put(p.getClass(), wish);
					if (wish < 0) wish = 0;
					wishes.add(new WishPair(p, wish));
				}
				wishes.sort((w1, w2) -> w1.w - w2.w);
				int median = wishes.get((wishes.size() - 1) / 2).w;
				for (WishPair w : wishes) {
					if (w.w <= median) {
						history[game].addPoints(w.p.getClass(), w.w, w.w);
					} else {
						history[game].addPoints(w.p.getClass(), w.w, 0);
					}
				}
				System.out.print(String.format("\r[Game %s] %d/%d (%.2f %%) : %d",
					game+1, i+1, rounds, 100d*(i+1)/rounds, median));
			}
			for (Player p : players) {
				comments.put(p.getClass(), p.getComment());
			}
			for (FileWriter fw : plots) fw.close();
			
			FileWriter fw = new FileWriter("plot.txt");
			
			for (int i = 0; i < players.size(); i++) {
				if (i == 0) fw.write("plot ");
				else fw.write(", ");
				fw.write("\""+i+".txt\" title '"+playerClasses.get(i).getSimpleName()+"' with lines");
			}
			
			fw.close();
			
			history[game].nextRound();
			System.out.println();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
			
			Map<Class<? extends Player>, Long> pointsMap = new HashMap<>();
			for (Class<? extends Player> p : playerClasses) pointsMap.put(p, 0l);
			for (History h : history) {
				for (Map.Entry<Class<? extends Player>, Long> score : h.getPointsMap().entrySet()) {
					pointsMap.put(score.getKey(), pointsMap.get(score.getKey()) + score.getValue());
				}
			}
			
			List<Map.Entry<Class<? extends Player>, Long>> entries
				= new ArrayList<>(pointsMap.entrySet());
			entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
			
			for (Map.Entry<Class<? extends Player>, Long> score : entries) {
				PlayerDetails details = score.getKey().getAnnotation(PlayerDetails.class);
				Effect.Color bgcolor = details == null ? Effect.Color.BLACK : details.color();
				
				String dataFormat = columns.stream().map(c -> c.dataFormat).collect(joining(" │ "));
				Object[] datas = columns.stream().map(c -> c.data.apply(score.getKey())).toArray();
				Effect effect = Effect.get().bgcolor(bgcolor).color(Effect.Color.WHITE);
				
				if(!score.getKey().isAnnotationPresent(Competitor.class)) {
					effect = Effect.get().color(bgcolor);
					
					if(bgcolor == Effect.Color.BLACK) {
						effect = Effect.get().invert();
					}
				}
				
				System.out.println(effect
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
		board.addColumn("%12s", "%12d", "TOTAL POINTS",
			c -> stream(history).mapToLong(h -> h.getPoints(c)).sum());
		board.addColumn("%10s", "%10.2f", "AVG POINTS",
			c -> stream(history).mapToDouble(h -> h.getAveragePoints(c)).average().orElse(0d));
		board.addColumn("%14s", "%14d", "TOTAL WISH",
			c -> stream(history).mapToLong(h -> h.getTotalWish(c)).sum());
		board.addColumn("%13s", "%13.2f", "AVG WISH",
			c -> stream(history).mapToDouble(h -> h.getAverageWish(c)).average().orElse(0d));
		board.addColumn("%10s", "%10d", "LAST WISH", wishes::get);
		board.addColumn("%6s", "%6.2f", "WIN %",
			c -> 100d * stream(history).mapToLong(h -> h.getRoundsWon(c)).sum()
				/ stream(history).mapToInt(h -> h.rounds()).sum());
		board.addColumn("%15s", "%15s", "COMMENT", comments::get);
		
		board.printScores();
	}
}
