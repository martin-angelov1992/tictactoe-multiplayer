package martin.tictactoe_multiplayer;

public class Player {
	private Symbol symbol;
	private String name;
	private final byte ID;
	private boolean isMe;

	public enum Symbol {
		X("X"), O("O");

		private String symbol;

		private Symbol(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
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

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public static Player newPlayer(String name, byte ID, boolean isMe) {
		return new Player(name, ID, isMe, null);
	}
}