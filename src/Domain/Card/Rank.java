package domain.card;

public enum Rank {
	DEUX("2", 2, 2),
    TROIS("3", 3, 3),
    QUATRE("4", 4, 4),
    CINQ("5", 5, 5),
    SIX("6", 6, 6),
    SEPT("7", 7, 7),
    HUIT("8", 8, 8),
    NEUF("9", 9, 9),
    DIX("10", 10, 10),
    VALET("J", 10, 11),
    DAME("Q", 10, 12),
    ROI("K", 10, 13),
    AS("A", 11, 14);
	

	private final String s;
	private final int value;
	private final int ordre;
	
    Rank(String s, int value, int ordre) {
        this.s = s;
        this.value = value;
        this.ordre = ordre;
    }
    
    public String symbole() {
    	return s;
    }

    public int value() {
    	return value;
    }

    public int ordre() {
    	return ordre;
    }
    
}
