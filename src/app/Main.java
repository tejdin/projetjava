import com.github.forax.zen.Application;

import java.awt.Color;

import controller.GameController;
//import views.ConsoleView;
import views.GraphicalView;

public class Main {

	public static void main(String[] args) {
		// if (args.length > 0 && args[0].equals("console")) {
		// 	new GameController(new ConsoleView()).run();
		// 	return;
		// }

		Application.run(new Color(20, 90, 50), context -> {
			var view = new GraphicalView(context);
			new GameController(view).run();
		});
	}
}
