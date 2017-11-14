package netty;

import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ClientChannelInitializer extends ChannelInitializer<io.netty.channel.socket.SocketChannel> {

    @Override
    protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
        //ch.pipeline().addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        ch.pipeline().addFirst("1",new ClientInbountHandler());
        //ch.pipeline().addAfter("1","2",new MessageToByteEncode());
        //ch.pipeline().addLast(new MessageToMessageEncoder());

        ch.pipeline().addLast(new ClientOutboundHandler());
    }
}
