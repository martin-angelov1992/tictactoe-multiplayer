package martin.tictactoe_multiplayer;

public class Position {
	private byte x;
	private byte y;
	private static Position[] positionsCache = new Position[9];

	private Position(byte x, byte y) {
		if (!validCoord(x) || !validCoord(y)) {
			throw new IllegalArgumentException("Invalid arguments.");
		}

		this.x = x;
		this.y = y;
	}

	private boolean validCoord(byte coord) {
		return coord >= 0 && coord < Board.BOARD_SIZE;
	}

	public byte getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public static Position getPosition(byte x, byte y) {
		byte index = (byte) (x+y*3);
		Position existing = positionsCache[index];

		if (existing != null) {
			return existing;
		}

		Position newPos = new Position(x, y);
		positionsCache[index] = newPos;

		return newPos;
	}
}