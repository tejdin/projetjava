package Views;

import Domain.Card.Card;
import Domain.Hand.Type;
import Domain.others.Blind;
import Domain.others.Planet;
import Model.GameState;

import java.util.List;

public interface View {
    void displayWelcome();
    
    void displayGameState(GameState state);
    
    void displayDrawnCards(List<Card> cards);

    List<Integer> requestSelection(List<Card> cards);
    
    void displayHandType(Type type, int score);
    
    void displayBlindBeaten(Blind blind);
    
    void displayObtainedPlanet(Planet planet);
    
    void displayVictory();
    
    void displayDefeat();
    
    void displayMessage(String message);
}
