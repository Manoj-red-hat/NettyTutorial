import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Manoj Kumar on 11/6/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class PlainEchoServerNIO {
    public void serve(int port) throws IOException{
        System.out.println("Listinig on :-"+port);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocket.bind(address);
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            try {
                selector.select();
            }catch (IOException e){
                e.printStackTrace();
                break;
            }

            Set readKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator= readKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();
                iterator.remove();
                try{
                    if (selectionKey.isAcceptable()){
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = serverSocketChannel1.accept();
                        System.out.println("Accepted connection"+client);
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ, ByteBuffer.allocate(100));
                    }
                    if(selectionKey.isReadable()){
                        SocketChannel client= (SocketChannel) selectionKey.channel();
                        ByteBuffer output= (ByteBuffer)selectionKey.attachment();
                        client.read(output);
                    }
                    if(selectionKey.isWritable()){
                        SocketChannel client=(SocketChannel) selectionKey.channel();
                        ByteBuffer output= (ByteBuffer)selectionKey.attachment();
                        output.flip();
                        client.write(output);
                        output.compact();
                    }
                }catch (IOException e){
                    selectionKey.cancel();
                    try {
                        selectionKey.channel().close();
                    } catch (IOException cex) {
                    }
                }
            }

        }

    }
}
