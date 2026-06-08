package domain.card;

import java.util.Objects;

public record Card(Rank rank, Suit suit ) {
	public Card{
		Objects.requireNonNull(rank);
		Objects.requireNonNull(suit);
	}
	
	@Override
	public final String toString() {
		return rank.symbol() + " " + suit.name();
	}
	
	
	
	
}
