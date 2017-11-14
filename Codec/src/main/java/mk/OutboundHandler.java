package mk;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

public class OutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf in=(ByteBuf)msg;
        System.out.println("Outbound handler: "+in.toString(CharsetUtil.UTF_8));
        ChannelFuture channelFuture=ctx.writeAndFlush(in,promise).sync();
        if(!channelFuture.isSuccess()){
            System.out.print("Success");
        }
    }
}
