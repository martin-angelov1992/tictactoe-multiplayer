package martin.tictactoe_multiplayer;

public class Player {
	private Symbol symbol;
	private String name;
	private final byte ID;
	private boolean isMe;

	public enum Symbol {
		X("X"), O("O");

		private String file;

		private Symbol(String file) {
			this.file = file;
		}

		public String getFIle() {
			return file;
		}
	}

	private Player(String name, byte ID, boolean me, Symbol symbol) {
		this.name = name;
		this.ID = ID;
		this.isMe = me;
		this.symbol = symbol;
	}

	public byte getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public boolean isMe() {
		return isMe;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public static Player newBot(String name, byte ID) {
		return new Player(name, ID, true, Symbol.O);
	}

	public static Player newPlayer(String name, byte ID) {
		return new Player(name, ID, false, Symbol.X);
	}
}