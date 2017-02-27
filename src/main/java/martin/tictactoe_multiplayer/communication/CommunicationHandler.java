package martin.tictactoe_multiplayer.communication;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.GeneratedMessage.GeneratedExtension;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import martin.tictactoe_multiplayer.Commands;
import martin.tictactoe_multiplayer.Commands.BaseCommand;
import martin.tictactoe_multiplayer.Commands.BaseCommand.CommandType;
import martin.tictactoe_multiplayer.Commands.Move;
import martin.tictactoe_multiplayer.communication.handlers.BaseCommandHandler;
import martin.tictactoe_multiplayer.communication.handlers.MoveHandler;
import martin.tictactoe_multiplayer.communication.handlers.StartGameRequestHandler;
import martin.tictactoe_multiplayer.communication.handlers.StartNewGameResponseHandler;

public class CommunicationHandler extends SimpleChannelInboundHandler<BaseCommand> {

	@Inject
	private Communication communication;

	private Map<CommandType, HandleInfo> handlersMap;

	public CommunicationHandler() {
		handlersMap = new HashMap<>();
		handlersMap.put(CommandType.MOVE, new HandleInfo(new MoveHandler(), Commands.Move.cmd));
		handlersMap.put(CommandType.START_GAME_REQUEST, new HandleInfo(new StartGameRequestHandler(), Commands.StartNewGame.cmd));
		handlersMap.put(CommandType.START_NEW_GAME_RESPONSE, new HandleInfo(new StartNewGameResponseHandler(), Commands.StartNewGameResponse.cmd));
	}

	public CommunicationHandler(Communication communication) {
		this.communication = communication;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseCommand msg) throws Exception {
		System.out.println("Message received");
		HandleInfo info = handlersMap.get(msg.getType());
		info.getHandler().handleCommand(msg.getExtension(info.getCmd()));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Move move = ((BaseCommand)msg).getExtension(Commands.Move.cmd);
		System.out.println(move.getX() + " " + move.getY());
		super.channelRead(ctx, msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		communication.setChannel(ctx.channel());
		System.out.println("Channel Active");
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