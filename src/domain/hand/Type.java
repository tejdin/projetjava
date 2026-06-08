package domain.hand;

public enum Type {
	    HIGH_CARD("Carte haute", 5, 1),
	    PAIR("Paire", 10, 2),
	    TWO_PAIR("Double paire", 20, 2),
	    THREE_OF_A_KIND("Brelan", 30, 3),
	    STRAIGHT("Suite", 30, 4),
	    FLUSH("Couleur", 35, 4),
	    FULL_HOUSE("Full", 40, 4),
	    FOUR_OF_A_KIND("Carré", 60, 7),
	    STRAIGHT_FLUSH("Quinte flush", 100, 8);

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
