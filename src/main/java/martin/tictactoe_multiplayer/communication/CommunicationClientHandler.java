package martin.tictactoe_multiplayer.communication;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import martin.tictactoe_multiplayer.Commands.BaseCommand;


public class CommunicationClientHandler extends SimpleChannelInboundHandler<BaseCommand> { 
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, BaseCommand msg)
      throws Exception {
	  switch (msg.getType()) {
		case MOVE:
			//msg.getExtension(extension);
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
}