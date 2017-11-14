package mk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@ChannelHandler.Sharable
public class InboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
         ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is active "+ctx.toString());
        Thread t = new Thread(()->
        {
            while (true) {
                try {
                    System.out.print("Enter Data: ");
                    BufferedReader inr = new BufferedReader(new InputStreamReader(System.in));
                    ChannelFuture channelFuture=ctx.channel().write(Unpooled.wrappedBuffer((inr.readLine()+"\n").getBytes())).sync();
                    if(!channelFuture.isSuccess()){
                        System.out.print("Success");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received :"+in.toString(CharsetUtil.UTF_8));
        ctx.channel().write(in);
    }
}
