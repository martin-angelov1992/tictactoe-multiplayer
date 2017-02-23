package martin.tictactoe_multiplayer.communication;

import com.google.protobuf.GeneratedMessage.GeneratedExtension;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import martin.tictactoe_multiplayer.Commands;
import martin.tictactoe_multiplayer.Commands.BaseCommand;
import martin.tictactoe_multiplayer.Commands.Move;
import martin.tictactoe_multiplayer.Commands.StartNewGame;
import martin.tictactoe_multiplayer.Commands.StartNewGameResponse;
import martin.tictactoe_multiplayer.Commands.TimesUp;

public class Communication {
	public static final int DEFAULT_PORT = 4000;

	private Channel channel;

	void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void awaitConnection(int port) {
		EventLoopGroup serverGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap bootStrap = new ServerBootstrap();
		bootStrap.group(serverGroup, workerGroup).channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new CommunicationInitializer(this));

		// Bind to port
		try {
			bootStrap.bind(port).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(new CommunicationInitializer(this));

			// Create connection
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
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

	private <Type> void sendMessage(BaseCommand.CommandType type, GeneratedExtension<BaseCommand, Type> extension,
			Type cmd) {
		BaseCommand wrapper = BaseCommand.newBuilder().setType(type).setExtension(extension, cmd).build();
		System.out.println(wrapper.getExtension(Commands.Move.cmd).getX() + " " + wrapper.getExtension(Commands.Move.cmd).getY());
		try {
			channel.writeAndFlush(wrapper).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}