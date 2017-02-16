package martin.tictactoe_multiplayer.communication.handlers;

import martin.tictactoe_multiplayer.Commands.BaseCommand;

public abstract class BaseCommandHandler {
	public abstract void handleCommand(BaseCommand cmd);
}