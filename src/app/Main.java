package app;

import com.github.forax.zen.Application;

import java.awt.Color;

import controller.GameController;
//import views.ConsoleView;
import views.GraphicalView;

public class Main {

	public static void main(String[] args) {
		Application.run(new Color(20, 90, 50), context -> {
			var view = new GraphicalView(context);
			new GameController(view).run();
		});
	}
}
