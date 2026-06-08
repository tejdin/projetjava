package views.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.util.stream.IntStream;

import domain.others.Planet;


public final class PlanetsView {

	private final int x;
	private final int y;
	private final int width;

	public PlanetsView(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
	}

	public void draw(Graphics2D g, List<Planet> planets) {
		var lineCount = Math.max(planets.size(), 1);
		var height = 60 + lineCount * 28 + 10;

		g.setColor(new Color(30, 30, 40, 220));
		g.fillRoundRect(x, y, width, height, 16, 16);

		g.setColor(new Color(180, 140, 255));
		g.setFont(new Font("SansSerif", Font.BOLD, 22));
		g.drawString("Planets", x + 20, y + 35);

		g.setFont(new Font("SansSerif", Font.PLAIN, 15));
		if (planets.isEmpty()) {
			g.setColor(new Color(200, 200, 200));
			g.drawString("(none yet)", x + 20, y + 68);
			return;
		}

		g.setColor(Color.WHITE);
		IntStream.range(0, planets.size()).forEach(i -> {
			var p = planets.get(i);
			g.drawString("- " + p.displayName() + " (" + p.handType().displayName() + ")", x + 20, y + 68 + i * 28);
		});
	}
}
