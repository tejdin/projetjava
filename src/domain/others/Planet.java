package domain.others;
import domain.hand.*;

public enum Planet {
    PLUTO   ("Pluto",   Type.HIGH_CARD,        10, 1),
    MERCURY ("Mercury", Type.PAIR,             15, 1),
    URANUS  ("Uranus",  Type.TWO_PAIR,         20, 1),
    VENUS   ("Venus",   Type.THREE_OF_A_KIND,  20, 2),
    SATURN  ("Saturn",  Type.STRAIGHT,         30, 3),
    JUPITER ("Jupiter", Type.FLUSH,            15, 2),
    EARTH   ("Earth",   Type.FULL_HOUSE,       25, 2),
    MARS    ("Mars",    Type.FOUR_OF_A_KIND,   30, 3),
    NEPTUNE ("Neptune", Type.STRAIGHT_FLUSH,   40, 4);

    private final String displayName;
    private final Type handType;
    private final int bonusPoint;
    private final int bonusMult;

    Planet(String displayName, Type handType, int bonusPoint, int bonusMult) {
        this.displayName = displayName;
        this.handType = handType;
        this.bonusPoint = bonusPoint;
        this.bonusMult = bonusMult;
    }

    public String displayName() {
        return displayName;
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
