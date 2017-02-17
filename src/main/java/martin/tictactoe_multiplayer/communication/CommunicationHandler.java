package martin.tictactoe_multiplayer.communication;

import javax.inject.Inject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import martin.tictactoe_multiplayer.Commands.BaseCommand;

public class CommunicationHandler extends SimpleChannelInboundHandler<BaseCommand> {

	@Inject
	public static Communication communication;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseCommand msg) throws Exception {
		System.out.println("Message received");
		switch (msg.getType()) {
		case MOVE:
			// msg.getExtension(extension);
			break;
		case START_GAME_REQUEST:
			break;
		case START_NEW_GAME_RESPONSE:
			break;
		case TIMES_UP:
			break;
		default:
			break;
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		communication.setChannel(ctx.channel());
	}
}