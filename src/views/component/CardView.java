package views.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

import domain.card.Card;
import domain.card.Couleur;

public final class CardView {

	public static final int WIDTH = 110;
	public static final int HEIGHT = 155;

	private static final Color RED = new Color(200, 30, 30);
	private static final Color BLACK = new Color(20, 20, 20);

	private final Card card;
	private final int x;
	private final int y;
	private boolean selected = false;

	public CardView(Card card, int x, int y) {
		this.card = Objects.requireNonNull(card);
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 120));
		g.fillRoundRect(x + 6, y + 6, WIDTH, HEIGHT, 14, 14);

		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 14, 14);
		g.setStroke(new BasicStroke(1f));
		g.setColor(new Color(170, 170, 170));
		g.drawRoundRect(x, y, WIDTH, HEIGHT, 14, 14);

		var rank = card.rank().symbole();
		var suit = symbole(card.color());
		g.setColor(isRed(card.color()) ? RED : BLACK);

		// coin haut-gauche : rang puis enseigne
		g.setFont(new Font("SansSerif", Font.BOLD, 18));
		g.drawString(rank, x + 8, y + 25);
		g.setFont(new Font("SansSerif", Font.PLAIN, 16));
		g.drawString(suit, x + 8, y + 44);

		// grand symbole central
		g.setFont(new Font("SansSerif", Font.PLAIN, 46));
		var fm = g.getFontMetrics();
		g.drawString(suit, x + (WIDTH - fm.stringWidth(suit)) / 2, y + HEIGHT / 2 + 20);

		// coin bas-droit (miroir)
		g.setFont(new Font("SansSerif", Font.BOLD, 18));
		var rankWidth = g.getFontMetrics().stringWidth(rank);
		g.drawString(rank, x + WIDTH - 8 - rankWidth, y + HEIGHT - 26);
		g.setFont(new Font("SansSerif", Font.PLAIN, 16));
		var suitWidth = g.getFontMetrics().stringWidth(suit);
		g.drawString(suit, x + WIDTH - 8 - suitWidth, y + HEIGHT - 8);

		// surbrillance si selectionnee
		if (selected) {
			g.setStroke(new BasicStroke(4f));
			g.setColor(new Color(255, 190, 0));
			g.drawRoundRect(x, y, WIDTH, HEIGHT, 14, 14);
		}
	}

	private static boolean isRed(Couleur couleur) {
		return couleur == Couleur.COEUR || couleur == Couleur.CARREAU;
	}

	private static String symbole(Couleur couleur) {
		var codePoint = switch (couleur) {
			case COEUR -> 0x2665;   // coeur
			case CARREAU -> 0x2666; // carreau
			case PIQUE -> 0x2660;   // pique
			case TREFLE -> 0x2663;  // trefle
		};
		return Character.toString(codePoint);
	}

	public boolean contains(int px, int py) {
		return px >= x && px <= x + WIDTH
				&& py >= y && py <= y + HEIGHT;
	}

	public void toggle() {
		selected = !selected;
	}

	public boolean isSelected() {
		return selected;
	}
}
