import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */

public class NettyClient {

    class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {

            System.out.println(ctx.channel());
            for(int i=0;i<4;i++){
                ByteBuf heapBuff=Unpooled.buffer();
                try  {
                    BufferedReader inr = new BufferedReader(new InputStreamReader(System.in));
                    heapBuff.writeCharSequence(inr.readLine(), Charset.defaultCharset());
                    ctx.writeAndFlush(heapBuff);
                    ctx.writeAndFlush("\r\n");
                }catch (IOException e){}

            }
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                     System.out.println("Client received: " +msg);
        }



        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
        {
            cause.printStackTrace();
            ctx.close();
        }
    }


    @ChannelHandler.Sharable
    class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            socketChannel.pipeline().addLast("decoder", new StringDecoder());
            socketChannel.pipeline().addLast("encoder", new StringEncoder());
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
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public static void main(String args[]) throws Exception {
        new NettyClient("127.0.0.1",12312).start();
    }
}
