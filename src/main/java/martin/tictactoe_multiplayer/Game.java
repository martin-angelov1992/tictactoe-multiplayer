package martin.tictactoe_multiplayer;

import javax.inject.Inject;

import martin.tictactoe_multiplayer.Commands.StartNewGame;
import martin.tictactoe_multiplayer.communication.Communication;

public class Game {
	@Inject
	private Board board;

	@Inject
	private View view;

	@Inject
	private Communication communication;

	private Timer timer;

	private StartNewGame pendingRequest;

	public Game() {
	}

	public void makeMove(Position pos) {
		board.tryMakeMove(board.getPlayerTurn(), pos);
		view.makeMove(pos, board.getPlayerTurn());

		Player winner = board.getWinner();

		if (winner != null) {
			view.gotWinner(winner.getName());
		}
	}

	public void notifyTimesUp() {
		timer.stop();
		timer = null;

		Player playerTurn = board.getPlayerTurn();
		Player me = board.getPlayer();
		Player otherPlayer = board.getOtherPlayer();

		Player winner = playerTurn.equals(me) ? otherPlayer : me;

		view.gotWinner(winner.getName());
	}

	public void startNewGame(boolean imFirst) {
		if (timer != null) {
			timer.stop();
		}

		timer = new Timer(this);
		timer.run();
		view.startNewGame(imFirst);
	}

	public void notifyTimerTick(int timeLeft) {
		view.setTimeLeft(timeLeft, board.getPlayerTurn().equals(board.getOtherPlayer()));

		if (timeLeft == 0) {
			view.notifyTimeOver(!isMyTurn());

			Player winner = board.getPlayerTurn().equals(board.getPlayer()) ? board.getOtherPlayer() : board.getPlayer();
			view.gotWinner(winner.getName());
			timer.stop();
		}
	}

	public void connect(String host, Integer port) {
		boolean success = communication.connect(host, port);

		String hostInfo = host;

		if (port != null) {
			hostInfo += ":" + port;
		}

		if (success) {
			view.setConnected(hostInfo);
		} else {
			view.notifyConnectionFailed(hostInfo);
		}
	}

	public void notifyConnectionMade(String host) {
		view.setConnected(host);
	}

	public boolean isMyTurn() {
		return board.getPlayer().equals(board.getPlayerTurn());
	}

	public void receiveNewGameRequestFromOtherPlayer(StartNewGame request) {
		pendingRequest = request;
		view.receiveStartNewGameRequestFromOtherPlayer(!request.getImFirst());
	}

	public void receiveNewGameRequestFromUI(boolean imFirst) {
		communication.sendStartNewGame(imFirst);
		pendingRequest = StartNewGame.newBuilder().setImFirst(imFirst).build();
	}

	public void receiveNewGameResponseFromUI(boolean agree) {
		if (agree) {
			startNewGame(!pendingRequest.getImFirst());
		}

		pendingRequest = null;
	}

	public void receiveNewGameResponseFromOtherPlayer(boolean agree) {
		if (agree) {
			startNewGame(!pendingRequest.getImFirst());
		}

		pendingRequest = null;
	}

	public void disconnect() {
		view.setDisconnected();
		communication.disconnect();
	}

	public void run() {
		int port = view.retrievePort();
		communication.awaitConnection(port);
		view.show();
	}
}