package Model;

import Domain.Card.Card;
import Domain.Card.Couleur;
import Domain.Card.Rank;

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
        this.drawPile = new ArrayList<>(Arrays.stream(Couleur.values())
                .flatMap(c -> Arrays.stream(Rank.values()).map(r -> new Card(r, c)))
                .toList());
        Collections.shuffle(this.drawPile);
    }

    public List<Card> draw(int n) {
        if (n < 0) throw new IllegalArgumentException("Le nombre de cartes à piocher doit être positif ou nul.");

        if (drawPile.size() < n) reshuffle();

        int count = Math.min(n, drawPile.size());
        int from = drawPile.size() - count;
        var drawn = new ArrayList<>(drawPile.subList(from, drawPile.size()));
        drawPile.subList(from, drawPile.size()).clear();
        return drawn;
    }

    public void discard(List<Card> cards) {
        Objects.requireNonNull(cards, "La liste de cartes ne peut pas être null");
        cards.forEach(c -> Objects.requireNonNull(c, "La défausse ne peut pas contenir de carte null"));
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
