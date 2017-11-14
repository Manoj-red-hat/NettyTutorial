package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;

/**
 * Created by Manoj Kumar on 11/13/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class MessageToByteEncode extends MessageToByteEncoder{
    private static Logger log = Logger.getLogger(MessageToByteEncode.class.getName());
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        log.debug("MESSAGE TO BYTE ENCODER\n");
        out.writeCharSequence(((String) msg)+"MESSAGE TO BYTE ENCODER\n", Charset.defaultCharset());
    }
}
