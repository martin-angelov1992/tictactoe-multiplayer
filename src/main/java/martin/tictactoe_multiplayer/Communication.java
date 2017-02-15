package martin.tictactoe_multiplayer;

import martin.tictactoe_multiplayer.Commands.BaseCommand;
import martin.tictactoe_multiplayer.Commands.Move;
import martin.tictactoe_multiplayer.Commands.StartNewGame;
import martin.tictactoe_multiplayer.Commands.StartNewGameResponse;
import martin.tictactoe_multiplayer.Commands.TimesUp;

public class Communication {
	public void connect() {
		
	}

	public void sendTimesUp() {
		TimesUp message = TimesUp.newBuilder().build();
	}

	public void sendStartNewGame(boolean imFirst) {
		StartNewGame message = StartNewGame.newBuilder().setImFirst(imFirst).build();
	}

	public void sendMove(byte x, byte y) {
		Move message = Move.newBuilder().setX(x).setY(y).build();
	}

	public void sendStartNewGameResponse(boolean agree) {
		StartNewGameResponse message = StartNewGameResponse.newBuilder().setLetsStart(agree).build();
	}

	private void sendMessage(BaseCommand.CommandType type) {
		BaseCommand cmd = BaseCommand.newBuilder().setType(type).setExtension(extension, cmd).build();
	}
}