package talo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Describes a text effect that can be represented by an ANSI escape sequence.
 * 
 * @author Pietu1998
 *
 */
public class Effect {

	/**
	 * Represents a color that can be set by an {@code Effect}.
	 */
	public static enum Color {
		/**
		 * The black color.
		 */
		BLACK("0"),
		/**
		 * The red color.
		 */
		RED("1"),
		/**
		 * The green color.
		 */
		GREEN("2"),
		/**
		 * The yellow/brown color.
		 */
		YELLOW("3"),
		/**
		 * The blue color.
		 */
		BLUE("4"),
		/**
		 * The magenta color.
		 */
		MAGENTA("5"),
		/**
		 * The cyan color.
		 */
		CYAN("6"),
		/**
		 * The white color.
		 */
		WHITE("7");

		private String code;

		private Color(String code) {
			this.code = code;
		}
	}

	private List<String> parts;

	private Effect() {
		parts = new ArrayList<String>();
	}

	private Effect(String add) {
		parts = new ArrayList<String>();
		parts.add(add);
	}

	private Effect(List<String> old, String add) {
		parts = new ArrayList<String>(old);
		parts.add(add);
	}

	private Effect(List<String> old, List<String> add) {
		parts = new ArrayList<String>(old);
		parts.addAll(add);
	}

	/**
	 * Returns a new {@code Effect} with the provided effect code added.
	 * 
	 * This method should be used for extend the functionality of this class in
	 * a subclass.
	 * 
	 * @param add
	 *            The effect code to be added. Must be in the format like "34"
	 *            in the escape sequence "\\x1b[34m".
	 * @return The new {@code Effect}
	 */
	protected Effect extend(String add) {
		return new Effect(parts, add);
	}

	/**
	 * Returns a new blank {@code Effect}.
	 * 
	 * @return The empty {@code Effect}
	 */
	public static Effect get() {
		return new Effect();
	}

	/**
	 * Returns a new {@code Effect} that resets any existing effects.
	 * 
	 * This {@code Effect} should be used as is to reset any existing effects to
	 * the default.
	 * 
	 * @return The reseting {@code Effect}
	 */
	public static Effect reset() {
		return new Effect("0");
	}

	/**
	 * Returns a copy of this {@code Effect} that changes the foreground color.
	 * 
	 * @param color
	 *            The color
	 * @return The new {@code Effect}
	 */
	public Effect color(final Color color) {
		return new Effect(parts, "3" + color.code);
	}

	/**
	 * Returns a copy of this {@code Effect} that changes the background color.
	 * 
	 * @param color
	 *            The background color to change to
	 * @return The new {@code Effect}
	 */
	public Effect bgcolor(final Color color) {
		return new Effect(parts, "4" + color.code);
	}

	/**
	 * Returns a copy of this {@code Effect} that bolds text.
	 * 
	 * @return The new {@code Effect}
	 */
	public Effect bold() {
		return new Effect(parts, "1");
	}

	/**
	 * Returns a copy of this {@code Effect} that italicizes text.
	 * 
	 * @return The new {@code Effect}
	 */
	public Effect italic() {
		return new Effect(parts, "2");
	}

	/**
	 * Returns a copy of this {@code Effect} that underlines text.
	 * 
	 * @return The new {@code Effect}
	 */
	public Effect underline() {
		return new Effect(parts, "4");
	}

	/**
	 * Returns a copy of this {@code Effect} that makes text blink.
	 * 
	 * @return The new {@code Effect}
	 */
	public Effect blink() {
		return new Effect(parts, "5");
	}

	/**
	 * Returns a copy of this {@code Effect} that swaps text foreground and
	 * background colors.
	 * 
	 * @return The new {@code Effect}
	 */
	public Effect invert() {
		return new Effect(parts, "7");
	}

	/**
	 * Combines the effects of this {@code Effect} and another {@code Effect},
	 * and returns a copy of this {@code Effect} with all of the resulting
	 * effects.
	 * 
	 * @param other
	 *            The other {@code Effect}
	 * @return The new {@code Effect}
	 */
	public Effect put(Effect other) {
		return new Effect(parts, other.parts);
	}

	/**
	 * Returns a {@code String} representation of this {@code Effect} that can
	 * be used in printing, resulting in the effects of this {@code Effect} to
	 * be applied to the following text.
	 * 
	 * @return The {@code String} representation of this {@code Effect} that can
	 *         be used in printing
	 */
	@Override
	public String toString() {
		return "\u001b[" + parts.stream().collect(Collectors.joining(";"))
				+ "m";
	}
}
