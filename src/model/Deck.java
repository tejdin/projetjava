package model;

import domain.card.Card;
import domain.card.Suit;
import domain.card.Rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Deck {
    private final List<Card> drawPile;
    private final List<Card> discardPile;

    public Deck() {
        this.discardPile = new ArrayList<>();
        this.drawPile = new ArrayList<>(Arrays.stream(Suit.values())
                .flatMap(c -> Arrays.stream(Rank.values()).map(r -> new Card(r, c)))
                .toList());
        Collections.shuffle(this.drawPile);
    }

    public List<Card> draw(int n) {
        if (n < 0) throw new IllegalArgumentException("The number of cards to draw must be positive or zero.");

        if (drawPile.size() < n) reshuffle();

        int count = Math.min(n, drawPile.size());
        int from = drawPile.size() - count;
        var drawn = new ArrayList<>(drawPile.subList(from, drawPile.size()));
        drawPile.subList(from, drawPile.size()).clear();
        return drawn;
    }

    public void discard(List<Card> cards) {
        Objects.requireNonNull(cards, "The card list cannot be null");
        cards.forEach(c -> Objects.requireNonNull(c, "The discard pile cannot contain a null card"));
        discardPile.addAll(cards);
    }

    private void reshuffle() {
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile);
    }

    public int remainingCards() {
        return drawPile.size();
    }
}
