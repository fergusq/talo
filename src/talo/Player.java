package talo;

public interface Player {
	public int getWish(History historySoFar);
	public default String getComment() {
		return "";
	}
}
