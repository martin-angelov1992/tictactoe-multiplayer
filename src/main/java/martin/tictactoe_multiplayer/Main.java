package martin.tictactoe_multiplayer;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new TicTacToeModule());
		Game game = injector.getInstance(Game.class);
		game.run();
	}
}
