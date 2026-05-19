package Views;

import Controller.GameController;

public class Main {

    public static void main(String[] args) {
        var view = new ConsoleView();
        var controller = new GameController(view);
        controller.run();
    }
}
