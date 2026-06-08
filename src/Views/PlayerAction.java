package views;

import java.util.List;
import java.util.Objects;


public record PlayerAction(Kind kind, List<Integer> indices) {

	public enum Kind { PLAY, DISCARD }

	//Choisi entre défausse ou jouer les cartes séléctionnes
	public PlayerAction {
		Objects.requireNonNull(kind);
		indices = List.copyOf(indices);
	}
}
