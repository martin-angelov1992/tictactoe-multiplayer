package martin.tictactoe_multiplayer;

import org.junit.Test;

import junit.framework.TestCase;
import martin.tictactoe_multiplayer.communication.Communication;
import martin.tictactoe_multiplayer.communication.CommunicationHandler;

public class TestConnection extends TestCase {

	@Test
	public void test() throws InterruptedException {
		Communication con = new Communication();
		CommunicationHandler.communication = con;
		Runnable r = () -> {
			con.awaitConnection(4000);
		};
		new Thread(r).start();
		Thread.sleep(1000);
		con.connect("localhost", 4000);
		Thread.sleep(1000);
		con.sendMove((byte)1, (byte)1);
		Thread.sleep(1000);
	}
}
