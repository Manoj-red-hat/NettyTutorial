package netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * Created by Manoj Kumar on 11/13/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class MessageToMessageDecoder extends io.netty.handler.codec.MessageToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        out.add(String.valueOf(msg)+"Message to Message Decoder");
    }
}
