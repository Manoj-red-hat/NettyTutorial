package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    private final int port;
    private final String host;

    public Server(String host,int port) throws InterruptedException {
        this.port=port;
        this.host=host;
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        serverBootstrap.group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer())
                .bind(host,port)
                .sync().addListener((ChannelFutureListener) future -> {
            if(future.isSuccess()){
                System.out.println("Success");
            }else{
                System.out.println(future.cause());
                nioEventLoopGroup.shutdownGracefully();
            }
        });
    }

}
