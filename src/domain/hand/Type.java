package domain.hand;

public enum Type {
	    HIGH_CARD("High Card", 5, 1),
	    PAIR("Pair", 10, 2),
	    TWO_PAIR("Two Pair", 20, 2),
	    THREE_OF_A_KIND("Three of a Kind", 30, 3),
	    STRAIGHT("Straight", 30, 4),
	    FLUSH("Flush", 35, 4),
	    FULL_HOUSE("Full House", 40, 4),
	    FOUR_OF_A_KIND("Four of a Kind", 60, 7),
	    STRAIGHT_FLUSH("Straight Flush", 100, 8);

	    private final String displayName;
	    private final int point;
	    private final int mult;

	    Type(String displayName, int point, int mult) {
	        this.displayName = displayName;
	        this.point = point;
	        this.mult = mult;
	    }

	    public String displayName() {
	        return displayName;
	    }

	    public int points() {
	        return point;
	    }

	    public int mult() {
	        return mult;
	    }

}
