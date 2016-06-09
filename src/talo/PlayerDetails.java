package talo;

import talo.util.Effect;

@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.TYPE})
public @interface PlayerDetails {
	public String name();
	public Effect.Color color();
}
