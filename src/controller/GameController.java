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
        this.view = Objects.requireNonNull(view, "The view cannot be null");
        initializeGame();
    }

    public void run() {
        view.displayWelcome();

        var blindsBeaten = 0;
        var totalScore = 0;
        while (true) {
            var beaten = playBlind(nextBlind(blindsBeaten + 1));
            totalScore += state.score();
            if (!beaten) break;
            blindsBeaten++;
        }

        var previousBest = HighScore.load();
        var newRecord = totalScore > previousBest;
        if (newRecord) HighScore.save(totalScore);
        view.displayGameOver(blindsBeaten, totalScore, Math.max(previousBest, totalScore), newRecord);
    }

    private Blind nextBlind(int number) {
        var names = List.of("Petit Blind", "Grand Blind", "Boss Blind");
        var name = names.get((number - 1) % names.size()) + " #" + number;
        var target = (int) (100 * number);
        return new Blind(name, target);
    }

    private boolean playBlind(Blind blind) {
        state.setCurrentBlind(blind);
        state.resetScore();
        state.resetHands(4);
        state.resetDiscards(3);
        view.displayGameState(state);

        while (state.handsRemaining() > 0 && !state.isBlindBeaten()) {
            playTurn();
            state.decrementHands();
            view.displayGameState(state);
        }

        if (state.isBlindBeaten()) {
            view.displayBlindBeaten(blind);
            awardRandomPlanet();
            return true;
        }
        return false;
    }

    private void playTurn() {
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
            var score = computeScore(handType);
            state.addScore(score);
            state.setCardsInHand(chosen);

            view.displayHandType(handType, score);
            deck.discard(discardedCards);
            return;
        }
    }

    private int computeScore(Type handType) {
        return (handType.points() + state.bonusChips(handType))
                * (handType.mult() + state.bonusMult(handType));
    }

    private void awardRandomPlanet() {
        var planets = Planet.values();
        var planet = planets[random.nextInt(planets.length)];
        view.displayObtainedPlanet(planet);
        state.applyPlanet(planet);
    }

    private void initializeGame() {
        deck = new Deck();
        state = new GameState();
    }
}
