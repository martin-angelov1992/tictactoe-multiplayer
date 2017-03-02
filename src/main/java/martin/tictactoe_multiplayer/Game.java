package martin.tictactoe_multiplayer;

import javax.inject.Inject;

import martin.tictactoe_multiplayer.Commands.StartNewGame;
import martin.tictactoe_multiplayer.Player.Symbol;
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

	private void makeMoveRequestCommon(Position pos) {
		Player playerTurn = board.getPlayerTurn();
		if (!board.tryMakeMove(playerTurn, pos)) {
			System.out.println("Unable to make move.");
			return;
		}

		view.makeMove(pos, playerTurn);

		Player winner = board.getWinner();

		if (winner == null) {
			timer.reset();
			if (board.getPlayerTurn().isMe()) {
				view.setMyTurn();
			} else {
				view.setOtherPlayerTurn();
			}
		} else {
			timer.stop();
			view.gotWinner(winner);
			return;
		}

		if (!board.hasMoreMoves()) {
			timer.stop();
			view.notifyDraw();
		}
	}

	public void makeMoveRequestFromUI(Position pos) {
		makeMoveRequestCommon(pos);

		communication.sendMove(pos.getX(), pos.getY());
	}

	public void makeMoveRequestFromOtherPlayer(Position pos) {
		makeMoveRequestCommon(pos);
	}

	public void notifyTimesUp() {
		timer.stop();
		timer = null;

		Player playerTurn = board.getPlayerTurn();
		Player me = board.getPlayer();
		Player otherPlayer = board.getOtherPlayer();

		Player winner = playerTurn.equals(me) ? otherPlayer : me;

		view.gotWinner(winner);
	}

	public void startNewGame(boolean imFirst) {
		System.out.println("Starting new game." + (imFirst ? " I'm first." : ""));

		if (timer != null) {
			timer.stop();
		}

		board.startNewGame(!imFirst);

		Player firstPlayer = imFirst ? board.getPlayer() : board.getOtherPlayer();
		Player secondPlayer = imFirst ? board.getOtherPlayer() : board.getPlayer();

		firstPlayer.setSymbol(Symbol.X);
		secondPlayer.setSymbol(Symbol.O);

		timer = new Timer(this);
		timer.run();
		view.startNewGame(board.getPlayer(), imFirst);
		view.setTimeLeft(timer.getTimeLeft(), !isMyTurn());
	}

	public void notifyTimerTick(int timeLeft) {
		view.setTimeLeft(timeLeft, board.getPlayerTurn().equals(board.getOtherPlayer()));

		if (timeLeft == 0) {
			view.notifyTimeOver(!isMyTurn());

			Player winner = board.getPlayerTurn().equals(board.getPlayer()) ? board.getOtherPlayer() : board.getPlayer();
			view.gotWinner(winner);
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
		if (board.isGameOver()) {
			return false;
		}
		
		return board.getPlayer().equals(board.getPlayerTurn());
	}

	public void receiveNewGameRequestFromOtherPlayer(StartNewGame request) {
		System.out.println("Setting pending request."+(!request.getImFirst() ? " I'm first." : ""));
		pendingRequest = StartNewGame.newBuilder().setImFirst(!request.getImFirst()).build();
		view.receiveStartNewGameRequestFromOtherPlayer(!request.getImFirst());
	}

	public void receiveNewGameRequestFromUI(boolean imFirst) {
		communication.sendStartNewGame(imFirst);
		pendingRequest = StartNewGame.newBuilder().setImFirst(imFirst).build();
	}

	public void receiveNewGameResponseFromUI(boolean agree) {
		if (agree) {
			startNewGame(pendingRequest.getImFirst());
		}

		pendingRequest = null;
		communication.sendStartNewGameResponse(agree);
	}

	public void receiveNewGameResponseFromOtherPlayer(boolean agree) {
		if (agree) {
			startNewGame(pendingRequest.getImFirst());
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

	public void notifyDisconnected() {
		view.notifyDisconnected();

		if (timer != null) {
			timer.stop();
		}

		stopGame();
	}

	private void stopGame() {
		board.stopGame();
	}
}