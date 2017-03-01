package martin.tictactoe_multiplayer.communication;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandler.Sharable;
import javax.inject.Inject;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.GeneratedMessage.GeneratedExtension;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import martin.tictactoe_multiplayer.Commands;
import martin.tictactoe_multiplayer.Commands.BaseCommand;
import martin.tictactoe_multiplayer.Commands.BaseCommand.CommandType;
import martin.tictactoe_multiplayer.Game;
import martin.tictactoe_multiplayer.communication.handlers.BaseCommandHandler;
import martin.tictactoe_multiplayer.communication.handlers.MoveHandler;
import martin.tictactoe_multiplayer.communication.handlers.StartGameRequestHandler;
import martin.tictactoe_multiplayer.communication.handlers.StartNewGameResponseHandler;

@Sharable
public class CommunicationHandler extends SimpleChannelInboundHandler<BaseCommand> {

	@Inject
	private Communication communication;

	@Inject
	private Game game;

	@Inject
	private MoveHandler moveHandler;

	@Inject
	private StartGameRequestHandler startGameRequestHandler;

	@Inject
	private StartNewGameResponseHandler startNewGameResponseHandler;

	private Map<CommandType, HandleInfo> handlersMap;

	public CommunicationHandler() {
		handlersMap = new HashMap<>();
	}

	@Inject
	public void init() {
		handlersMap.put(CommandType.MOVE, new HandleInfo(moveHandler, Commands.Move.cmd));
		handlersMap.put(CommandType.START_GAME_REQUEST, new HandleInfo(startGameRequestHandler, Commands.StartNewGame.cmd));
		handlersMap.put(CommandType.START_NEW_GAME_RESPONSE, new HandleInfo(startNewGameResponseHandler, Commands.StartNewGameResponse.cmd));
	}

	public CommunicationHandler(Communication communication) {
		this.communication = communication;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseCommand msg) throws Exception {
		System.out.println("Handling "+msg.getType());
		HandleInfo info = handlersMap.get(msg.getType());
		info.getHandler().handleCommand(msg.getExtension(info.getCmd()));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		System.out.println("Disconnecting");
		communication.disconnect();
		game.notifyDisconnected();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NioSocketChannel channel = (NioSocketChannel)ctx.channel();
		InetSocketAddress address = channel.localAddress();
		communication.setChannel(channel);
		if (communication.isHost()) {
			game.notifyConnectionMade(address.getHostName());
		}
	}

	private class HandleInfo {
		private final BaseCommandHandler handler;
		private final GeneratedExtension<BaseCommand, ? extends AbstractMessage> cmd;

		public HandleInfo(BaseCommandHandler handler, GeneratedExtension<BaseCommand, ? extends AbstractMessage> cmd) {
			super();
			this.handler = handler;
			this.cmd = cmd;
		}

		public BaseCommandHandler getHandler() {
			return handler;
		}

		public GeneratedExtension<BaseCommand, ? extends AbstractMessage> getCmd() {
			return cmd;
		}
	}
}