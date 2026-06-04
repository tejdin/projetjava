package Views;
import com.github.forax.zen.Application;
import com.github.forax.zen.PointerEvent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Controller.GameController;
import Domain.Card.Card;
import Domain.Card.Couleur;
import Domain.Card.Rank;
import Domain.others.Blind;
import Model.GameState;
import Views.component.CardView;
import Views.component.BoardView;
import Views.component.DeckView;

public class Main {

	public static void main(String[] args) {
	    Application.run(new Color(20, 90, 50), context -> {
	        var info = context.getScreenInfo();
	        int w = info.width(), h = info.height();

	        GameState state = new GameState();
	        state.setCurrentBlind(new Blind("Small Blind", 300));
	        state.addScore(120);

	        BoardView board = new BoardView(40, 100);

	        List<CardView> cartes = new ArrayList<>();
	        for (int i = 0; i < 5; i++) {
	            int x = 200 + i * 150;
	            cartes.add(new CardView(new Card(Rank.AS, Couleur.PIQUE), x, 400));
	        }
	        
	        

	        for (;;) {
	        	context.renderFrame(g -> {
	        	    g.setColor(new Color(20, 90, 50));
	        	    g.fillRect(0, 0, w, h);
	        	    board.draw(g, state);
	        	    for (CardView c : cartes) {
	        	        c.draw(g);
	        	    }
	        	});

	            var e = context.pollOrWaitEvent(100);
	            if(e == null)continue;
	            switch(e) {
	            case PointerEvent e1 : {
	            	if(e1.action() == PointerEvent.Action.POINTER_DOWN) {
	            		for(var card : cartes) {
	            			if(card.itsMe(e1.location().x(), e1.location().y()))
	            			{
	            				 card.selected();
	            			}
	            		}
	            	}
	            }
				default:
					break;

	            }
	        }
	    });
	}
}