package netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.apache.log4j.Logger;

public class ServerOutboundHandler extends ChannelOutboundHandlerAdapter {
    private static Logger log = Logger.getLogger(ClientOutboundHandler.class.getName());

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ChannelFuture cf = ctx.write(Unpooled.wrappedBuffer(msg.toString().getBytes()),promise);
        log.info("Outbound Channel write : "+ctx.toString());
        cf.addListener((ChannelFutureListener) future -> {
            if(cf.isSuccess())
                log.info("Done");
            else
                log.error("Send failed: " + cf.cause());

        });
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("Outbound Channel flush : "+ctx.toString());
        ctx.flush();

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("Outbound Channel handler added : "+ctx.toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("Outbound Channel Registered : "+ctx.toString());
    }

}
