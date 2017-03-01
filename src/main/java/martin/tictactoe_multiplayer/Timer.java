package martin.tictactoe_multiplayer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {

	private ScheduledExecutorService executor;
	private Game game;
	private volatile Integer timeLeft;

	public Timer(Game game) {
		this.game = game;
	}

	public void run() {
		timeLeft = Board.MOVE_SECONDS;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable decreaser = new Decreaser();
		executor.scheduleAtFixedRate(decreaser, 1, 1, TimeUnit.SECONDS);
		this.executor = executor;
	}

	public void stop() {
		executor.shutdownNow();
	}

	public void reset() {
		stop();
		run();
	}

	private class Decreaser implements Runnable {

		@Override
		public void run() {
			synchronized(timeLeft) {
				if (Thread.interrupted()) {
					return;
				}

				--timeLeft;
			}

			try {
				game.notifyTimerTick(timeLeft);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getTimeLeft() {
		return timeLeft;
	}
}