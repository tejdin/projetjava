package Domain.others;

import java.util.Objects;

public record Blind(String name, int score) {
	public Blind {
		Objects.requireNonNull(name);
		if(score <= 0)throw new IllegalArgumentException();
	}

}
