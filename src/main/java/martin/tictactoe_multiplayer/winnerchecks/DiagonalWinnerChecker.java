package martin.tictactoe_multiplayer.winnerchecks;

import martin.tictactoe_multiplayer.Position;

public class DiagonalWinnerChecker extends WinnerChecker {

	private static final Position[][] WINNING_POSITIONS = {{Position.getPosition((byte)0, (byte)0),
		Position.getPosition((byte)1, (byte)1), Position.getPosition((byte)2, (byte)2)},
			
			{Position.getPosition((byte)2, (byte)0), Position.getPosition((byte)1, (byte)1), 
			Position.getPosition((byte)0, (byte)2)}};

	@Override
	public byte getWinnerID(byte[][] squares) {
		for (Position[] winningPositions : WINNING_POSITIONS) {
			byte winner = getWinnerByPositions(squares, winningPositions[0], 
					winningPositions[1], winningPositions[2]);
			if (winner != 0) {
				return winner;
			}
		}

		return 0;
	}
}