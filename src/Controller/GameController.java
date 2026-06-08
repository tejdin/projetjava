package controller;

import domain.hand.HandDetector;
import domain.hand.Type;
import domain.others.Blind;
import domain.others.Planet;
import model.Deck;
import model.GameState;
import model.HighScore;
import views.PlayerAction;
import views.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class GameController {

    private final View view;
    private final HandDetector detector = new HandDetector();
    private final Random random = new Random();
    private GameState state;
    private Deck deck;

    public GameController(View view) {
        this.view = Objects.requireNonNull(view, "La view ne peut pas être null");
        initialiserPartie();
    }

    public void run() {
        view.displayWelcome();

        var blindsBeaten = 0;
        var totalScore = 0;
        while (true) {
            var beaten = jouerBlind(nextBlind(blindsBeaten + 1));
            totalScore += state.score();
            if (!beaten) break;
            blindsBeaten++;
        }

        var previousBest = HighScore.load();
        var newRecord = totalScore > previousBest;
        if (newRecord) HighScore.save(totalScore);
        view.displayGameOver(blindsBeaten, totalScore, Math.max(previousBest, totalScore), newRecord);
    }

    private Blind nextBlind(int numero) {
        var names = List.of("Petit Blind", "Grand Blind", "Boss Blind");
        var name = names.get((numero - 1) % names.size()) + " #" + numero;
        var target = (int) (100 * numero);
        return new Blind(name, target);
    }

    private boolean jouerBlind(Blind blind) {
        state.setCurrentBlind(blind);
        state.resetScore();
        state.resetHands(4);
        state.resetDiscards(3);
        view.displayGameState(state);

        while (state.handsRemaining() > 0 && !state.isBlindBeaten()) {
            jouerTour();
            state.decrementHands();
            view.displayGameState(state);
        }

        if (state.isBlindBeaten()) {
            view.displayBlindBeaten(blind);
            donnerPlaneteAleatoire();
            return true;
        }
        return false;
    }

    private void jouerTour() {
        var hand = new ArrayList<>(deck.draw(8));
        view.displayDrawnCards(hand);

        while (true) {
            var action = view.requestAction(hand, state.discardsRemaining());
            var indices = action.indices();
            var chosen = indices.stream().map(hand::get).toList();

            if (action.kind() == PlayerAction.Kind.DISCARD && state.discardsRemaining() > 0) {
                deck.discard(chosen);
                var fresh = deck.draw(indices.size());
                IntStream.range(0, indices.size())
                        .forEach(k -> hand.set(indices.get(k), fresh.get(k)));
                state.decrementDiscards();
                view.displayDrawnCards(hand);
                continue;
            }

            var discardedCards = IntStream.range(0, hand.size())
                    .filter(i -> !indices.contains(i))
                    .mapToObj(hand::get)
                    .toList();

            var handType = detector.detect(chosen);
            var score = calculerScore(handType);
            state.addScore(score);
            state.setCardsInHand(chosen);

            view.displayHandType(handType, score);
            deck.discard(discardedCards);
            return;
        }
    }

    private int calculerScore(Type handType) {
        return (handType.points() + state.bonusChips(handType))
                * (handType.mult() + state.bonusMult(handType));
    }

    private void donnerPlaneteAleatoire() {
        var planets = Planet.values();
        var planet = planets[random.nextInt(planets.length)];
        view.displayObtainedPlanet(planet);
        state.applyPlanet(planet);
    }

    private void initialiserPartie() {
        deck = new Deck();
        state = new GameState();
    }
}
