import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */

public class PlainEchoServerNIO2  {

    AsynchronousServerSocketChannel serverSocketChannel;
    InetSocketAddress inetSocketAddress;
    final CountDownLatch countDownLatch  = new CountDownLatch(1);

    class onWriteHandler implements CompletionHandler<Integer,ByteBuffer>{
        private final AsynchronousSocketChannel channel;
        onWriteHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }
        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            if (attachment.hasRemaining()){
                channel.write(attachment,attachment,this);
            }else{
                attachment.compact();
                channel.read(attachment,attachment,new onReadHandler(channel));
            }

        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                channel.close();
            } catch (IOException e) {
                 // ingnore on close
            }
        }
    }

    class onReadHandler implements CompletionHandler<Integer,ByteBuffer>{
        private final AsynchronousSocketChannel channel;
        onReadHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            channel.write(attachment,attachment,new onWriteHandler(channel));
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

        }
    }

    class onAcceptHandler implements CompletionHandler<AsynchronousSocketChannel,Object > {

        @Override
        public void completed(final AsynchronousSocketChannel result, Object attachment) {
            serverSocketChannel.accept(null,this);
            ByteBuffer buffer=ByteBuffer.allocate(100);
            result.read(buffer,buffer,new onReadHandler(result));
            System.out.println("Accepted Connection from"+result);
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void serve(int port) throws IOException{
        System.out.println("Listenig on : "+port);
        serverSocketChannel =AsynchronousServerSocketChannel.open();
        inetSocketAddress = new InetSocketAddress(port);
        serverSocketChannel.bind(inetSocketAddress);
        serverSocketChannel.accept(null, new onAcceptHandler());
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
