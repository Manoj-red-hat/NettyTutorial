import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */

public class EchoServerNetty {

    class EchoServerHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx,Object obj){
            System.out.println("Server recieved msg " + obj);
            ctx.writeAndFlush(obj);
            ctx.writeAndFlush("\r\n");
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
           ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
                    //.addListener(ChannelFutureListener.CLOSE);
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    class CustomChannelInitializer extends ChannelInitializer<SocketChannel>{
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            socketChannel.pipeline().addLast("decoder", new StringDecoder());
            socketChannel.pipeline().addLast("encoder", new StringEncoder());
            socketChannel.pipeline().addLast(new EchoServerHandler());
        }
    }

    private final int port;

    public EchoServerNetty( int port){
        this.port=port;
    }

    public void start()  {
        EventLoopGroup group= new NioEventLoopGroup();
        ServerBootstrap sb=new ServerBootstrap();
        sb.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new CustomChannelInitializer());
        ChannelFuture f;
        try {
            f = sb.bind().sync();
            System.out.println(EchoServerNetty.class.getName()+"Started and listening on"+f.channel().localAddress());
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }
}
