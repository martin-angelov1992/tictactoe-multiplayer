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
		communication.awaitConnection(Communication.DEFAULT_PORT);
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
		
	}

	public void startNewGame(boolean imFirst) {
		timer = new Timer(this);
		timer.run();
		view.startNewGame();
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

	public void connect(String host, int port) {
		communication.connect(host, port);
		view.setConnected(host + ":" + port);
	}

	public boolean isMyTurn() {
		return board.getPlayer().equals(board.getPlayerTurn());
	}

	public void requestNewGame(StartNewGame request) {
		pendingRequest = request;
		view.proposeNewGame(!request.getImFirst());
	}

	public void receiveNewGameResponse(boolean agree) {
		if (agree) {
			startNewGame(!pendingRequest.getImFirst());
		}

		pendingRequest = null;
	}
}