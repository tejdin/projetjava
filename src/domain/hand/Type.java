package domain.hand;

public enum Type {
	    CARTE_HAUTE("Carte haute", 5, 1),
	    PAIRE("Paire", 10, 2),
	    DOUBLE_PAIRE("Double paire", 20, 2),
	    BRELAN("Brelan", 30, 3),
	    SUITE("Suite", 30, 4),
	    COULEUR("Couleur", 35, 4),
	    FULL("Full", 40, 4),
	    CARRE("Carré", 60, 7),
	    QUINTE_FLUSH("Quinte flush", 100, 8);

	    private final String nom;
	    private final int point;
	    private final int mult;

	    Type(String nom, int point, int mult) {
	        this.nom = nom;
	        this.point = point;
	        this.mult = mult;
	    }

	    public String nom() {
	        return nom;
	    }

	    public int points() {
	        return point;
	    }

	    public int mult() {
	        return mult;
	    }

}
