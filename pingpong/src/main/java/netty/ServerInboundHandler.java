package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ServerInboundHandler implements ChannelInboundHandler {
    private static Logger log = Logger.getLogger(ClientInbountHandler.class.getName());

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel Registered : "+ctx.toString());

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel UnRegistered : "+ctx.toString());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel Active : "+ctx.toString());
//        new Thread(()->
//        {
//            try {
//                BufferedReader inr = new BufferedReader(new InputStreamReader(System.in));
//                ctx.channel().writeAndFlush(inr.readLine());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel InActive : "+ctx.toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("Channel read : "+ctx.toString());
        String message=((ByteBuf) msg).readCharSequence(((ByteBuf) msg).readableBytes(), Charset.defaultCharset()).toString();
        if (message.compareToIgnoreCase("done") ==1)
            ctx.channel().closeFuture();
        ctx.channel().writeAndFlush(ctx.channel().localAddress()+" : "+message);
        System.out.print(ctx.channel().remoteAddress().toString()+"-->"+ message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel ReadComplete : "+ctx.toString());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel writability changed : "+ctx.toString());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel handler added : "+ctx.toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel handler removed : "+ctx.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("Channel Active : "+ctx.toString()+cause);
    }
}
