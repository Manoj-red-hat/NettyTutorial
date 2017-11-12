package netty;

import io.netty.channel.ChannelInitializer;

public class ServerChannelInitializer extends ChannelInitializer<io.netty.channel.socket.SocketChannel> {

    @Override
    protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ServerInboundHandler());
        ch.pipeline().addLast(new ServerOutboundHandler());
    }
}
