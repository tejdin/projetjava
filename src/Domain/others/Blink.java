package Domain.others;

import java.util.Objects;

public record Blink(String name, int score) {
	public Blink {
		Objects.requireNonNull(name);
		if(score <= 0)throw new IllegalArgumentException();
	}

}
