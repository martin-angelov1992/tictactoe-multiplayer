package martin.tictactoe_multiplayer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import martin.tictactoe_multiplayer.winnerchecks.DiagonalWinnerChecker;
import martin.tictactoe_multiplayer.winnerchecks.HorizontalWinnerChecker;
import martin.tictactoe_multiplayer.winnerchecks.VerticalWinnerChecker;
import martin.tictactoe_multiplayer.winnerchecks.WinnerChecker;

public class Board {
	private byte[][] squares;
	private Integer value;
	private List<Player> players;
	private Player playerTurn;
	private boolean gameStarted = false;
	private boolean gameOver = false;
	private static Set<WinnerChecker> checkers;
	private static Board instance;

	static {
		initCheckersSet();
	}

	public static final int MOVE_SECONDS = 4;
	public static final int BOARD_SIZE = 3;

	private Board() {
		players = new LinkedList<>();
		squares = new byte[BOARD_SIZE][BOARD_SIZE];
		addDefaultPlayers();
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Player getPlayerTurn() {
		return playerTurn;
	}

	private void addDefaultPlayers() {
		Player me = Player.newPlayer("me", (byte)1);
		Player bot = Player.newBot("bot", (byte)2);
		players.add(me);
		players.add(bot);
	}

	private static void initCheckersSet() {
		checkers = new HashSet<>();
		checkers.add(new DiagonalWinnerChecker());
		checkers.add(new HorizontalWinnerChecker());
		checkers.add(new VerticalWinnerChecker());
	}

	public boolean tryMakeMove(Player player, Position pos) {
		if (!validMove(player, pos)) {
			return false;
		}

		squares[pos.getX()][pos.getY()] = player.getID();
		Player otherPlayer = getOtherPlayer(player);
		playerTurn = otherPlayer;

		return true;
	}

	public boolean validMove(Player player, Position pos) {
		if (gameOver || !gameStarted) {
			return false;
		}

		if (player.getID() != playerTurn.getID()) {
			return false;
		}

		if (squares[pos.getX()][pos.getY()] != 0) {
			return false;
		}

		return true;
	}

	public Player getWinner() {
		return getWinner(squares);
	}

	private Player getWinner(byte[][] squares) {
		for (WinnerChecker checker : checkers) {
			byte playerID = checker.getWinnerID(squares);
			if (playerID != 0) {
				return getPlayerByID(playerID);
			}
		}

		return null;		
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public Player getPlayerByID(int ID) {
		for (Player player : players) {
			if (player.getID() == ID) {
				return player;
			}
		}

		return null;
	}

	public Player getPlayer() {
		for (Player player : players) {
			if (!player.isMe()) {
				return player;
			}
		}

		return null;
	}

	public Player getOtherPlayer() {
		for (Player player : players) {
			if (player.isMe()) {
				return player;
			}
		}

		return null;
	}

	public Player getOtherPlayer(Player player) {
		for (Player otherPlayer : players) {
			if (otherPlayer.getID() != player.getID()) {
				return otherPlayer;
			}
		}

		return null;
	}

	public void reset() {
		for (int i = 0; i < squares.length; ++i) {
			for (int j = 0; j < squares[i].length; ++j) {
				squares[i][j] = 0;
			}
		}
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public static void startNewGame(boolean otherPlayerFirst) {
		instance = new Board();
		instance.gameStarted = true;
		instance.playerTurn = otherPlayerFirst ? instance.getOtherPlayer() : instance.getPlayer();
	}

	public static Board getInstance() {
		if (instance == null) {
			synchronized (Board.class) {
				if (instance == null) {
					instance = new Board();
				}
			}
		}

		return instance;
	}

	public void stopGame() {
		gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	private class MoveIterator implements Iterator<Position> {

		private byte x = 1;
		private byte y = 1;

		@Override
		public boolean hasNext() {
			// This call will mutate the iterator but still no blocks will be
			// skipped
			Position nextBlock = getNext();
			return nextBlock != null;
		}

		@Override
		public Position next() {
			Position nextBlock = getNext();
			mutate();
			return nextBlock;
		}

		private void mutate() {
			if (y == BOARD_SIZE-1) {
				++x;
				y = 0;
			} else {
				++y;
			}
		}

		private Position getNext() {
			for (; x < BOARD_SIZE; ++x) {
				for (; y < BOARD_SIZE; ++y) {
					if (squares[x][y] == 0) {
						return Position.getPosition(x, y);
					}
				}
			}

			return null;
		}
	}

	public Iterator<Position> movementIterator() {
		return new MoveIterator();
	}

	public Board clone() {
		Board board = new Board();

		board.players = this.players;
		board.squares = cloneSquares(this.squares);
		board.playerTurn = this.playerTurn;
		board.value = this.value;
		board.gameStarted = this.gameStarted;

		return board;
	}

	public List<Position> getAllActions() {
		List<Position> moves = new LinkedList<>();

		for (int i=0;i<BOARD_SIZE;++i) {
			for (int j=0;j<BOARD_SIZE;++j) {
				if (squares[i][j] == 0) {
					moves.add(Position.getPosition((byte)i, (byte)j));
				}
			}
		}

		return moves;
	}

	public Board with(Position successor) {
		Board newBoard = new Board();

		newBoard.playerTurn = getOtherPlayer(playerTurn);
		newBoard.squares = cloneSquares(squares);
		newBoard.squares[successor.getX()][successor.getY()] = newBoard.playerTurn.getID();
		newBoard.players = players;
		newBoard.value = value;
		newBoard.gameStarted = gameStarted;

		return newBoard;
	}

	private static byte[][] cloneSquares(byte[][] squares) {
		byte[][] newSquares = new byte[squares.length][squares.length];

		for (int i=0;i<squares.length;++i) {
			for (int j=0;j<squares[i].length;++j) {
				newSquares[i][j] = squares[i][j];
			}
		}

		return newSquares;
	}

	public boolean hasMoreMoves() {
		for (int i=0;i<squares.length;++i) {
			for (int j=0;j<squares[i].length;++j) {
				if (squares[i][j] == 0) {
					return true;
				}
			}
		}

		return false;
	}
}