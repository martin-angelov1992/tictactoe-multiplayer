package martin.tictactoe_multiplayer.communication;

import com.google.protobuf.GeneratedMessage.GeneratedExtension;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import martin.tictactoe_multiplayer.Commands;
import martin.tictactoe_multiplayer.Commands.BaseCommand;
import martin.tictactoe_multiplayer.Commands.Move;
import martin.tictactoe_multiplayer.Commands.StartNewGame;
import martin.tictactoe_multiplayer.Commands.StartNewGameResponse;
import martin.tictactoe_multiplayer.Commands.TimesUp;

public class Communication {
	public void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();

	    try {
	        Bootstrap bootstrap = new Bootstrap();
	        bootstrap.group(group)
	                 .channel(NioSocketChannel.class)
	                 .handler(new CommunicationInitializer());
	        
	        // Create connection 
	        bootstrap.connect(host, port);
	      } finally {
	        group.shutdownGracefully();
	      }
	}

	public void sendTimesUp() {
		TimesUp message = TimesUp.newBuilder().build();
		sendMessage(BaseCommand.CommandType.TIMES_UP, Commands.TimesUp.cmd, message);
	}

	public void sendStartNewGame(boolean imFirst) {
		StartNewGame message = StartNewGame.newBuilder().setImFirst(imFirst).build();
		sendMessage(BaseCommand.CommandType.START_GAME_REQUEST, Commands.StartNewGame.cmd, message);
	}

	public void sendMove(byte x, byte y) {
		Move message = Move.newBuilder().setX(x).setY(y).build();
		sendMessage(BaseCommand.CommandType.MOVE, Commands.Move.cmd, message);
	}

	public void sendStartNewGameResponse(boolean agree) {
		StartNewGameResponse message = StartNewGameResponse.newBuilder().setLetsStart(agree).build();
		sendMessage(BaseCommand.CommandType.START_NEW_GAME_RESPONSE, Commands.StartNewGameResponse.cmd, message);
	}

	private <Type> void sendMessage(BaseCommand.CommandType type, GeneratedExtension<BaseCommand, Type> extension, Type cmd) {
		BaseCommand wrapper = BaseCommand.newBuilder().setType(type).setExtension(extension, cmd).build();
	}
}