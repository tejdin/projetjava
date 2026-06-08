package views;

import domain.card.Card;
import domain.hand.Type;
import domain.others.Blind;
import domain.others.Planet;
import model.GameState;

import java.util.List;

public interface View {
    void displayWelcome();
    
    void displayGameState(GameState state);
    
    void displayDrawnCards(List<Card> cards);

    PlayerAction requestAction(List<Card> hand, int discardsRemaining);
    
    void displayHandType(Type type, int score);
    
    void displayBlindBeaten(Blind blind);
    
    void displayObtainedPlanet(Planet planet);
    
    void displayGameOver(int blindsBeaten, int totalScore, int highScore, boolean newRecord);

    void displayMessage(String message);
}
