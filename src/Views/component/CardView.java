package Views.component;

import com.github.forax.zen.Application;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import Domain.Card.Card;
import Domain.Card.Rank;
import Domain.Card.Couleur;

public class CardView {
	
	private Card card = null;
	private int x=0;
	private int y=0;
	private boolean selected = false;
	public CardView(Card card,int x,int y) {
	// TODO Auto-generated constructor stub
		this.card=card;
		this.x=x;  
		this.y=y;
	}	
	
	public void draw(Graphics2D g) {
		// shadow effect
		g.setColor(new Color(0, 0, 0, 120));
		g.fillRoundRect(x + 8, y + 8, 90, 130, 16, 16);
		
		// card
		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, 90, 130, 16, 16);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", Font.BOLD, 18));
		g.drawString(card.toString(), x + 8, y + 70);
		
		
		if (selected) {
		    g.setStroke(new BasicStroke(4f));
		    g.setColor(new Color(255, 190, 0));
		    g.drawRoundRect(x, y, 90, 130, 16, 16);
		}
		
	}
	
	public boolean itsMe(int px , int py) {
		return px >= x && px <= x + 90
		        && py >= y && py <= y + 130;
	}
	
	
	public void selected() {
		selected = !selected;
	}
	
	
	
	
	
	
	

}
