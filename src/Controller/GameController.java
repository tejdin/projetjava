package Controller;

import Domain.Hand.HandDetector;
import Domain.Hand.Type;
import Domain.others.Blind;
import Domain.others.Planet;
import Model.Deck;
import Model.GameState;
import Views.View;

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
    private List<Blind> blinds;

    public GameController(View view) {
        this.view = Objects.requireNonNull(view, "La view ne peut pas être null");
        initialiserPartie();
    }

    public void run() {
        view.displayWelcome();
        for (var blind : blinds) {
            if (!jouerBlind(blind)) {
                view.displayDefeat();
                return;
            }
        }
        view.displayVictory();
    }

    private boolean jouerBlind(Blind blind) {
        state.setCurrentBlind(blind);
        state.resetScore();
        state.resetHands(4);
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
        var drawnCards = deck.draw(8);
        view.displayDrawnCards(drawnCards);

        var selectedIndices = view.requestSelection(drawnCards);
        var selectedCards = selectedIndices.stream().map(drawnCards::get).toList();
        var discardedCards = IntStream.range(0, drawnCards.size())
                .filter(i -> !selectedIndices.contains(i))
                .mapToObj(drawnCards::get)
                .toList();

        var handType = detector.detect(selectedCards);
        var score = calculerScore(handType);
        state.addScore(score);
        state.setCardsInHand(selectedCards);

        view.displayHandType(handType, score);
        deck.discard(discardedCards);
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
        blinds = List.of(
                new Blind("Petit Blind", 300),
                new Blind("Grand Blind", 600),
                new Blind("Boss Blind", 1200)
        );
    }
}
