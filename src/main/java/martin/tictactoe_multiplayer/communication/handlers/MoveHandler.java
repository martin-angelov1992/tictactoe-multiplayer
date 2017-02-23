package martin.tictactoe_multiplayer.communication.handlers;

import javax.inject.Inject;

import martin.tictactoe_multiplayer.Commands.Move;
import martin.tictactoe_multiplayer.Game;
import martin.tictactoe_multiplayer.Position;

public class MoveHandler extends BaseCommandHandler {

	@Inject
	private Game game;

	@Override
	public void handleCommand(Object objCmd) {
		Move cmd = (Move)objCmd;
		Position pos = Position.getPosition((byte)cmd.getX(), (byte)cmd.getY());
		game.makeMove(pos);
	}
}