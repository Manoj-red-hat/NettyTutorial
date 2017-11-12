package netty;

import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ClientChannelInitializer extends ChannelInitializer<io.netty.channel.socket.SocketChannel> {

    @Override
    protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
        ch.pipeline().addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        ch.pipeline().addLast(new ClientInbountHandler());
        ch.pipeline().addLast(new ClientOutboundHandler());

    }
}
