package martin.tictactoe_multiplayer.winnerchecks;

import martin.tictactoe_multiplayer.Position;

public class VerticalWinnerChecker extends WinnerChecker {

	@Override
	public byte getWinnerID(byte[][] squares) {
		for (byte i=0; i<squares.length; ++i) {
			byte winner = getWinnerByPositions(squares, Position.getPosition(i, (byte)0), 
					Position.getPosition(i, (byte)1), Position.getPosition(i, (byte)2));
			if (winner != 0) {
				return winner;
			}
		}

		return 0;
	}
}