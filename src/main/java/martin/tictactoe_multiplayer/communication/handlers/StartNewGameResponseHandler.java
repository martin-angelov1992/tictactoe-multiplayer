package martin.tictactoe_multiplayer.communication.handlers;

import javax.inject.Inject;

import martin.tictactoe_multiplayer.Commands.StartNewGameResponse;
import martin.tictactoe_multiplayer.Game;

public class StartNewGameResponseHandler extends BaseCommandHandler {

	@Inject
	private Game game;

	@Override
	public void handleCommand(Object objCmd) {
		StartNewGameResponse cmd = (StartNewGameResponse)objCmd;

		game.receiveNewGameResponseFromOtherPlayer(cmd.getLetsStart());
	}
}