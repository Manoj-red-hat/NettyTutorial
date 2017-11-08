import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */

public class NettyClient {

    @ChannelHandler.Sharable
    class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf in = (ByteBuf)msg;
            System.out.println("Client received: " + ByteBufUtil
                    .hexDump(in.readBytes(in.readableBytes())));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
        {
            cause.printStackTrace();
            ctx.close();
        }
    }

    class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyClient.ClientHandler());
        }
    }

    private final String host;
    private final int port;

    public NettyClient(String host,int port){
        this.host=host;
        this.port=port;
    }

    public void start() throws Exception{
        EventLoopGroup group= new NioEventLoopGroup();
        Bootstrap b=new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host,port))
                .handler(new NettyClient.CustomChannelInitializer());

        try {
            ChannelFuture f= b.connect().sync();
            System.out.println(NettyClient.class.getName()+"Started and listening on"+f.channel().localAddress());
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }
}
