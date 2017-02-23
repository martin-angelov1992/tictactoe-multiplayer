package martin.tictactoe_multiplayer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import martin.tictactoe_multiplayer.winnerchecks.DiagonalWinnerChecker;
import martin.tictactoe_multiplayer.winnerchecks.HorizontalWinnerChecker;
import martin.tictactoe_multiplayer.winnerchecks.VerticalWinnerChecker;
import martin.tictactoe_multiplayer.winnerchecks.WinnerChecker;

public class Board {
	private byte[][] squares;
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

	public Board() {
		players = new LinkedList<>();
		squares = new byte[BOARD_SIZE][BOARD_SIZE];
		addDefaultPlayers();
	}

	public Player getPlayerTurn() {
		return playerTurn;
	}

	private void addDefaultPlayers() {
		Player me = Player.newPlayer("me", (byte)1);
		Player otherPlayer = Player.newBot("other player", (byte)2);
		players.add(me);
		players.add(otherPlayer);
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

	public void startNewGame(boolean otherPlayerFirst) {
		instance = new Board();
		instance.gameStarted = true;
		instance.playerTurn = otherPlayerFirst ? instance.getOtherPlayer() : instance.getPlayer();
	}

	public void stopGame() {
		gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void makeMove(Position move) {
		squares[move.getX()][move.getY()] = playerTurn.getID();
		playerTurn = getOtherPlayer(playerTurn);
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