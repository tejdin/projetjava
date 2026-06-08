package views.component;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import model.GameState;
import domain.others.Blind;

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
		String blindName = blind.name();
		int target    = blind.score();

		g.setColor(new Color(255, 190, 0));
		g.setFont(new Font("SansSerif", Font.BOLD, 26));
		g.drawString(blindName, x + 20, y + 45);

		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.PLAIN, 16));
		g.drawString("À battre : " + target, x + 20, y + 72);

		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		int line = y + 120;
		int step = 32;
		g.drawString("Score : " + state.score(),                  x + 20, line);
		g.drawString("Mains : " + state.handsRemaining(),         x + 20, line += step);
		g.drawString("Défausses : " + state.discardsRemaining(),  x + 20, line += step);
	}
}