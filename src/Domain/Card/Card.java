package Domain.Card;

import java.util.Objects;

public record Card(Rank rank, Couleur color ) {
	public Card{
		Objects.requireNonNull(rank);
		Objects.requireNonNull(color);
	}
	
	@Override
	public final String toString() {
		return rank.symbole() + color.name();
	}
	
	
	
	
}
