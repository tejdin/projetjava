package views;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.PointerEvent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import domain.card.Card;
import domain.hand.Type;
import domain.others.Blind;
import domain.others.Planet;
import model.GameState;
import model.HighScore;
import views.component.BoardView;
import views.component.CardView;
import views.component.PlanetsView;

public final class GraphicalView implements View {

	private static final int MAX_SELECTION = 5;
	private static final Color BACKGROUND = new Color(20, 90, 50);

	private final ApplicationContext context;
	private final int width;
	private final int height;
	private final BoardView board = new BoardView(40, 100);
	private final PlanetsView planetsView;

	// "PLAY" and "DISCARD" buttons
	private final int buttonWidth = 200;
	private final int buttonHeight = 50;
	private final int buttonY;
	private final int playButtonX;
	private final int discardButtonX;

	// mutable display state
	private GameState state;
	private List<CardView> cardViews = List.of();
	private final List<Planet> planets = new ArrayList<>();
	private int discardsRemaining;
	private boolean selecting = false;
	private String overlay = null;

	public GraphicalView(ApplicationContext context) {
		this.context = Objects.requireNonNull(context);
		var info = context.getScreenInfo();
		this.width = info.width();
		this.height = info.height();
		this.buttonY = height - 80;
		this.playButtonX = width / 2 - buttonWidth - 10;
		this.discardButtonX = width / 2 + 10;
		this.planetsView = new PlanetsView(width - 260, 100, 220);
	}

	@Override
	public void displayWelcome() {
		showOverlayUntilClick("BALATRI\nBest score: " + HighScore.load()
				+ "\nClick to start");
	}

	@Override
	public void displayGameState(GameState state) {
		this.state = Objects.requireNonNull(state);
		render();
	}

	@Override
	public void displayDrawnCards(List<Card> cards) {
		Objects.requireNonNull(cards);
		var n = cards.size();
		var total = n * CardView.WIDTH + (n - 1) * 20;
		var startX = (width - total) / 2;
		var y = height / 2 - CardView.HEIGHT / 2;
		this.cardViews = IntStream.range(0, n)
				.mapToObj(i -> new CardView(cards.get(i), startX + i * (CardView.WIDTH + 20), y))
				.toList();
		render();
	}

	@Override
	public PlayerAction requestAction(List<Card> hand, int discardsRemaining) {
		Objects.requireNonNull(hand);
		this.discardsRemaining = discardsRemaining;
		selecting = true;
		try {
			while (true) {
				render();
				if (context.pollOrWaitEvent(50) instanceof PointerEvent pe
						&& pe.action() == PointerEvent.Action.POINTER_DOWN) {
					var px = pe.location().x();
					var py = pe.location().y();
					var count = selectedCount();
					if (inButton(playButtonX, px, py) && count == MAX_SELECTION) {
						return new PlayerAction(PlayerAction.Kind.PLAY, selectedIndices());
					}
					if (inButton(discardButtonX, px, py) && count >= 1 && discardsRemaining > 0) {
						return new PlayerAction(PlayerAction.Kind.DISCARD, selectedIndices());
					}
					handleCardClick(px, py);
				}
			}
		} finally {
			selecting = false;
		}
	}

	@Override
	public void displayHandType(Type type, int score) {
		Objects.requireNonNull(type);
		showOverlayUntilClick("Hand: " + type.displayName() + "\nScore: " + score);
	}

	@Override
	public void displayBlindBeaten(Blind blind) {
		Objects.requireNonNull(blind);
		showOverlayUntilClick("Blind beaten!\n" + blind.name());
	}

	@Override
	public void displayObtainedPlanet(Planet planet) {
		Objects.requireNonNull(planet);
		planets.add(planet);
		showOverlayUntilClick("Planet obtained: " + planet.displayName()
				+ "\nBoosts: " + planet.handType().displayName());
	}

	@Override
	public void displayGameOver(int blindsBeaten, int totalScore, int highScore, boolean newRecord) {
		var record = newRecord ? "\nNEW RECORD!" : "";
		showOverlayUntilClick("GAME OVER"
				+ "\nBlinds beaten: " + blindsBeaten
				+ "\nTotal score: " + totalScore
				+ "\nBest score: " + highScore + record);
	}

	@Override
	public void displayMessage(String message) {
		Objects.requireNonNull(message);
		showOverlayUntilClick(message);
	}


	private void handleCardClick(int px, int py) {
		cardViews.stream()
				.filter(c -> c.contains(px, py))
				.findFirst()
				.filter(c -> c.isSelected() || selectedCount() < MAX_SELECTION)
				.ifPresent(CardView::toggle);
	}

	private long selectedCount() {
		return cardViews.stream().filter(CardView::isSelected).count();
	}

	private List<Integer> selectedIndices() {
		return IntStream.range(0, cardViews.size())
				.filter(i -> cardViews.get(i).isSelected())
				.boxed()
				.toList();
	}

	private boolean inButton(int x0, int px, int py) {
		return px >= x0 && px <= x0 + buttonWidth
				&& py >= buttonY && py <= buttonY + buttonHeight;
	}

	private void showOverlayUntilClick(String text) {
		overlay = text;
		render();
		while (!(context.pollOrWaitEvent(50) instanceof PointerEvent pe
				&& pe.action() == PointerEvent.Action.POINTER_DOWN)) {
		}
		overlay = null;
		render();
	}


	private void render() {
		context.renderFrame(g -> {
			g.setColor(BACKGROUND);
			g.fillRect(0, 0, width, height);
			drawTitle(g);
			if (state != null && state.currentBlind() != null) {
				board.draw(g, state);
			}
			planetsView.draw(g, planets);
			cardViews.forEach(c -> c.draw(g));
			if (selecting) {
				drawButtons(g);
			}
			if (overlay != null) {
				drawOverlay(g, overlay);
			}
		});
	}

	private void drawTitle(Graphics2D g) {
		var title = "BALATRI";
		g.setColor(new Color(255, 190, 0));
		g.setFont(new Font("SansSerif", Font.BOLD, 40));
		var titleWidth = g.getFontMetrics().stringWidth(title);
		g.drawString(title, (width - titleWidth) / 2, 60);
	}

	private void drawButtons(Graphics2D g) {
		var count = selectedCount();
		drawButton(g, playButtonX, count == MAX_SELECTION,
				"PLAY (" + count + "/" + MAX_SELECTION + ")");
		drawButton(g, discardButtonX, count >= 1 && discardsRemaining > 0,
				"DISCARD (" + discardsRemaining + ")");
	}

	private void drawButton(Graphics2D g, int x0, boolean enabled, String label) {
		g.setColor(enabled ? new Color(220, 160, 0) : new Color(90, 90, 90));
		g.fillRoundRect(x0, buttonY, buttonWidth, buttonHeight, 14, 14);
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, 18));
		var labelWidth = g.getFontMetrics().stringWidth(label);
		g.drawString(label, x0 + (buttonWidth - labelWidth) / 2, buttonY + 33);
	}

	private void drawOverlay(Graphics2D g, String text) {
		g.setColor(new Color(0, 0, 0, 190));
		g.fillRect(0, 0, width, height);

		var lines = text.split("\n");
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, 34));
		var fm = g.getFontMetrics();
		var lineHeight = fm.getHeight();
		var startY = height / 2 - lines.length * lineHeight / 2;
		IntStream.range(0, lines.length).forEach(i -> {
			var w = fm.stringWidth(lines[i]);
			g.drawString(lines[i], (width - w) / 2, startY + i * lineHeight);
		});

		var hint = "Click to continue";
		g.setColor(new Color(255, 190, 0));
		g.setFont(new Font("SansSerif", Font.PLAIN, 16));
		var hintWidth = g.getFontMetrics().stringWidth(hint);
		g.drawString(hint, (width - hintWidth) / 2, startY + lines.length * lineHeight + 30);
	}
}
