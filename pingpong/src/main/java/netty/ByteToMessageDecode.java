package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Manoj Kumar on 11/13/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class ByteToMessageDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()>=4){
            out.add(in.readInt());
        }
    }
}
