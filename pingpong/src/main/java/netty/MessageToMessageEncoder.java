package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Manoj Kumar on 11/13/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class MessageToMessageEncoder extends io.netty.handler.codec.MessageToMessageEncoder {
    private static Logger log = Logger.getLogger(MessageToMessageEncoder.class.getName());
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        log.debug("Message to Message Encoder\n");
        out.add(((ByteBuf)msg).readCharSequence(((ByteBuf)msg).readableBytes(), Charset.defaultCharset()) +"Message to Message Encoder\n");
    }
}
