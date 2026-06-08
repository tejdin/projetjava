package domain.card;

public enum Rank {
	TWO("2", 2, 2),
    THREE("3", 3, 3),
    FOUR("4", 4, 4),
    FIVE("5", 5, 5),
    SIX("6", 6, 6),
    SEVEN("7", 7, 7),
    EIGHT("8", 8, 8),
    NINE("9", 9, 9),
    TEN("10", 10, 10),
    JACK("J", 10, 11),
    QUEEN("Q", 10, 12),
    KING("K", 10, 13),
    ACE("A", 11, 14);
	

	private final String symbol;
	private final int value;
	private final int order;

    Rank(String symbol, int value, int order) {
        this.symbol = symbol;
        this.value = value;
        this.order = order;
    }

    public String symbol() {
    	return symbol;
    }

    public int value() {
    	return value;
    }

    public int order() {
    	return order;
    }
    
}
