package martin.tictactoe_multiplayer.winnerchecks;

import martin.tictactoe_multiplayer.Position;

public class HorizontalWinnerChecker extends WinnerChecker {

	@Override
	public byte getWinnerID(byte[][] squares) {
		for (byte i=0; i<squares.length; ++i) {
			byte winner = getWinnerByPositions(squares, Position.getPosition((byte)0, i), 
					Position.getPosition((byte)1, i), Position.getPosition((byte)2, i));
			if (winner != 0) {
				return winner;
			}
		}

		return 0;
	}
}