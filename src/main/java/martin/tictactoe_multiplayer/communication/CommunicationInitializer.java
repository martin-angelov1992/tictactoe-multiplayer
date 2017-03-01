package martin.tictactoe_multiplayer.communication;

import javax.inject.Inject;

import com.google.protobuf.ExtensionRegistry;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import martin.tictactoe_multiplayer.Commands;

public class CommunicationInitializer extends ChannelInitializer<SocketChannel> {

	@Inject
	private CommunicationHandler connectionHandler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();

		ExtensionRegistry registry = ExtensionRegistry.newInstance();
		Commands.registerAllExtensions(registry);

		p.addLast(new ProtobufVarint32FrameDecoder());
		p.addLast(new ProtobufDecoder(Commands.BaseCommand.getDefaultInstance(), registry));

		p.addLast(new ProtobufVarint32LengthFieldPrepender());
		p.addLast(new ProtobufEncoder());

		p.addLast(connectionHandler);
	}
}