package views;

import java.util.List;
import java.util.Objects;


public record PlayerAction(Kind kind, List<Integer> indices) {

	public enum Kind { PLAY, DISCARD }

	public PlayerAction {
		Objects.requireNonNull(kind);
		indices = List.copyOf(indices);
	}
}
