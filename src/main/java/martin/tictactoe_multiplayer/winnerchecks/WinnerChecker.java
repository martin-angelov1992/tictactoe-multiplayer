package martin.tictactoe_multiplayer.winnerchecks;

import martin.tictactoe_multiplayer.Position;

public abstract class WinnerChecker {
	public abstract byte getWinnerID(byte[][] squares);

	protected byte getWinnerByPositions(byte[][] squares, Position pos1, 
			Position pos2, Position pos3) {
		byte square1 = squares[pos1.getX()][pos1.getY()];

		if (square1 == 0) {
			return 0;
		}

		byte square2 = squares[pos2.getX()][pos2.getY()];
		byte square3 = squares[pos3.getX()][pos3.getY()];

		if (square1 != square2 || square2 != square3) {
			return 0;
		}

		return square1;
	}
}