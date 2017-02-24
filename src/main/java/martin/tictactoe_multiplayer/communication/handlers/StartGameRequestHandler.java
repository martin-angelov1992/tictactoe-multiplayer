package martin.tictactoe_multiplayer.communication.handlers;

import javax.inject.Inject;

import martin.tictactoe_multiplayer.Commands.StartNewGame;
import martin.tictactoe_multiplayer.Game;

public class StartGameRequestHandler extends BaseCommandHandler {

	@Inject
	private Game game;

	@Override
	public void handleCommand(Object objCmd) {
		StartNewGame cmd = (StartNewGame)objCmd;
		game.
	}
}