package Domain.others;
import Domain.Hand.*;

public enum Planet {
    PLUTON  ("Pluton",  Type.CARTE_HAUTE,   10, 1),
    MERCURE ("Mercure", Type.PAIRE,         15, 1),
    URANUS  ("Uranus",  Type.DOUBLE_PAIRE,  20, 1),
    VENUS   ("Vénus",   Type.BRELAN,        20, 2),
    SATURNE ("Saturne", Type.SUITE,         30, 3),
    JUPITER ("Jupiter", Type.COULEUR,       15, 2),
    TERRE   ("Terre",   Type.FULL,          25, 2),
    MARS    ("Mars",    Type.CARRE,         30, 3),
    NEPTUNE ("Neptune", Type.QUINTE_FLUSH,  40, 4);

    private final String nom;
    private final Type handType;
    private final int bonusPoint;
    private final int bonusMult;

    Planet(String nom, Type handType, int bonusPoint, int bonusMult) {
        this.nom = nom;
        this.handType = handType;
        this.bonusPoint = bonusPoint;
        this.bonusMult = bonusMult;
    }

    public String nom() {
        return nom;
    }

    public Type handType() {
        return handType;
    }

    public int bonusPoint() {
        return bonusPoint;
    }

    public int bonusMult() {
        return bonusMult;
    }
}
