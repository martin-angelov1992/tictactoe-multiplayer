package martin.tictactoe_multiplayer.communication;

import javax.inject.Inject;

import com.google.protobuf.GeneratedMessage.GeneratedExtension;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
import martin.tictactoe_multiplayer.Game;

public class Communication {
	private boolean isHost;

	@Inject
	private CommunicationInitializer communicationInitializer;

	@Inject
	private Game game;

	private volatile Channel channel;

	void setChannel(Channel channel) {
		channel.closeFuture().addListener(new ChannelFutureListener() {
	        @Override
	        public void operationComplete(ChannelFuture future) throws Exception {
	           game.notifyDisconnected();
	           isHost = true;
	        }
	    });
		this.channel = channel;
	}

	public void awaitConnection(int port) {
		EventLoopGroup serverGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap bootStrap = new ServerBootstrap();
		bootStrap.group(serverGroup, workerGroup).channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO)).childHandler(communicationInitializer);

		// Bind to port
		try {
			isHost = true;
			bootStrap.bind(port).sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).handler(communicationInitializer);

		ChannelFuture future = bootstrap.connect(host, port);
		isHost = false;

		future.awaitUninterruptibly();
		if (!future.isSuccess()) {
			// it is still awaiting connection
			isHost = true;
			return false;
		}

		// Create connection
		channel = future.channel();
		return true;
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
		System.out.println("Receiving "+type);
		BaseCommand wrapper = BaseCommand.newBuilder().setType(type).setExtension(extension, cmd).build();
		try {
			channel.writeAndFlush(wrapper).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		channel.disconnect();
		channel.close();
	}

	public boolean isHost() {
		return isHost;
	}
}