package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;



import java.net.InetSocketAddress;

public class Client {
    private final int port;
    private final int loop;
    Bootstrap bootstrap;
    NioEventLoopGroup nioEventLoopGroup;


    public Client(int port,int loop){
        this.port=port;
        this.loop=loop;
        bootstrap = new Bootstrap();
        nioEventLoopGroup  = new NioEventLoopGroup();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("127.0.0.1",this.port))
                .handler(new ClientChannelInitializer());
        ChannelFuture channelFuture;
        try {
            channelFuture= bootstrap.connect().sync();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if(channelFuture.isSuccess()){
                    System.out.println("Success");
                }
            });
        }catch (InterruptedException e){
            nioEventLoopGroup.shutdownGracefully();
            e.printStackTrace();
        }



    }

}
