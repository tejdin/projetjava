package Views.component;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import Model.GameState;
import Domain.others.Blind;

public class BoardView {

	private int x;
	private int y;

	public BoardView(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics2D g, GameState state) {
		g.setColor(new Color(30, 30, 40, 220));
		g.fillRoundRect(x, y, 220, 230, 16, 16);
		

		Blind blind = state.currentBlind();
		String nomBlind = blind.name();
		int objectif    = blind.score();

		g.setColor(new Color(255, 190, 0));
		g.setFont(new Font("SansSerif", Font.BOLD, 26));
		g.drawString(nomBlind, x + 20, y + 45);

		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.PLAIN, 16));
		g.drawString("a battre : " + objectif, x + 20, y + 72);

		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		int ligne = y + 130;
		int pas = 40;
		g.drawString("Score : " + state.score(),          x + 20, ligne);
		g.drawString("Mains : " + state.handsRemaining(), x + 20, ligne += pas);
	}
}