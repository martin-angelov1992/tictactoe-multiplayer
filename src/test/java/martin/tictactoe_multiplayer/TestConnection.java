package martin.tictactoe_multiplayer;

import org.junit.Test;

import junit.framework.TestCase;
import martin.tictactoe_multiplayer.communication.Communication;

public class TestConnection extends TestCase {

	@Test
	public void test() throws InterruptedException {
		Communication client = new Communication();
		Communication server = new Communication();
		Runnable r = () -> {
			server.awaitConnection(4000);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		new Thread(r).start();
		client.connect("localhost", 4000);
		client.sendMove((byte)1, (byte)1);
		Thread.sleep(5000);
	}
}
