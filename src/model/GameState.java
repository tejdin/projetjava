package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import domain.card.*;
import domain.hand.*;
import domain.others.*;

public class GameState {
    private Blind currentBlind;
    private List<Card> cardsInHand;
    private int handsRemaining;
    private int discardsRemaining;
    private int score;
    private final Map<Type, Integer> bonusChips;
    private final Map<Type, Integer> bonusMult;

    public GameState() {
        this.currentBlind = null;
        this.cardsInHand = new ArrayList<>();
        this.handsRemaining = 4;
        this.discardsRemaining = 3;
        this.score = 0;
        this.bonusChips = new HashMap<>();
        this.bonusMult = new HashMap<>();
        for (Type t : Type.values()) {
            bonusChips.put(t, 0);
            bonusMult.put(t, 0);
        }
    }
    
    public int score() {
        return score;
    }
    
    public void addScore(int points) {
        this.score += points;
    }
    
    public void resetScore() {
        this.score = 0;
    }
    
    public Blind currentBlind() {
        return currentBlind;
    }
    
    public void setCurrentBlind(Blind blind) {
        Objects.requireNonNull(blind);
        this.currentBlind = blind;
    }
    
    public int handsRemaining() {
        return handsRemaining;
    }
    
    public void decrementHands() {
        if (handsRemaining > 0) handsRemaining--;
    }
    
    public void resetHands(int n) {
        this.handsRemaining = n;
    }

    public int discardsRemaining() {
        return discardsRemaining;
    }

    public void decrementDiscards() {
        if (discardsRemaining > 0) discardsRemaining--;
    }

    public void resetDiscards(int n) {
        this.discardsRemaining = n;
    }

    public List<Card> cardsInHand() {
        return cardsInHand;
    }
    
    public void setCardsInHand(List<Card> cards) {
        Objects.requireNonNull(cards);
        this.cardsInHand = cards;
    }
    
    public int bonusChips(Type type) {
        return bonusChips.get(type);
    }
    
    public int bonusMult(Type type) {
        return bonusMult.get(type);
    }
    
    public void applyPlanet(Planet planet) {
        Objects.requireNonNull(planet);
        Type cible = planet.handType();
        bonusChips.merge(cible, planet.bonusPoint(), Integer::sum);
        bonusMult.merge(cible, planet.bonusMult(), Integer::sum);
    }
    
    public boolean isBlindBeaten() {
        return currentBlind != null && score >= currentBlind.score();
    }
    
    public boolean isGameLost() {
        return handsRemaining <= 0 && !isBlindBeaten();
    }
}